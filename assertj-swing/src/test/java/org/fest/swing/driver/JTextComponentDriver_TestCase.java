/*
 * Created on Jul 17, 2009
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JTextComponentSelectedTextQuery.selectedTextOf;
import static org.fest.swing.driver.JTextComponentSetEditableTask.setTextFieldEditable;
import static org.fest.swing.driver.JTextComponentTextQuery.textOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;

import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;

/**
 * Base test case for {@link JTextComponentDriver}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class JTextComponentDriver_TestCase extends RobotBasedTestCase {
  MyWindow window;
  JTextField textField;
  JTextComponentDriver driver;

  @Override
  protected final void onSetUp() {
    driver = new JTextComponentDriver(robot);
    window = MyWindow.createNew(getClass());
    textField = window.textField;
    extraSetUp();
  }

  void extraSetUp() {}

  @RunsInEDT
  final void requireSelectedTextInTextField(String expected) {
    assertThat(selectedTextOf(textField)).isEqualTo(expected);
  }

  @RunsInEDT
  final void requireTextInTextField(String expected) {
    assertThat(textOf(textField)).isEqualTo(expected);
  }

  @RunsInEDT
  final void requireEmptyTextField() {
    assertThat(textOf(textField)).isNullOrEmpty();
  }

  @RunsInEDT
  final void disableTextField() {
    disable(textField);
    robot.waitForIdle();
  }

  final void showWindow() {
    robot.showWindow(window);
  }

  @RunsInEDT
  final void clearTextField() {
    setTextFieldText("");
  }

  @RunsInEDT
  final void setTextFieldText(String text) {
    setText(textField, text);
    robot.waitForIdle();
  }

  @RunsInEDT
  static void setText(final JTextField textField, final String text) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        textField.setText(text);
      }
    });
  }

  @RunsInEDT
  final void makeTextFieldEditable() {
    setTextFieldEditable(textField, true);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void makeTextFieldNotEditable() {
    setTextFieldEditable(textField, false);
    robot.waitForIdle();
  }

  static class MyWindow extends TestWindow {
    final JTextField textField = new JTextField("This is a test");

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(textField);
    }
  }
}
