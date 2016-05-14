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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.driver.JTextComponentTextQuery.textOf;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.query.ComponentHasFocusQuery.hasFocus;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.task.ComponentSetEnabledTask.disable;
import static org.assertj.swing.timing.Pause.pause;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.assertj.swing.test.util.StopWatch;
import org.junit.Rule;

/**
 * Base test case for {@link ComponentDriver}.
 * 
 * @author Alex Ruiz
 */
public abstract class ComponentDriver_TestCase extends RobotBasedTestCase {
  static final int TIME_TO_WAIT_FOR_FOCUS_GAIN = 2000;
  @Rule
  public ExpectedException thrown = none();
  ComponentDriver driver;
  MyWindow window;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew(getClass());
    driver = new ComponentDriver(robot);
    extraSetUp();
  }

  void extraSetUp() {
  }

  final void showWindow() {
    robot.showWindow(window);
  }

  @RunsInEDT
  final void disableButton() {
    disableInEDT(window.button);
  }

  @RunsInEDT
  final void disableTextField() {
    disableInEDT(window.textField);
  }

  @RunsInEDT
  private void disableInEDT(Component c) {
    disable(c);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void assertThatTextFieldIsEmpty() {
    assertThat(textOf(window.textField)).isEmpty();
  }

  @RunsInEDT
  final void assertThatButtonHasFocus() {
    assertThatButtonHasFocus(true);
  }

  @RunsInEDT
  final void assertThatButtonDoesNotHaveFocus() {
    assertThatButtonHasFocus(false);
  }

  @RunsInEDT
  final void assertThatButtonHasFocus(boolean expected) {
    assertThat(hasFocus(window.button)).isEqualTo(expected);
  }

  @RunsInEDT
  final void assertThatTextInTextFieldIs(String expected) {
    assertThat(textOf(window.textField)).isEqualTo(expected);
  }

  final void assertThatWaited(StopWatch stopWatch, long minimumWaitedTime) {
    long ellapsedTimeInMs = stopWatch.ellapsedTime();
    assertThat(ellapsedTimeInMs).isGreaterThanOrEqualTo(minimumWaitedTime);
  }

  static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
    }

    final JTextField textField = new JTextField(20);
    final MyButton button = new MyButton("Click Me");

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(textField, button);
    }
  }

  static class MyButton extends JButton {
    private boolean waitToRequestFocus;

    MyButton(String text) {
      super(text);
    }

    void waitToRequestFocus() {
      waitToRequestFocus = true;
    }

    @Override
    public boolean requestFocusInWindow() {
      if (waitToRequestFocus) {
        pause(TIME_TO_WAIT_FOR_FOCUS_GAIN);
      }
      return super.requestFocusInWindow();
    }
  }
}
