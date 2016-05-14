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
import static org.assertj.swing.util.Maps.newHashMap;

import java.awt.EventQueue;
import java.awt.Window;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for {@link WindowEventQueueMapping#removeMappingFor(java.awt.Component)}.
 *
 * @author Alex Ruiz
 */
public class WindowEventQueueMapping_removeMappingFor_Test extends WindowEventQueueMapping_withWindow_TestCase {
  @Test
  public void should_Remove_Component_From_Mapping() {
    mapping.addQueueFor(window);
    mapping.removeMappingFor(window);
    assertThat(queueMap).hasSize(1);
    assertThat(queueMap.keySet()).contains(eventQueue);
    Map<Window, Boolean> windowMapping = queueMap.get(eventQueue);
    assertThat(windowMapping).isEmpty();
  }

  @Test
  public void should_Remove_Component_From_All_Mappings() {
    EventQueue anotherEventQueue = new EventQueue();
    Map<Window, Boolean> windowMapping = newHashMap();
    windowMapping.put(window, true);
    queueMap.put(anotherEventQueue, windowMapping);
    mapping.removeMappingFor(window);
    assertThat(windowMapping).isEmpty();
  }
}
