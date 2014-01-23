/*
 * Created on Mar 28, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.input;

import static java.awt.AWTEvent.KEY_EVENT_MASK;
import static java.awt.AWTEvent.MOUSE_EVENT_MASK;
import static java.awt.AWTEvent.MOUSE_MOTION_EVENT_MASK;
import static javax.swing.SwingUtilities.getDeepestComponentAt;
import static org.fest.swing.awt.AWT.locationOnScreenOf;
import static org.fest.swing.input.MouseInfo.BUTTON_MASK;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.IllegalComponentStateException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import org.fest.swing.exception.UnexpectedException;
import org.fest.swing.listener.EventDispatchThreadedEventListener;

/**
 * <p>
 * Keeps track of a given input state. Includes mouse/pointer position and keyboard modifier key state.
 * </p>
 * 
 * <p>
 * Synchronization assumes that any given instance might be called from more than one event dispatch thread (EDT.)
 * </p>
 */
// TODO: add a BitSet with the full keyboard key press state
@ThreadSafe
public class InputState {
  @GuardedBy("this")
  private final MouseInfo mouseInfo = new MouseInfo();

  @GuardedBy("this")
  private final DragDropInfo dragDropInfo = new DragDropInfo();

  @GuardedBy("this")
  private int modifiers;

  @GuardedBy("this")
  private long lastEventTime;

  private EventNormalizer normalizer;

  public InputState(@Nonnull Toolkit toolkit) {
    long mask = MOUSE_MOTION_EVENT_MASK | MOUSE_EVENT_MASK | KEY_EVENT_MASK;
    AWTEventListener listener = new EventDispatchThreadedEventListener() {
      @Override
      protected void processEvent(@Nonnull AWTEvent event) {
        update(event);
      }
    };
    normalizer = new EventNormalizer();
    normalizer.startListening(toolkit, listener, mask);
  }

  public synchronized void clear() {
    mouseInfo.clear();
    dragDropInfo.clear();
    modifiers = 0;
    lastEventTime = 0;
  }

  public void dispose() {
    normalizer.stopListening();
    normalizer = null;
  }

  /**
   * Explicitly update the internal state.
   * 
   * @param event the event to use to update the internal state.
   */
  public void update(@Nonnull AWTEvent event) {
    if (event instanceof InputEvent) {
      InputEvent inputEvent = (InputEvent) event;
      if (inputEvent.getWhen() < lastEventTime()) {
        // event is old.
        return;
      }
      if (inputEvent instanceof KeyEvent) {
        synchronized (this) {
          lastEventTime(inputEvent);
          modifiers(inputEvent.getModifiers());
          // FIXME add state of individual keys
        }
      }
      if (inputEvent instanceof MouseEvent) {
        MouseEvent mouseEvent = (MouseEvent) inputEvent;
        Point eventScreenLocation = screenLocation(mouseEvent);
        synchronized (this) {
          lastEventTime(mouseEvent);
          dragDropInfo.update(mouseEvent);
          mouseInfo.modifiers(modifiers);
          mouseInfo.update(mouseEvent, eventScreenLocation);
          modifiers(mouseInfo.modifiers());
        }
      }
    }
  }

  private @Nullable Point screenLocation(@Nonnull MouseEvent event) {
    // childAt and locationOnScreenOf want the tree lock, so be careful not to use any additional locks at the same time
    // to avoid deadlock.
    // Determine the current mouse position in screen coordinates
    try {
      Component component = checkNotNull(event.getComponent());
      return locationOnScreenOf(component);
    } catch (IllegalComponentStateException e) {
      // component might be hidden by the time we process this event
    } catch (UnexpectedException e) {
      if (!(e.getCause() instanceof IllegalComponentStateException)) {
        throw e;
      }
    }
    return null;
  }

  private void lastEventTime(@Nonnull InputEvent event) {
    lastEventTime = event.getWhen();
  }

  private void modifiers(int newModifiers) {
    modifiers = newModifiers;
  }

  /**
   * @return the most deeply nested AWT or Swing {@code Component} which currently contains the pointer.
   */
  public synchronized @Nullable Component deepestComponentUnderMousePointer() {
    Component c = mouseComponent();
    if (c != null) {
      Point mouseLocation = mouseLocation();
      if (mouseLocation != null) {
        c = childAt(c, mouseLocation);
      }
    }
    return c;
  }

  /**
   * Returns the last known AWT or Swing {@code Component} to contain the pointer, or {@code null} if none. Note that
   * this may not correspond to the {@code Component} that actually shows up in {@code AWTEvent}s.
   * 
   * @return the last known {@code Component} to contain the pointer, or {@code null} if none.
   */
  public synchronized @Nullable Component mouseComponent() {
    return mouseInfo.component();
  }

  /**
   * <p>
   * Returns the AWT or Swing {@code Component} under the given coordinates in the given parent component. Events are
   * often generated only for the outermost {@code Container}, so we have to determine if the pointer is actually within
   * a child. Basically the same as {@code Component.getComponentAt}, but recurses to the lowest-level {@code Component}
   * instead of only one level. Point is in {@code Component} coordinates.
   * </p>
   * 
   * <p>
   * The default {@code Component.getComponentAt} can return invisible {@code Component}s ({@code JRootPane} has an
   * invisible {@code JPanel} (glass pane?) which will otherwise swallow everything).
   * </p>
   * 
   * <p>
   * NOTE: childAt grabs the {@code TreeLock}, so this should *only* be invoked on the event dispatch thread, preferably
   * with no other locks held. Use it elsewhere at your own risk.
   * </p>
   * 
   * @param parent the given parent.
   * @param where the given coordinates.
   * @return the {@code Component} under the given coordinates in the given parent {@code Component}.
   */
  public static Component childAt(@Nonnull Component parent, @Nonnull Point where) {
    return getDeepestComponentAt(parent, where.x, where.y);
  }

  /**
   * Indicates there is a drag operation in progress.
   * 
   * @return {@code true} if there is a drag operation in progress, {@code false} otherwise.
   */
  public synchronized boolean dragInProgress() {
    return dragDropInfo.isDragging();
  }

  /**
   * @return the AWT or Swing {@code Component} where a drag operation started.
   */
  public synchronized Component dragSource() {
    return dragDropInfo.source();
  }

  /**
   * @return the coordinates where a drag operation started.
   */
  public synchronized Point dragOrigin() {
    return dragDropInfo.origin();
  }

  /**
   * @return the number of times a mouse button was clicked.
   */
  public synchronized int clickCount() {
    return mouseInfo.clickCount();
  }

  /**
   * @return the time when the last input event occurred.
   */
  public synchronized long lastEventTime() {
    return lastEventTime;
  }

  /**
   * @return all currently active modifiers.
   */
  public synchronized int modifiers() {
    return modifiers;
  }

  /**
   * @return the currently pressed key modifiers.
   */
  public synchronized int keyModifiers() {
    return modifiers & ~BUTTON_MASK;
  }

  /**
   * @return the mouse buttons used in the last input event.
   */
  public synchronized int buttons() {
    return mouseInfo.buttons();
  }

  /**
   * @return the mouse location relative to the component that currently contains the pointer, or {@code null} if
   *         outside all components.
   */
  public synchronized @Nullable Point mouseLocation() {
    return mouseInfo.location();
  }

  /**
   * @return the last known mouse location.
   */
  public synchronized @Nullable Point mouseLocationOnScreen() {
    return mouseInfo.locationOnScreen();
  }

  /**
   * Indicates whether there is a native drag/drop operation in progress.
   * 
   * @return {@code true} if there is a native drag/drop operation in progress, {@code false} otherwise.
   */
  public boolean isNativeDragActive() {
    return dragDropInfo.isNativeDragActive();
  }
}
