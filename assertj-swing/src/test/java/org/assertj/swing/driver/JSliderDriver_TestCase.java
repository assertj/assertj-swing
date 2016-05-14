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

import static javax.swing.SwingConstants.HORIZONTAL;
import static javax.swing.SwingConstants.VERTICAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.driver.JSliderValueQuery.valueOf;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.task.ComponentSetEnabledTask.disable;

import java.awt.Dimension;

import javax.swing.JSlider;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Base test case for {@link JSliderDriver}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public abstract class JSliderDriver_TestCase extends RobotBasedTestCase {
  MyWindow window;
  JSlider slider;
  JSliderDriver driver;

  @Rule
  public ExpectedException thrown = none();

  final int orientation;

  public JSliderDriver_TestCase(int orientation) {
    this.orientation = orientation;
  }

  static Object[][] orientations() {
    return new Object[][] { { HORIZONTAL }, { VERTICAL } };
  }

  @Override
  protected final void onSetUp() {
    driver = new JSliderDriver(robot);
    window = MyWindow.createNew(getClass(), orientation);
    slider = window.slider;
  }

  final void showWindow() {
    robot.showWindow(window);
  }

  @RunsInEDT
  final void assertThatSliderValueIs(int expected) {
    assertThat(valueOf(slider)).isEqualTo(expected);
  }

  @RunsInEDT
  final void disableSlider() {
    disable(slider);
    robot.waitForIdle();
  }

  static class MyWindow extends TestWindow {
    final JSlider slider = new JSlider();

    @RunsInEDT
    static MyWindow createNew(final Class<? extends JSliderDriver_TestCase> testClass, final int orientation) {
      return execute(() -> new MyWindow(testClass, orientation));
    }

    private MyWindow(Class<? extends JSliderDriver_TestCase> testClass, int orientation) {
      super(testClass);
      add(slider);
      slider.setOrientation(orientation);
      slider.setMinimum(0);
      slider.setMaximum(30);
      slider.setValue(15);
      setPreferredSize(new Dimension(300, 300));
    }
  }
}
