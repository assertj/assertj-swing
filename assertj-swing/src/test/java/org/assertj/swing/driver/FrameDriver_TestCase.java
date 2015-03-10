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
package org.assertj.swing.driver;

import static org.assertj.swing.query.ComponentLocationOnScreenQuery.locationOnScreen;
import static org.assertj.swing.query.ComponentSizeQuery.sizeOf;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.task.ComponentSetEnabledTask.disable;
import static org.assertj.swing.test.task.FrameSetResizableTask.setResizable;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.annotation.ThreadSafeAction;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.awt.FluentDimension;
import org.assertj.swing.test.awt.FluentPoint;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;

/**
 * Base test case for {@link FrameDriver}.
 * 
 * @author Alex Ruiz
 */
public abstract class FrameDriver_TestCase extends RobotBasedTestCase {
  TestWindow window;
  FrameDriver driver;

  @Rule
  public ExpectedException thrown = none();

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
