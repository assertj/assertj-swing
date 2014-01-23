/*
 * Created on Oct 13, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.monitor;

import static java.awt.AWTEvent.COMPONENT_EVENT_MASK;
import static java.awt.AWTEvent.WINDOW_EVENT_MASK;
import static java.awt.event.ComponentEvent.COMPONENT_SHOWN;
import static java.awt.event.WindowEvent.WINDOW_CLOSED;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;
import static java.awt.event.WindowEvent.WINDOW_FIRST;
import static java.awt.event.WindowEvent.WINDOW_LAST;
import static java.awt.event.WindowEvent.WINDOW_OPENED;
import static org.fest.swing.listener.WeakEventListener.attachAsWeakEventListener;
import static org.fest.swing.query.ComponentParentQuery.parentOf;

import java.applet.Applet;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentEvent;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInEDT;

/**
 * Monitor for AWT or Swing {@code Component}s, and event queues.
 * 
 * @author Alex Ruiz
 */
final class ContextMonitor implements AWTEventListener {
  private static final long EVENT_MASK = WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK;

  private final Context context;
  private final Windows windows;

  ContextMonitor(Context context, Windows windows) {
    this.context = context;
    this.windows = windows;
  }

  void attachTo(@Nonnull Toolkit toolkit) {
    attachAsWeakEventListener(toolkit, this, EVENT_MASK);
  }

  @RunsInEDT
  @Override
  public void eventDispatched(AWTEvent e) {
    if (!(e instanceof ComponentEvent)) {
      return;
    }
    ComponentEvent event = (ComponentEvent) e;
    Component component = event.getComponent();
    // This is our sole means of accessing other AppContexts (if running within an applet). We look for window events
    // beyond OPENED in order to catch windows that have already opened by the time we start listening but which are not
    // in the Frame.getFrames list (i.e. they are on a different context). Specifically watch for COMPONENT_SHOWN on
    // applets, since we may not get frame events for them.
    if (!(component instanceof Applet) && !(component instanceof Window)) {
      return;
    }
    processEvent(event);
    // The context for root-level windows may change between WINDOW_OPENED and subsequent events.
    if (!component.getToolkit().getSystemEventQueue().equals(context.storedQueueFor(component))) {
      context.addContextFor(component);
    }
  }

  private void processEvent(@Nonnull ComponentEvent event) {
    Component component = event.getComponent();
    if (component == null) {
      return;
    }
    int id = event.getID();
    if (id == WINDOW_OPENED) {
      recognizeAsOpenWindow(component);
      return;
    }
    if (id == WINDOW_CLOSED) {
      recognizeAsClosedWindow(component);
      return;
    }
    if (id == WINDOW_CLOSING) {
      return;
    }
    if ((id >= WINDOW_FIRST && id <= WINDOW_LAST) || id == COMPONENT_SHOWN) {
      if ((!context.rootWindows().contains(component)) || windows.isClosed(component)) {
        recognizeAsOpenWindow(component);
      }
    }
  }

  private void recognizeAsOpenWindow(@Nonnull Component component) {
    context.addContextFor(component);
    // Attempt to ensure the window is ready for input before recognizing it as "open".
    // There is no Java API for this, so we institute an empirically tested delay.
    if (!(component instanceof Window)) {
      return;
    }
    windows.attachNewWindowVisibilityMonitor((Window) component);
    windows.markAsShowing((Window) component);
    // Native components don't receive events anyway...
    if (component instanceof FileDialog) {
      windows.markAsReady((Window) component);
    }
  }

  private void recognizeAsClosedWindow(@Nonnull Component component) {
    if (parentOf(component) == null) {
      context.removeContextFor(component);
    }
    if (component instanceof Window) {
      windows.markAsClosed((Window) component);
    }
  }
}
