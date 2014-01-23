/*
 * Created on Jun 25, 2008
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

import static java.util.logging.Level.WARNING;

import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.util.EmptyStackException;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

import org.fest.util.VisibleForTesting;

/**
 * AWT event listener which normalizes the event stream:
 * <ul>
 * <li>sends a single {@code WINDOW_CLOSED}, instead of one every time dispose is called
 * <li>catches {@code sun.awt.dnd.SunDropTargetEvent}s during native drags
 * </ul>
 *
 * @author Alex Ruiz
 */
class DragAwareEventNormalizer extends EventNormalizer {
  private static Logger logger = Logger.getLogger(DragAwareEventNormalizer.class.getCanonicalName());

  private DragAwareEventQueue dragAwareEventQueue;

  @Override
  public void startListening(@Nonnull Toolkit toolkit, @Nonnull AWTEventListener delegate, long mask) {
    super.startListening(toolkit, delegate, mask);
    try {
      dragAwareEventQueue = createEventQueue(toolkit, mask);
      toolkit.getSystemEventQueue().push(dragAwareEventQueue);
      // execute(pushDragAwareEventQueueTask(toolkit, dragAwareEventQueue));
    } catch (RuntimeException e) {
      String msg = String.format("Ignoring error when starting up %s", DragAwareEventNormalizer.class.getName());
      logger.log(WARNING, msg, e);
    }
  }

  @VisibleForTesting
  DragAwareEventQueue createEventQueue(@Nonnull Toolkit toolkit, long mask) {
    return new DragAwareEventQueue(toolkit, mask, this);
  }

  @Override
  public void stopListening() {
    disposeDragAwareEventQueue();
    super.stopListening();
  }

  private void disposeDragAwareEventQueue() {
    if (dragAwareEventQueue == null) {
      return;
    }
    try {
      dragAwareEventQueue.pop();
    } catch (EmptyStackException e) {
    }
    dragAwareEventQueue = null;
  }
}
