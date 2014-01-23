/*
 * Created on Oct 18, 2007
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
import static java.awt.AWTEvent.MOUSE_EVENT_MASK;
import static java.awt.AWTEvent.MOUSE_MOTION_EVENT_MASK;
import static java.awt.AWTEvent.PAINT_EVENT_MASK;
import static java.awt.AWTEvent.WINDOW_EVENT_MASK;
import static org.fest.assertions.Assertions.assertThat;

import java.awt.event.AWTEventListener;
import java.util.List;

import org.fest.swing.listener.WeakEventListener;
import org.junit.Test;

/**
 * Tests for {@link WindowMonitor#WindowMonitor(java.awt.Toolkit, Context, WindowStatus)}.
 * 
 * @author Alex Ruiz
 */
public class WindowMonitor_constructor_Test extends WindowMonitor_TestCase {
  private static final long WINDOWS_AVAILABILITY_MONITOR_EVENT_MASK = MOUSE_MOTION_EVENT_MASK | MOUSE_EVENT_MASK
      | PAINT_EVENT_MASK;

  private static final long CONTEXT_MONITOR_EVENT_MASK = WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK;

  @Test
  public void should_attach_monitors_and_populate_existing_Windows() {
    assertThatListenerIsUnderMask(CONTEXT_MONITOR_EVENT_MASK, ContextMonitor.class);
    assertThatListenerIsUnderMask(WINDOWS_AVAILABILITY_MONITOR_EVENT_MASK, WindowAvailabilityMonitor.class);
  }

  private void assertThatListenerIsUnderMask(long mask, Class<? extends AWTEventListener> type) {
    AWTEventListener listener = listenerUnderMask(mask);
    assertThat(listener).isInstanceOf(type);
  }

  private AWTEventListener listenerUnderMask(long mask) {
    List<WeakEventListener> contextMonitorWrappers = toolkit
        .eventListenersUnderEventMask(mask, WeakEventListener.class);
    assertThat(contextMonitorWrappers).hasSize(1);
    return contextMonitorWrappers.get(0).underlyingListener();
  }
}
