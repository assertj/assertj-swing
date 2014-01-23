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

import static java.awt.event.WindowEvent.WINDOW_CLOSED;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;
import static java.awt.event.WindowEvent.WINDOW_FIRST;
import static java.awt.event.WindowEvent.WINDOW_LAST;
import static java.awt.event.WindowEvent.WINDOW_OPENED;
import static org.fest.swing.monitor.TestContexts.newMockContext;
import static org.fest.swing.monitor.TestWindows.newWindowsMock;
import static org.fest.util.Lists.newArrayList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link ContextMonitor#eventDispatched(java.awt.AWTEvent)}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class ContextMonitor_eventDispatched_ParameterizedTest extends EDTSafeTestCase {
  private ContextMonitor monitor;

  private Windows windows;
  private Context context;
  private TestWindow window;

  private final int eventId;

  @Parameters
  public static Collection<Object[]> eventsBetweenWindowFirstAndWindowLast() {
    List<Object[]> ids = newArrayList();
    for (int id = WINDOW_FIRST; id <= WINDOW_LAST; id++) {
      if (id == WINDOW_OPENED || id == WINDOW_CLOSED || id == WINDOW_CLOSING) {
        continue;
      }
      ids.add(new Object[] { id });
    }
    return ids;
  }

  public ContextMonitor_eventDispatched_ParameterizedTest(int eventId) {
    this.eventId = eventId;
  }

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
  public void shouldProcessEventWithIdBetweenWindowFirstAndWindowLastAndWindowNotInContext() {
    when(context.rootWindows()).thenReturn(new ArrayList<Window>());
    when(context.storedQueueFor(window)).thenReturn(window.getToolkit().getSystemEventQueue());
    dispatchEventToMonitor(eventId);
    verify(context).addContextFor(window);
    verify(windows).attachNewWindowVisibilityMonitor(window);
    verify(windows).markAsShowing(window);
  }

  @Test
  public void shouldProcessEventWithIdBetweenWindowFirstAndWindowLastAndWindowInContextAndClosed() {
    when(context.storedQueueFor(window)).thenReturn(window.getToolkit().getSystemEventQueue());
    when(context.rootWindows()).thenReturn(frameInList());
    when(windows.isClosed(window)).thenReturn(true);
    dispatchEventToMonitor(eventId);
    verify(context).addContextFor(window);
    verify(windows).attachNewWindowVisibilityMonitor(window);
    verify(windows).markAsShowing(window);
  }

  @Test
  public void shouldProcessEventWithIdBetweenWindowFirstAndWindowLastAndWindowInContextAndNotClosed() {
    when(context.storedQueueFor(window)).thenReturn(window.getToolkit().getSystemEventQueue());
    when(context.rootWindows()).thenReturn(frameInList());
    when(windows.isClosed(window)).thenReturn(false);
    dispatchEventToMonitor(eventId);
    // TODO(Alex): verify something!
  }

  private void dispatchEventToMonitor(int eventId) {
    monitor.eventDispatched(new ComponentEvent(window, eventId));
  }

  private List<Window> frameInList() {
    List<Window> rootWindows = new ArrayList<Window>();
    rootWindows.add(window);
    return rootWindows;
  }
}
