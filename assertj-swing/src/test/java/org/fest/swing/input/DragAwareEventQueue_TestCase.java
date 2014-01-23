/*
 * Created on Apr 4, 2008
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

import static org.fest.swing.test.awt.TestAWTEventListeners.singletonAWTEventListenerMock;
import static org.fest.swing.test.awt.Toolkits.newToolkitStub;

import java.awt.AWTEvent;
import java.awt.ActiveEvent;

import org.fest.swing.test.awt.ToolkitStub;
import org.junit.Before;

/**
 * Base test case for {@link DragAwareEventQueue}.
 * 
 * @author Alex Ruiz
 */
public abstract class DragAwareEventQueue_TestCase {
  private long mask;

  ToolkitStub toolkit;
  DragAwareEventQueue queue;

  @Before
  public final void setUp() {
    toolkit = newToolkitStub();
    queue = new DragAwareEventQueue(toolkit, mask, singletonAWTEventListenerMock());
    toolkit.eventQueue(queue);
  }

  static class MyEvent extends AWTEvent implements ActiveEvent {
    boolean dispatched;

    MyEvent() {
      super(new Object(), 0);
    }

    @Override
    public void dispatch() {
      dispatched = true;
    }
  }
}
