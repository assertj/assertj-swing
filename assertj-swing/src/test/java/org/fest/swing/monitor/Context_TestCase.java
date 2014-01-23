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

import static org.fest.swing.test.awt.Toolkits.newToolkitStub;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.awt.EventQueue;

import org.fest.swing.test.awt.ToolkitStub;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.After;
import org.junit.Before;

/**
 * Base test case for {@link Context}.
 *
 * @author Alex Ruiz
 */
public abstract class Context_TestCase extends EDTSafeTestCase {
  private ToolkitStub toolkit;

  EventQueue eventQueue;
  WindowEventQueueMapping windowEventQueueMapping;
  EventQueueMapping eventQueueMapping;
  TestWindow window;
  Context context;

  @Before
  public final void setUp() {
    eventQueue = new EventQueue();
    toolkit = newToolkitStub(eventQueue);
    window = TestWindow.createNewWindow(getClass());
    windowEventQueueMapping = mock(WindowEventQueueMapping.class);
    eventQueueMapping = mock(EventQueueMapping.class);
    createContext();
  }

  private void createContext() {
    context = new Context(toolkit, windowEventQueueMapping, eventQueueMapping);
    verify(windowEventQueueMapping).addQueueFor(toolkit);
    reset(windowEventQueueMapping);
  }

  @After
  public final void tearDown() {
    window.destroy();
  }
}
