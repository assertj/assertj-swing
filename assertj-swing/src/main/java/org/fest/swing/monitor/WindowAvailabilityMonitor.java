/*
 * Created on Oct 9, 2007
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

import static java.awt.AWTEvent.MOUSE_EVENT_MASK;
import static java.awt.AWTEvent.MOUSE_MOTION_EVENT_MASK;
import static java.awt.AWTEvent.PAINT_EVENT_MASK;
import static javax.swing.SwingUtilities.getWindowAncestor;
import static org.fest.swing.listener.WeakEventListener.attachAsWeakEventListener;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInEDT;

/**
 * Event listener that monitors when a window is ready to receive OS-level event input.
 * 
 * @author Alex Ruiz
 */
final class WindowAvailabilityMonitor implements AWTEventListener {
  private static final long EVENT_MASK = MOUSE_MOTION_EVENT_MASK | MOUSE_EVENT_MASK | PAINT_EVENT_MASK;

  private final Windows windows;

  WindowAvailabilityMonitor(@Nonnull Windows windows) {
    this.windows = windows;
  }

  void attachTo(@Nonnull Toolkit toolkit) {
    attachAsWeakEventListener(toolkit, this, EVENT_MASK);
  }

  @RunsInEDT
  @Override
  public void eventDispatched(AWTEvent e) {
    if (!(e instanceof MouseEvent)) {
      return;
    }
    Object source = e.getSource();
    if (!(source instanceof Component)) {
      return;
    }
    Component c = (Component) source;
    Window w = c instanceof Window ? (Window) c : getWindowAncestor(c);
    windows.markAsReady(w);
  }
}
