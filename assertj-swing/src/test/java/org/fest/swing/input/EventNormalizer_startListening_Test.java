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

import static org.fest.swing.test.awt.Toolkits.newToolkitStub;

import java.awt.event.AWTEventListener;

import org.fest.swing.test.awt.ToolkitStub;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link EventNormalizer#startListening(java.awt.Toolkit, AWTEventListener, long)}.
 * 
 * @author Alex Ruiz
 */
public class EventNormalizer_startListening_Test extends EventNormalizer_TestCase {
  private EventNormalizer eventNormalizer;

  @Before
  public void setUp() {
    eventNormalizer = new EventNormalizer();
  }

  @Test
  public void should_attach_to_Toolkit_when_start_listening() {
    ToolkitStub toolkit = newToolkitStub();
    int mask = 8;
    eventNormalizer.startListening(toolkit, delegateEventListenerMock(), mask);
    checkEventNormalizerInToolkit(toolkit, eventNormalizer, mask);
  }
}
