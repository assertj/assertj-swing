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

import static java.awt.event.WindowEvent.WINDOW_CLOSED;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;
import static java.awt.event.WindowEvent.WINDOW_OPENED;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;
import static org.fest.swing.test.awt.TestAWTEvents.singletonAWTEventMock;
import static org.fest.swing.test.builder.JFrames.frame;

import java.awt.AWTEvent;
import java.awt.Window;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link DisposedWindowMonitor#isDuplicateDispose(AWTEvent)}.
 * 
 * @author Alex Ruiz
 */
public class DisposedWindowMonitor_isDuplicateDispose_Test {
  private DisposedWindowMonitor monitor;
  private Window window;

  @Before
  public void setUp() {
    monitor = new DisposedWindowMonitor();
    window = frame().createNew();
  }

  @Test
  public void should_return_is_not_duplicate_if_event_is_not_WindowEvent() {
    assertThat(monitor.isDuplicateDispose(singletonAWTEventMock())).isFalse();
    assertThat(monitor.disposedWindows).isEmpty();
  }

  @Test
  public void should_return_is_not_duplicate_if_Window_is_closing() {
    WindowEvent e = new WindowEvent(window, WINDOW_CLOSING);
    assertThat(monitor.isDuplicateDispose(e)).isFalse();
    assertThat(monitor.disposedWindows).isEmpty();
  }

  @Test
  public void should_return_is_not_duplicate_if_Window_is_not_closing_and_it_is_not_closed() {
    monitor.disposedWindows.put(window, true);
    WindowEvent e = new WindowEvent(window, WINDOW_OPENED);
    assertThat(monitor.isDuplicateDispose(e)).isFalse();
    assertThat(monitor.disposedWindows).isEmpty();
  }

  @Test
  public void should_return_is_duplicate_if_Window_is_closed_and_it_is_marked_as_disposed() {
    monitor.disposedWindows.put(window, true);
    WindowEvent e = new WindowEvent(window, WINDOW_CLOSED);
    assertThat(monitor.isDuplicateDispose(e)).isTrue();
    assertThat(monitor.disposedWindows).hasSize(1);
    assertThat(monitor.disposedWindows).includes(entry(window, true));
  }

  @Test
  public void should_return_is_not_duplicate_if_Window_is_closed_and_it_is_not_marked_as_disposed() {
    WindowEvent e = new WindowEvent(window, WINDOW_CLOSED);
    assertThat(monitor.isDuplicateDispose(e)).isFalse();
    assertThat(monitor.disposedWindows).hasSize(1).includes(entry(window, true));
    ComponentListener[] componentListeners = window.getComponentListeners();
    assertThat(componentListeners).hasSize(1);
    assertThat(componentListeners[0]).isInstanceOf(DisposalMonitor.class);
    DisposalMonitor disposalMonitor = (DisposalMonitor) componentListeners[0];
    assertThat(disposalMonitor.disposedWindows).isSameAs(monitor.disposedWindows);
  }
}
