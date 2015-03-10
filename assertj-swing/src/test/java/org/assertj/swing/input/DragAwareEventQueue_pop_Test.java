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
package org.assertj.swing.input;

import java.awt.EventQueue;
import java.util.EmptyStackException;

import org.junit.Test;

/**
 * Tests for {@link DragAwareEventQueue#pop()}.
 * 
 * @author Alex Ruiz
 */
public class DragAwareEventQueue_pop_Test extends DragAwareEventQueue_TestCase {
  @Test(expected = EmptyStackException.class)
  public void should_Pop_If_SystemEventQueue_Is_Same_As_Queue_Under_Test() {
    queue.pop();
  }

  @Test
  public void should_Not_Pop_If_SystemEventQueue_Is_Not_Same_As_Queue_Under_Test() {
    toolkit.eventQueue(new EventQueue());
    queue.pop(); // if really pops should throw an EmptyStackException
  }
}
