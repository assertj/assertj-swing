/*
 * Created on Feb 24, 2008
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
package org.fest.swing.driver;

import static org.fest.swing.query.ComponentLocationOnScreenQuery.locationOnScreen;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.FrameSetResizableTask.setResizable;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.annotation.ThreadSafeAction;
import org.fest.swing.test.awt.FluentDimension;
import org.fest.swing.test.awt.FluentPoint;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;

/**
 * Base test case for {@link FrameDriver}.
 * 
 * @author Alex Ruiz
 */
public abstract class FrameDriver_TestCase extends RobotBasedTestCase {
  TestWindow window;
  FrameDriver driver;

  @Override
  protected final void onSetUp() {
    window = TestWindow.createNewWindow(getClass());
    driver = new FrameDriver(robot);
  }

  @ThreadSafeAction
  final int frameState() {
    return window.getExtendedState();
  }

  @RunsInEDT
  final FluentDimension windowSize() {
    return new FluentDimension(sizeOf(window));
  }

  @RunsInEDT
  final FluentPoint windowLocationOnScreen() {
    return new FluentPoint(locationOnScreen(window));
  }

  @RunsInEDT
  final void makeWindowNotResizable() {
    showWindow();
    makeNotResizable(window);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void makeNotResizable(final TestWindow window) {
    setResizable(window, false);
  }

  @RunsInEDT
  final void disableWindow() {
    disable(window);
    robot.waitForIdle();
  }

  final void showWindow() {
    robot.showWindow(window);
  }
}
