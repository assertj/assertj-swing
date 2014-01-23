/*
 * Created on Jul 31, 2009
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
package org.fest.swing.monitor;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.awt.Toolkits.newToolkitStub;
import static org.fest.swing.test.builder.JTextFields.textField;

import java.awt.EventQueue;
import java.util.Collection;

import org.fest.swing.test.awt.ToolkitStub;
import org.junit.Test;

/**
 * Tests for {@link EventQueueMapping#eventQueues()}.
 * 
 * @author Alex Ruiz
 */
public class EventQueueMapping_eventQueues_Test extends EventQueueMapping_TestCase {
  @Test
  public void should_return_all_EventQueues() {
    EventQueue anotherEventQueue = new EventQueue();
    ToolkitStub anotherToolkit = newToolkitStub(anotherEventQueue);
    ComponentWithCustomEventQueue anotherComponent = new ComponentWithCustomEventQueue(anotherToolkit);
    mapping.addQueueFor(component);
    mapping.addQueueFor(anotherComponent);
    Collection<EventQueue> allEventQueues = mapping.eventQueues();
    assertThat(allEventQueues).containsOnly(eventQueue, anotherEventQueue);
  }

  @Test
  public void should_not_fail_if_mapping_has_null_reference() {
    mapping.addQueueFor(component);
    queueMap.put(textField().createNew(), null);
    Collection<EventQueue> allEventQueues = mapping.eventQueues();
    assertThat(allEventQueues).containsOnly(eventQueue);
  }
}
