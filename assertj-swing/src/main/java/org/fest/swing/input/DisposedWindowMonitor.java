/*
 * Created on Jun 24, 2008
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

import static java.awt.event.WindowEvent.WINDOW_CLOSED;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;
import static org.fest.util.Maps.newWeakHashMap;

import java.awt.AWTEvent;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.util.Map;

/**
 * Verifies that a notification of the disposal of an AWT or Swing {@code Window} is not duplicated.
 * 
 * @author Alex Ruiz
 */
class DisposedWindowMonitor {
  final Map<Window, Boolean> disposedWindows = newWeakHashMap();

  // We want to ignore consecutive event indicating window disposal; it needs to be an intervening SHOWN/OPEN before
  // we're interested again.
  boolean isDuplicateDispose(AWTEvent event) {
    if (!(event instanceof WindowEvent)) {
      return false;
    }
    WindowEvent windowEvent = (WindowEvent) event;
    int eventId = windowEvent.getID();
    if (eventId == WINDOW_CLOSING) {
      return false;
    }
    if (eventId == WINDOW_CLOSED) {
      Window w = windowEvent.getWindow();
      if (disposedWindows.containsKey(w)) {
        return true;
      }
      disposedWindows.put(w, true);
      // execute(addComponentListenerTask(w, new DisposalMonitor(disposedWindows)));
      w.addComponentListener(new DisposalMonitor(disposedWindows));
      return false;
    }
    disposedWindows.remove(windowEvent.getWindow());
    return false;
  }
}
