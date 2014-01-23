/*
 * Created on May 22, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.awt.TestAWTEvents.singletonAWTEventMock;
import static org.fest.swing.test.awt.Toolkits.newToolkitMock;
import static org.fest.swing.test.util.StopWatch.startNewStopWatch;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;

import org.fest.swing.input.InputState;
import org.fest.swing.monitor.WindowMonitor;
import org.fest.swing.test.awt.TestComponents;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.fest.swing.test.util.StopWatch;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link AWTEventPoster#postEvent(Component, AWTEvent)}.
 *
 * @author Alex Ruiz
 */
public class AWTEventPoster_postEvent_Test extends EDTSafeTestCase {
  private static final int WAIT_DELAY = 1000;
  private Toolkit toolkit;
  private InputState inputState;
  private WindowMonitor monitor;
  private Settings settings;
  private EventQueue eventQueue;
  private AWTEvent event;
  private AWTEventPoster poster;

  @Before
  public void setUp() {
    toolkit = newToolkitMock();
    inputState = mock(InputState.class);
    monitor = mock(WindowMonitor.class);
    settings = mock(Settings.class);
    eventQueue = mock(EventQueue.class);
    event = singletonAWTEventMock();
    poster = new AWTEventPoster(toolkit, inputState, monitor, settings);
  }

  @Test
  public void should_post_event_in_Component_EventQueue_if_Component_is_not_null() {
    final Component c = TestComponents.newComponentMock();
    when(settings.delayBetweenEvents()).thenReturn(WAIT_DELAY);
    when(monitor.eventQueueFor(c)).thenReturn(eventQueue);
    postEventAndAssertItWaited(c);
    verify(eventQueue).postEvent(event);
    verify(inputState).update(event);
  }

  @Test
  public void should_post_event_in_System_EventQueue_ff_Component_is_null() {
    when(settings.delayBetweenEvents()).thenReturn(WAIT_DELAY);
    when(toolkit.getSystemEventQueue()).thenReturn(eventQueue);
    postEventAndAssertItWaited(null);
    verify(inputState).update(event);
    verify(eventQueue).postEvent(event);
  }

  private void postEventAndAssertItWaited(Component c) {
    StopWatch stopWatch = startNewStopWatch();
    poster.postEvent(c, event);
    stopWatch.stop();
    assertThat(stopWatch.ellapsedTime()).isGreaterThanOrEqualTo(WAIT_DELAY);
  }
}
