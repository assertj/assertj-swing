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

import java.awt.EventQueue;

import org.junit.Before;

/**
 * Base test case for {@link DragAwareEventNormalizer}.
 * 
 * @author Alex Ruiz
 */
public abstract class DragAwareEventNormalizer_TestCase extends EventNormalizer_TestCase {
  DragAwareEventNormalizer eventNormalizer;

  @Before
  public void setUp() {
    eventNormalizer = new DragAwareEventNormalizer();
  }

  static class EventQueueStub extends EventQueue {
    EventQueue pushedEventQueue;

    EventQueueStub() {
    }

    @Override
    public synchronized void push(EventQueue newEventQueue) {
      this.pushedEventQueue = newEventQueue;
      super.push(newEventQueue);
    }
  }
}
