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

import javax.swing.JLabel;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JLabelTextQuery#textOf(JLabel)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JLabelTextQuery_textOf_Test extends RobotBasedTestCase {
  private static final String TEXT = "Hello";

  private MyLabel label;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    label = window.label;
  }

  @Test
  public void should_Return_Text_Of_JLabel() {
    label.startRecording();
    assertThat(JLabelTextQuery.textOf(label)).isEqualTo(TEXT);
    label.requireInvoked("getText");
  }

  private static class MyWindow extends TestWindow {
    final MyLabel label = new MyLabel(TEXT);

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
      super(JLabelTextQuery_textOf_Test.class);
      addComponents(label);
    }
  }

  private static class MyLabel extends JLabel {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyLabel(String text) {
      super(text);
    }

    @Override
    public String getText() {
      if (recording) {
        methodInvocations.invoked("getText");
      }
      return super.getText();
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
