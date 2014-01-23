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
import static org.fest.swing.monitor.TestWindows.singletonWindowsMock;
import static org.fest.swing.util.TestRobotFactories.newRobotFactoryMock;
import static org.mockito.Mockito.when;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Window;

import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.util.RobotFactory;
import org.junit.Test;

/**
 * Tests for {@link WindowStatus#checkIfReady(Window)}.
 *
 * @author Alex Ruiz
 */
public class WindowStatus_checkIfReady_withNullRobot_Test extends SequentialEDTSafeTestCase {
  private TestWindow window;
  private Windows windows;
  private RobotFactory factory;

  @Override
  protected final void onSetUp() {
    window = TestWindow.createNewWindow(getClass());
    windows = singletonWindowsMock();
    factory = newRobotFactoryMock();
  }

  @Override
  protected final void onTearDown() {
    window.destroy();
  }

  @Test
  public void should_not_check_if_Frame_is_ready_if_Robot_is_Null() throws AWTException {
    Point before = MouseInfo.getPointerInfo().getLocation();
    when(factory.newRobotInPrimaryScreen()).thenReturn(null);
    WindowStatus status = new WindowStatus(windows, factory);
    status.checkIfReady(window);
    // mouse pointer should not have moved
    assertThat(MouseInfo.getPointerInfo().getLocation()).isEqualTo(before);
  }
}
