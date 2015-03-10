/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.monitor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.test.awt.Toolkits.newToolkitStub;

import java.awt.EventQueue;
import java.util.Collection;

import org.assertj.swing.test.awt.ToolkitStub;
import org.junit.Test;

/**
 * Tests for {@link WindowEventQueueMapping#eventQueues()}.
 * 
 * @author Alex Ruiz
 */
public class WindowEventQueueMapping_eventQueues_Test extends WindowEventQueueMapping_withWindow_TestCase {
  @Test
  public void should_Return_EventQueues() {
    EventQueue anotherEventQueue = new EventQueue();
    ToolkitStub anotherToolkit = newToolkitStub(anotherEventQueue);
    MyWindow anotherWindow = MyWindow.createNew(anotherToolkit, getClass());
    mapping.addQueueFor(window);
    mapping.addQueueFor(anotherWindow);
    Collection<EventQueue> eventQueues = mapping.eventQueues();
    assertThat(eventQueues).containsOnly(eventQueue, anotherEventQueue);
  }
}
