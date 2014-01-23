/*
 * Created on Oct 14, 2007
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

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Sets.newLinkedHashSet;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Monitor that maps event queues to GUI components and GUI components to event event queues.
 * 
 * @author Alex Ruiz
 */
@ThreadSafe
class Context {
  /** Maps unique event queues to the set of root windows found on each queue. */
  @GuardedBy("lock")
  private final WindowEventQueueMapping windowEventQueueMapping;

  /** Maps components to their corresponding event queues. */
  @GuardedBy("lock")
  private final EventQueueMapping eventQueueMapping;

  private final Object lock = new Object();

  Context(@Nonnull Toolkit toolkit) {
    this(toolkit, new WindowEventQueueMapping(), new EventQueueMapping());
  }

  Context(@Nonnull Toolkit toolkit, @Nonnull WindowEventQueueMapping windowEventQueueMapping,
      @Nonnull EventQueueMapping eventQueueMapping) {
    this.windowEventQueueMapping = windowEventQueueMapping;
    this.eventQueueMapping = eventQueueMapping;
    this.windowEventQueueMapping.addQueueFor(toolkit);
  }

  /**
   * Return all available root {@code Window}s. A root {@code Window} is one that has a {@code null} parent. Nominally
   * this means a list similar to that returned by {@code Frame.getFrames()}, but in the case of an {@code Applet} may
   * return a few dialogs as well.
   * 
   * @return all available root {@code Window}s.
   */
  @Nonnull
  Collection<Window> rootWindows() {
    Set<Window> rootWindows = newLinkedHashSet();
    synchronized (lock) {
      rootWindows.addAll(windowEventQueueMapping.windows());
    }
    rootWindows.addAll(newArrayList(Frame.getFrames()));
    rootWindows.addAll(newArrayList(Window.getOwnerlessWindows()));
    return rootWindows;
  }

  @Nullable EventQueue storedQueueFor(@Nonnull Component c) {
    synchronized (lock) {
      return eventQueueMapping.storedQueueFor(c);
    }
  }

  void removeContextFor(@Nonnull Component component) {
    synchronized (lock) {
      windowEventQueueMapping.removeMappingFor(component);
    }
  }

  void addContextFor(@Nonnull Component component) {
    synchronized (lock) {
      windowEventQueueMapping.addQueueFor(component);
      eventQueueMapping.addQueueFor(component);
    }
  }

  /**
   * Return the event queue corresponding to the given AWT or Swing {@code Component}. In most cases, this is the same as
   * {@link java.awt.Toolkit#getSystemEventQueue()}, but in the case of applets will bypass the {@code AppContext} and
   * provide the real event queue.
   * 
   * @param c the given {@code Component}.
   * @return the event queue corresponding to the given {@code Component}.
   */
  @RunsInEDT
  @Nullable EventQueue eventQueueFor(@Nonnull Component c) {
    Component component = topParentOf(c);
    if (component == null) {
      return null;
    }
    synchronized (lock) {
      return eventQueueMapping.queueFor(component);
    }
  }

  @RunsInEDT
  private static @Nullable Component topParentOf(final @Nonnull Component c) {
    return execute(new GuiQuery<Component>() {
      @Override
      protected @Nullable Component executeInEDT() {
        Component parent = c;
        // Components above the applet in the hierarchy may or may not share the same context with the applet itself.
        while (!(parent instanceof java.applet.Applet) && parent.getParent() != null) {
          parent = parent.getParent();
        }
        return parent;
      }
    });
  }

  /**
   * @return all known event queues.
   */
  @Nonnull Collection<EventQueue> allEventQueues() {
    Set<EventQueue> eventQueues = newLinkedHashSet();
    synchronized (lock) {
      eventQueues.addAll(windowEventQueueMapping.eventQueues());
      eventQueues.addAll(eventQueueMapping.eventQueues());
    }
    return eventQueues;
  }
}
