/*
 * Created on Oct 11, 2008
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.MethodInvocations.Args.args;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.MethodInvocations.Args;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JSpinnerSetValueTask#setValue(JSpinner, Object)}.
 * 
 * @author Alex Ruiz
 */
public class JSpinnerSetValueTask_setValue_Test extends RobotBasedTestCase {
  private MyWindow window;
  private MySpinner spinner;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    spinner = window.spinner;
  }

  @Test
  public void should_set_value() {
    robot.showWindow(window);
    String value = "Three";
    spinner.startRecording();
    JSpinnerSetValueTask.setValue(spinner, value);
    robot.waitForIdle();
    assertThat(JSpinnerValueQuery.valueOf(spinner)).isEqualTo(value);
    spinner.requireInvoked("setValue", args(value));
  }

  @Test
  public void should_throw_error_if_JSpinner_is_disabled() {
    disable(spinner);
    robot.waitForIdle();
    try {
      JSpinnerSetValueTask.setValue(spinner, "Two");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JSpinner_is_not_showing_on_the_screen() {
    hide(window);
    robot.waitForIdle();
    try {
      JSpinnerSetValueTask.setValue(spinner, "Two");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final MySpinner spinner = new MySpinner("One", "Two", "Three");

    private MyWindow() {
      super(JSpinnerSetValueTask.class);
      spinner.setValue("Two");
      addComponents(spinner);
    }
  }

  private static class MySpinner extends JSpinner {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MySpinner(Object... values) {
      super(new SpinnerListModel(values));
    }

    @Override
    public void setValue(Object value) {
      if (recording) {
        methodInvocations.invoked("setValue", args(value));
      }
      super.setValue(value);
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName, Args args) {
      return methodInvocations.requireInvoked(methodName, args);
    }
  }
}
