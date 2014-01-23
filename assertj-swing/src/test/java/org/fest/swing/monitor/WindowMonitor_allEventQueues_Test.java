/*
 * Created on Oct 18, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.monitor;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Lists.newArrayList;
import static org.mockito.Mockito.when;

import java.awt.EventQueue;
import java.util.List;

import org.junit.Test;

/**
 * Tests for {@link WindowMonitor#allEventQueues()}.
 *
 * @author Alex Ruiz
 */
public class WindowMonitor_allEventQueues_Test extends WindowMonitor_TestCase {
  @Test
  public void should_return_all_EventQueues() {
    List<EventQueue> allQueues = newArrayList();
    when(context.allEventQueues()).thenReturn(allQueues);
    assertThat(monitor.allEventQueues()).isSameAs(allQueues);
  }
}
