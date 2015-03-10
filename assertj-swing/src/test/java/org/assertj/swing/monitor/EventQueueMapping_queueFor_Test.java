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
import java.lang.ref.WeakReference;

import org.junit.Test;

/**
 * Tests for {@link EventQueueMapping#queueFor(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class EventQueueMapping_queueFor_Test extends EventQueueMapping_TestCase {
  @Test
  public void should_Return_EventQueue() {
    EventQueue testQueue = new EventQueue();
    queueMap.put(component, new WeakReference<EventQueue>(testQueue));
    EventQueue storedEventQueue = mapping.queueFor(component);
    assertThat(storedEventQueue).isNotSameAs(eventQueue);
    assertThat(storedEventQueue).isSameAs(testQueue);
  }

  @Test
  public void should_Return_EventQueue_In_Component_If_No_Mapping_Found() {
    assertThat(queueMap.keySet()).doesNotContain(component);
    EventQueue storedEventQueue = mapping.queueFor(component);
    assertThat(storedEventQueue).isSameAs(eventQueue);
  }
}
