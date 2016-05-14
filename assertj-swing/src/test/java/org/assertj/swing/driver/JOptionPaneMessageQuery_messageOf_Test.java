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

import javax.swing.JOptionPane;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.junit.Test;

/**
 * Tests for {@link JOptionPaneMessageQuery#messageOf(JOptionPane)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JOptionPaneMessageQuery_messageOf_Test extends RobotBasedTestCase {
  private static final String MESSAGE = "Hello World";

  private MyOptionPane optionPane;

  @Override
  protected void onSetUp() {
    optionPane = MyOptionPane.createNew(MESSAGE);
  }

  @Test
  public void should_Return_Message_Of_JOptionPane() {
    optionPane.startRecording();
    assertThat(JOptionPaneMessageQuery.messageOf(optionPane)).isEqualTo(MESSAGE);
    optionPane.requireInvoked("getMessage");
  }

  private static class MyOptionPane extends JOptionPane {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    @RunsInEDT
    static MyOptionPane createNew(final String message) {
      return execute(() -> new MyOptionPane(message));
    }

    private MyOptionPane(String message) {
      super(message, INFORMATION_MESSAGE);
    }

    @Override
    public Object getMessage() {
      if (recording) {
        methodInvocations.invoked("getMessage");
      }
      return super.getMessage();
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
