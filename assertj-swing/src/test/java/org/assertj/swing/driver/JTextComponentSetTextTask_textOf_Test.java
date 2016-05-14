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
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.core.MethodInvocations.Args.args;

import javax.swing.JTextField;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.MethodInvocations.Args;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JTextComponentSetTextTask#setTextIn(javax.swing.text.JTextComponent, String)}.
 * 
 * @author Alex Ruiz
 */
public class JTextComponentSetTextTask_textOf_Test extends RobotBasedTestCase {
  static final String TEXTBOX_TEXT = "Hello World";

  private MyTextField textField;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    textField = window.textField;
  }

  @Test
  public void should_Set_Text_In_JTextComponent() {
    textField.startRecording();
    JTextComponentSetTextTask.setTextIn(textField, TEXTBOX_TEXT);
    robot.waitForIdle();
    assertThat(textOf(textField)).isEqualTo(TEXTBOX_TEXT);
    textField.requireInvoked("setText", args(TEXTBOX_TEXT));
  }

  private static String textOf(final MyTextField textField) {
    return execute(() -> textField.getText());
  }

  private static class MyWindow extends TestWindow {
    final MyTextField textField = new MyTextField();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    private MyWindow() {
      super(JTextComponentSetTextTask_textOf_Test.class);
      add(textField);
    }
  }

  private static class MyTextField extends JTextField {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyTextField() {
      super(20);
    }

    @Override
    public void setText(String text) {
      if (recording) {
        methodInvocations.invoked("setText", args(text));
      }
      super.setText(text);
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName, Args args) {
      return methodInvocations.requireInvoked(methodName, args);
    }
  }
}
