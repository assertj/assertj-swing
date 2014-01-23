/*
 * Created on Jul 27, 2009
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

import static java.awt.AWTEvent.KEY_EVENT_MASK;
import static org.fest.swing.test.awt.TestAWTEventListeners.singletonAWTEventListenerMock;
import static org.fest.swing.test.awt.Toolkits.newToolkitMock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Toolkit;
import java.awt.event.AWTEventListener;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link EmergencyAbortListener#register()}.
 *
 * @author Alex Ruiz
 */
public class EmergencyAbortListener_register_Test {
  private Toolkit toolkit;
  private EmergencyAbortListener listener;

  @Before
  public void setUp() {
    toolkit = newToolkitMock();
    listener = new EmergencyAbortListener(toolkit);
  }

  @Test
  public void should_remove_previous_and_register_itself_in_toolkit() {
    EmergencyAbortListener previous = mock(EmergencyAbortListener.class);
    AWTEventListener[] allPrevious = { previous, singletonAWTEventListenerMock() };
    when(toolkit.getAWTEventListeners(KEY_EVENT_MASK)).thenReturn(allPrevious);
    listener.register();
    verify(toolkit).removeAWTEventListener(previous);
    verify(toolkit).addAWTEventListener(listener, KEY_EVENT_MASK);
  }
}
