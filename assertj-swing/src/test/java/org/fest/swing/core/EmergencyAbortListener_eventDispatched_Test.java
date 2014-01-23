/*
 * Created on Jul 20, 2008
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
package org.fest.swing.core;

import static java.awt.event.InputEvent.CTRL_MASK;
import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.KEY_PRESSED;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_Z;
import static org.fest.swing.test.awt.TestAWTEvents.newAWTEventMock;
import static org.fest.swing.test.awt.Toolkits.singletonToolkitMock;
import static org.fest.swing.test.builder.JButtons.button;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link EmergencyAbortListener#eventDispatched(AWTEvent)}.
 *
 * @author Alex Ruiz
 */
public class EmergencyAbortListener_eventDispatched_Test extends EDTSafeTestCase {
  private TestTerminator terminator;
  private EmergencyAbortListener listener;

  @Before
  public void setUp() {
    terminator = mock(TestTerminator.class);
    listener = new EmergencyAbortListener(singletonToolkitMock(), terminator);
  }

  @Test
  public void should_not_terminate_tests_if_event_id_is_not_KeyPressed() {
    AWTEvent event = newAWTEventMock();
    int eventId = KEY_PRESSED + 1;
    when(event.getID()).thenReturn(eventId);
    listener.eventDispatched(event);
    verifyZeroInteractions(terminator);
  }

  @Test
  public void should_not_terminate_tests_if_event_is_not_KeyEvent() {
    AWTEvent event = newAWTEventMock();
    when(event.getID()).thenReturn(KEY_PRESSED);
    listener.eventDispatched(event);
    verifyZeroInteractions(terminator);
  }

  @Test
  public void should_not_terminate_tests_if_key_code_does_not_match() {
    KeyEvent event = new KeyEvent(button().createNew(), KEY_PRESSED, 0, 0, VK_Z, 'Z');
    listener.eventDispatched(event);
    verifyZeroInteractions(terminator);
  }

  @Test
  public void should_not_terminate_tests_if_modifiers_do_not_match() {
    KeyEvent event = new KeyEvent(button().createNew(), KEY_PRESSED, 0, 0, VK_A, 'A');
    listener.eventDispatched(event);
    verifyZeroInteractions(terminator);
  }

  @Test
  public void should_terminate_tests_if_key_combination_matches() {
    KeyEvent event = new KeyEvent(button().createNew(), KEY_PRESSED, 0, CTRL_MASK | SHIFT_MASK, VK_A, 'A');
    listener.eventDispatched(event);
    verify(terminator).terminateTests();
  }
}
