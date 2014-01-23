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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.awt.Toolkits.newToolkitStub;

import org.fest.swing.test.awt.ToolkitStub;
import org.junit.Test;

/**
 * Tests for {@link DragAwareEventNormalizer#startListening(java.awt.Toolkit, java.awt.event.AWTEventListener, long)}.
 * 
 * @author Alex Ruiz
 */
public class DragAwareEventNormalizer_startListening_Test extends DragAwareEventNormalizer_TestCase {
  @Test
  public void should_replace_EventQueue_when_starts_listening() {
    ToolkitStub toolkit = newToolkitStub();
    EventQueueStub eventQueue = new EventQueueStub();
    toolkit.eventQueue(eventQueue);
    int mask = 8;
    eventNormalizer.startListening(toolkit, delegateEventListenerMock(), mask);
    checkEventNormalizerInToolkit(toolkit, eventNormalizer, mask);
    assertThat(eventQueue.pushedEventQueue).isInstanceOf(DragAwareEventQueue.class);
  }
}
