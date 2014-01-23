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
 * Tests for {@link EventQueueMapping#queueFor(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class EventQueueMapping_queueFor_Test extends EventQueueMapping_TestCase {
  @Test
  public void should_return_EventQueue() {
    mapping.addQueueFor(component);
    EventQueue storedEventQueue = mapping.queueFor(component);
    assertThat(storedEventQueue).isSameAs(eventQueue);
  }

  @Test
  public void should_return_EventQueue_in_Component_if_no_mapping_found() {
    assertThat(queueMap.keySet()).excludes(eventQueue);
    EventQueue storedEventQueue = mapping.queueFor(component);
    assertThat(storedEventQueue).isSameAs(eventQueue);
  }
}
