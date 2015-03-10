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
import static org.assertj.swing.driver.JComboBoxSetEditableTask.setEditable;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.util.Collection;

import javax.swing.JComboBox;

import org.assertj.swing.annotation.RunsInEDT;
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
 * Tests for {@link JComboBoxEditableQuery#isEditable(JComboBox)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class JComboBoxEditableQuery_isEditable_Test extends RobotBasedTestCase {
  private MyComboBox comboBox;

  private final boolean editable;

  @Parameters
  public static Collection<Object[]> booleans() {
    return newArrayList(BooleanProvider.booleans());
  }

  public JComboBoxEditableQuery_isEditable_Test(boolean editable) {
    this.editable = editable;
  }

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    comboBox = window.comboBox;
  }

  @Test
  public void should_Indicate_If_JComboBox_Is_Editable() {
    setEditable(comboBox, editable);
    robot.waitForIdle();
    comboBox.startRecording();
    assertThat(JComboBoxEditableQuery.isEditable(comboBox)).isEqualTo(editable);
    comboBox.requireInvoked("isEditable");
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

    final MyComboBox comboBox = new MyComboBox("one", "two", "three");

    private MyWindow() {
      super(JComboBoxEditableQuery_isEditable_Test.class);
      add(comboBox);
    }
  }

  private static class MyComboBox extends JComboBox {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyComboBox(Object... items) {
      super(items);
    }

    void startRecording() {
      recording = true;
    }

    @Override
    public boolean isEditable() {
      if (recording) {
        methodInvocations.invoked("isEditable");
      }
      return super.isEditable();
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
