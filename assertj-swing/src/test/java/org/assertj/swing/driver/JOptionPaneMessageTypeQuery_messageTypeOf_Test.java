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

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.JOptionPane;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.junit.Test;

/**
 * Tests for {@link JOptionPaneMessageTypeQuery#messageTypeOf(JOptionPane)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JOptionPaneMessageTypeQuery_messageTypeOf_Test extends RobotBasedTestCase {
  private MyOptionPane optionPane;

  @Override
  protected void onSetUp() {
    optionPane = MyOptionPane.createNew();
  }

  @Test
  public void should_Return_Message_Type_Of_JOptionPane() {
    optionPane.startRecording();
    assertThat(JOptionPaneMessageTypeQuery.messageTypeOf(optionPane)).isEqualTo(INFORMATION_MESSAGE);
    optionPane.requireInvoked("getMessageType");
  }

  private static class MyOptionPane extends JOptionPane {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    @RunsInEDT
    static MyOptionPane createNew() {
      return execute(() -> new MyOptionPane());
    }

    private MyOptionPane() {
      super("Hello World", INFORMATION_MESSAGE);
    }

    @Override
    public int getMessageType() {
      if (recording) {
        methodInvocations.invoked("getMessageType");
      }
      return super.getMessageType();
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
