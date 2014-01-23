/*
 * Created on Oct 11, 2007
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

import static java.awt.AWTEvent.MOUSE_EVENT_MASK;
import static java.awt.AWTEvent.MOUSE_MOTION_EVENT_MASK;
import static java.awt.AWTEvent.PAINT_EVENT_MASK;
import static org.fest.swing.monitor.TestWindows.newWindowsMock;
import static org.fest.swing.test.awt.Toolkits.newToolkitStub;

import org.fest.swing.test.awt.ToolkitStub;
import org.junit.Before;

/**
 * Base test case for {@link WindowAvailabilityMonitor}.
 * 
 * @author Alex Ruiz
 */
public abstract class WindowAvailabilityMonitor_TestCase {
  static final long EVENT_MASK = MOUSE_MOTION_EVENT_MASK | MOUSE_EVENT_MASK | PAINT_EVENT_MASK;

  WindowAvailabilityMonitor monitor;

  ToolkitStub toolkit;
  Windows windows;

  @Before
  public final void setUp() {
    toolkit = newToolkitStub();
    windows = newWindowsMock();
    monitor = new WindowAvailabilityMonitor(windows);
    onSetUp();
  }

  void onSetUp() {}
}
