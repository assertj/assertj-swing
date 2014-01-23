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

import java.awt.EventQueue;

import org.junit.Test;

/**
 * Tests for {@link EventQueueMapping#storedQueueFor(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class EventQueueMapping_storedQueueFor_Test extends EventQueueMapping_TestCase {
  @Test
  public void should_return_stored_EventQueue() {
    mapping.addQueueFor(component);
    EventQueue storedEventQueue = mapping.storedQueueFor(component);
    assertThat(storedEventQueue).isSameAs(eventQueue);
  }

  @Test
  public void should_return_null_if_EventQueue_not_stored() {
    assertThat(queueMap.keySet()).excludes(eventQueue);
    EventQueue storedEventQueue = mapping.storedQueueFor(component);
    assertThat(storedEventQueue).isNull();
  }

  @Test
  public void should_return_null_if_EventQueue_reference_is_null() {
    queueMap.put(component, null);
    EventQueue storedEventQueue = mapping.storedQueueFor(component);
    assertThat(storedEventQueue).isNull();
  }
}
