/*
 * Created on Mar 27, 2008
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
package org.fest.swing.listener;

import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.SwingUtilities.isEventDispatchThread;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

/**
 * <p>
 * AWT event listener that ensures all events are handled on the event dispatch thread (EDT.)
 * </p>
 * 
 * <p>
 * NOTE from Abbot: {@code Applet} runners may run several simultaneous event dispatch threads when displaying multiple
 * {@code Applet}s simultaneously. If this listener is installed in the parent context of those dispatch threads, it
 * will be invoked on each of those threads, possibly simultaneously.
 * </p>
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@ThreadSafe
public abstract class EventDispatchThreadedEventListener implements AWTEventListener {
  @GuardedBy("lock")
  private final List<AWTEvent> deferredEvents = newArrayList();

  private final Object lock = new Object();

  private final Runnable processDeferredEventsTask = new Runnable() {
    @Override
    public void run() {
      processDeferredEvents();
    }
  };

  /**
   * If this method is called in the event dispatch thread, it processes the given event and the queued ones. Otherwise
   * it will add the given event to the queue and process all the events in the queue in the event dispatch thread (EDT.)
   * 
   * @param event the event to process.
   */
  @Override
  public void eventDispatched(AWTEvent event) {
    if (!isEventDispatchThread()) {
      // Often the application under test will invoke Window.show, which spawns hierarchy events. We want to ensure we
      // respond to those events on the dispatch thread to avoid deadlock.
      synchronized (lock) {
        deferredEvents.add(event);
      }
      // Ensure that in the absence of any subsequent event thread events deferred events still get processed.
      // If regular events are received before this action is run, the deferred events will be processed prior to those
      // events and the action will do nothing.
      invokeLater(processDeferredEventsTask);
      return;
    }
    // Ensure any deferred events are processed prior to subsequently posted events.
    processDeferredEvents();
    processEvent(checkNotNull(event));
  }

  /** Processes any events that were generated off the event queue but not immediately handled. */
  protected void processDeferredEvents() {
    // Make a copy of the deferred events and empty the queue
    List<AWTEvent> queue = newArrayList();
    synchronized (lock) {
      // In the rare case where there are multiple simultaneous dispatch threads, it's possible for deferred events to
      // get posted while another event is being processed. At most this will mean a few events get processed out of
      // order, but they will likely be from different event dispatch contexts, so it shouldn't matter.
      queue.addAll(deferredEvents);
      deferredEvents.clear();
    }
    while (queue.size() > 0) {
      AWTEvent event = queue.get(0);
      queue.remove(0);
      processEvent(checkNotNull(event));
    }
  }

  /**
   * This method is not protected by any synchronization locks (nor should it be); in the presence of multiple
   * simultaneous event dispatch threads, the listener must be thread-safe.
   * 
   * @param event the event to process.
   */
  protected abstract void processEvent(@Nonnull AWTEvent event);
}
