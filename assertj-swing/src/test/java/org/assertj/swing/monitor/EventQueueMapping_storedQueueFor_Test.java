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

import java.awt.EventQueue;

import org.junit.Test;

/**
 * Tests for {@link EventQueueMapping#storedQueueFor(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class EventQueueMapping_storedQueueFor_Test extends EventQueueMapping_TestCase {
  @Test
  public void should_Return_Stored_EventQueue() {
    mapping.addQueueFor(component);
    EventQueue storedEventQueue = mapping.storedQueueFor(component);
    assertThat(storedEventQueue).isSameAs(eventQueue);
  }

  @Test
  public void should_Return_Null_If_EventQueue_Not_Stored() {
    assertThat(queueMap.keySet()).doesNotContain(component);
    EventQueue storedEventQueue = mapping.storedQueueFor(component);
    assertThat(storedEventQueue).isNull();
  }

  @Test
  public void should_Return_Null_If_EventQueue_Reference_Is_Null() {
    queueMap.put(component, null);
    EventQueue storedEventQueue = mapping.storedQueueFor(component);
    assertThat(storedEventQueue).isNull();
  }
}
