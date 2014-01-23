/*
 * Created on Aug 8, 2008
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

import javax.swing.JFileChooser;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JFileChooserApproveButtonTextQuery#approveButtonTextFrom(JFileChooser)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JFileChooserApproveButtonTextQuery_approveButtonTextFrom_Test extends RobotBasedTestCase {
  private MyFileChooser fileChooser;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    fileChooser = window.fileChooser;
  }

  @Test
  public void should_return_text_for_approve_button_from_JFileChooser() {
    fileChooser.startRecording();
    String approveButtonText = JFileChooserApproveButtonTextQuery.approveButtonTextFrom(fileChooser);
    assertThat(approveButtonText).isNotEmpty();
    fileChooser.requireInvoked("getApproveButtonText");
  }

  @Test
  public void should_return_text_for_approve_button_from_UI_if_JFileChooser_does_not_have_it() {
    fileChooser.shouldReturnNullAsApproveButtonText(true);
    fileChooser.startRecording();
    String approveButtonText = JFileChooserApproveButtonTextQuery.approveButtonTextFrom(fileChooser);
    assertThat(approveButtonText).isNotEmpty();
    fileChooser.requireInvoked("getApproveButtonText");
  }

  private static class MyWindow extends TestWindow {
    final MyFileChooser fileChooser = new MyFileChooser();

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
      super(JFileChooserApproveButtonTextQuery_approveButtonTextFrom_Test.class);
      addComponents(fileChooser);
    }
  }

  private static class MyFileChooser extends JFileChooser {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    private boolean returnNullAsApproveButtonText;

    void startRecording() {
      recording = true;
    }

    void shouldReturnNullAsApproveButtonText(boolean b) {
      returnNullAsApproveButtonText = b;
    }

    @Override
    public String getApproveButtonText() {
      if (recording) {
        methodInvocations.invoked("getApproveButtonText");
      }
      if (returnNullAsApproveButtonText) {
        return null;
      }
      return super.getApproveButtonText();
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
