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
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.test.task.AbstractButtonSetSelectedTask.setSelected;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.swing.JCheckBox;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.data.BooleanProvider;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link AbstractButtonSelectedQuery#isSelected(javax.swing.AbstractButton)}.
 * 
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class AbstractButtonSelectedQuery_isSelected_Test extends RobotBasedTestCase {
  private MyCheckBox checkBox;

  private final boolean selected;

  @Parameters
  public static @Nonnull Collection<Object[]> booleans() {
    return newArrayList(BooleanProvider.booleans());
  }

  public AbstractButtonSelectedQuery_isSelected_Test(boolean selected) {
    this.selected = selected;
  }

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    checkBox = window.checkBox;
  }

  @Test
  public void should_Indicate_If_AbstractButton_Is_Selected() {
    setSelected(checkBox, selected);
    robot.waitForIdle();
    checkBox.startRecording();
    boolean isSelected = AbstractButtonSelectedQuery.isSelected(checkBox);
    assertThat(isSelected).isEqualTo(selected);
    checkBox.requireInvoked("isSelected");
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return GuiActionRunner.execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final MyCheckBox checkBox = new MyCheckBox("A Button");

    private MyWindow() {
      super(AbstractButtonSelectedQuery_isSelected_Test.class);
      addComponents(checkBox);
    }
  }

  private static class MyCheckBox extends JCheckBox {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyCheckBox(String text) {
      super(text);
    }

    void startRecording() {
      recording = true;
    }

    @Override
    public boolean isSelected() {
      if (recording) {
        methodInvocations.invoked("isSelected");
      }
      return super.isSelected();
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
