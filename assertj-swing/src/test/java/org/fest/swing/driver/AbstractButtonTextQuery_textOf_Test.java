/*
 * Created on Aug 6, 2008
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

import javax.swing.JButton;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link AbstractButtonTextQuery#textOf(javax.swing.AbstractButton)}.
 *
 * @author Alex Ruiz
 */
public class AbstractButtonTextQuery_textOf_Test extends RobotBasedTestCase {
  private MyButton button;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    button = window.button;
  }

  @Test
  public void should_return_text_of_AbstractButton() {
    button.startRecording();
    assertThat(AbstractButtonTextQuery.textOf(button)).isEqualTo("A Button");
    button.requireInvoked("getText");
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

    final MyButton button = new MyButton("A Button");

    private MyWindow() {
      super(AbstractButtonTextQuery_textOf_Test.class);
      addComponents(button);
    }
  }

  private static class MyButton extends JButton {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    public MyButton(String text) {
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
