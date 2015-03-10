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
import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.driver.JSpinnerValueQuery.valueOf;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.task.ComponentSetEnabledTask.disable;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;

/**
 * Base test case for {@link JSpinnerDriver}.
 * 
 * @author Alex Ruiz
 */
public abstract class JSpinnerDriver_TestCase extends RobotBasedTestCase {
  MyWindow window;
  JSpinner spinner;
  JSpinnerDriver driver;
  @Rule
  public ExpectedException thrown = none();

  @Override
  protected final void onSetUp() {
    driver = new JSpinnerDriver(robot);
    window = MyWindow.createNew();
    spinner = window.spinner;
  }

  final void showWindow() {
    robot.showWindow(window);
  }

  @RunsInEDT
  final void assertThatFirstValueIsSelected() {
    assertThatValueIs("Frodo");
  }

  @RunsInEDT
  final void setJLabelAsEditor() {
    setJLabelAsEditorIn(spinner);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setJLabelAsEditorIn(final JSpinner spinner) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        spinner.setEditor(new JLabel());
      }
    });
  }

  @RunsInEDT
  final void selectLastValue() {
    updateValue("Gandalf");
    robot.waitForIdle();
  }

  @RunsInEDT
  final void updateValue(Object value) {
    setValue(spinner, value);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setValue(final JSpinner spinner, final Object value) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        spinner.setValue(value);
      }
    });
  }

  @RunsInEDT
  final void assertThatLastValueIsSelected() {
    assertThatValueIs("Gandalf");
  }

  @RunsInEDT
  final void assertThatValueIs(Object expected) {
    assertThat(valueOf(spinner)).isEqualTo(expected);
  }

  @RunsInEDT
  final void disableSpinner() {
    disable(spinner);
    robot.waitForIdle();
  }

  private static class MyWindow extends TestWindow {
    final JSpinner spinner = new JSpinner(new SpinnerListModel(array("Frodo", "Sam", "Gandalf")));

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JSpinnerDriver_TestCase.class);
      add(spinner);
      setPreferredSize(new Dimension(160, 80));
    }
  }
}
