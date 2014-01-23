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

import static java.awt.AWTEvent.COMPONENT_EVENT_MASK;
import static java.awt.AWTEvent.WINDOW_EVENT_MASK;
import static java.awt.event.WindowEvent.WINDOW_CLOSED;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;
import static java.awt.event.WindowEvent.WINDOW_OPENED;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.monitor.TestContexts.newMockContext;
import static org.fest.swing.monitor.TestWindows.newWindowsMock;
import static org.fest.swing.test.awt.Toolkits.newToolkitStub;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.applet.Applet;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.util.List;

import org.fest.swing.listener.WeakEventListener;
import org.fest.swing.test.awt.ToolkitStub;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link ContextMonitor#eventDispatched(java.awt.AWTEvent)}.
 *
 * @author Alex Ruiz
 */
public class ContextMonitor_eventDispatched_Test extends EDTSafeTestCase {
  private static final long EVENT_MASK = WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK;

  private ContextMonitor monitor;

  private Windows windows;
  private Context context;
  private TestWindow window;

  @Before
  public void setUp() {
    window = TestWindow.createNewWindow(getClass());
    windows = newWindowsMock();
    context = newMockContext();
    monitor = new ContextMonitor(context, windows);
  }

  @After
  public void tearDown() {
    window.destroy();
  }

  @Test
  public void shouldAttachItSelfToToolkit() {
    ToolkitStub toolkit = newToolkitStub();
    monitor.attachTo(toolkit);
    List<WeakEventListener> eventListeners = toolkit.eventListenersUnderEventMask(EVENT_MASK, WeakEventListener.class);
    assertThat(eventListeners).hasSize(1);
    WeakEventListener weakEventListener = eventListeners.get(0);
    assertThat(weakEventListener.underlyingListener()).isSameAs(monitor);
  }

  @Test
  public void shouldNotProcessEventIfComponentIsNotWindowOrApplet() {
    monitor.eventDispatched(new ComponentEvent(textField().createNew(), 8));
    verifyZeroInteractions(windows, context);
  }

  @Test
  public void shouldProcessEventWithIdEqualToWindowOpen() {
    when(context.storedQueueFor(window)).thenReturn(window.getToolkit().getSystemEventQueue());
    monitor.eventDispatched(new ComponentEvent(window, WINDOW_OPENED));
    verify(context).addContextFor(window);
    verify(windows).attachNewWindowVisibilityMonitor(window);
    verify(windows).markAsShowing(window);
  }

  @Test
  public void shouldProcessEventWithIdEqualToWindowOpenedAndMarkWindowAsReadyIfWindowIsFileDialog() {
    Window w = new FileDialog(window);
    when(context.storedQueueFor(w)).thenReturn(w.getToolkit().getSystemEventQueue());
    monitor.eventDispatched(new ComponentEvent(w, WINDOW_OPENED));
    verify(context).addContextFor(w);
    verify(windows).attachNewWindowVisibilityMonitor(w);
    verify(windows).markAsShowing(w);
    verify(windows).markAsReady(w);
  }

  @Test
  public void shouldProcessEventWithIdEqualToWindowClosedAndWithRootWindow() {
    when(context.storedQueueFor(window)).thenReturn(window.getToolkit().getSystemEventQueue());
    monitor.eventDispatched(new ComponentEvent(window, WINDOW_CLOSED));
    verify(context).removeContextFor(window);
    verify(windows).markAsClosed(window);
  }

  @Test
  public void shouldProcessEventWithIdEqualToWindowClosedAndWithNotRootWindow() {
    final Applet applet = new Applet();
    window.add(applet);
    when(context.storedQueueFor(applet)).thenReturn(applet.getToolkit().getSystemEventQueue());
    monitor.eventDispatched(new ComponentEvent(applet, WINDOW_CLOSED));
    verifyNoMoreInteractions(windows);
  }

  @Test
  public void shouldNotProcessEventWithIdWindowClosing() {
    when(context.storedQueueFor(window)).thenReturn(window.getToolkit().getSystemEventQueue());
    monitor.eventDispatched(new ComponentEvent(window, WINDOW_CLOSING));
    verifyZeroInteractions(windows);
  }

  @Test
  public void shouldAddToContextIfComponentEventQueueNotEqualToSystemEventQueue() {
    when(context.storedQueueFor(window)).thenReturn(new EventQueue());
    monitor.eventDispatched(new ComponentEvent(window, WINDOW_CLOSING));
    verify(context).addContextFor(window);
  }
}
