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
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.task.DialogSetModalTask.makeModal;

import java.awt.Dimension;
import java.util.Collection;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.data.BooleanProvider;
import org.assertj.swing.test.swing.TestDialog;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link DialogModalQuery#isModal(java.awt.Dialog)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class DialogModalQuery_isModal_Test extends RobotBasedTestCase {
  private MyDialog dialog;

  private final boolean modal;

  @Parameters
  public static Collection<Object[]> booleans() {
    return newArrayList(BooleanProvider.booleans());
  }

  public DialogModalQuery_isModal_Test(boolean modal) {
    this.modal = modal;
  }

  @Override
  protected void onSetUp() {
    dialog = MyDialog.createNew();
  }

  @Test
  public void should_Indicate_If_Dialog_Is_Modal() {
    makeModal(dialog, modal);
    robot.waitForIdle();
    dialog.startRecording();
    assertThat(DialogModalQuery.isModal(dialog)).isEqualTo(modal);
    dialog.requireInvoked("isModal");
  }

  private static class MyDialog extends TestDialog {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    @RunsInEDT
    static MyDialog createNew() {
      return execute(new GuiQuery<MyDialog>() {
        @Override
        protected MyDialog executeInEDT() {
          return new MyDialog();
        }
      });
    }

    private MyDialog() {
      super(TestWindow.createNewWindow(DialogModalQuery_isModal_Test.class));
      setPreferredSize(new Dimension(200, 100));
    }

    void startRecording() {
      recording = true;
    }

    @Override
    public boolean isModal() {
      if (recording) {
        methodInvocations.invoked("isModal");
      }
      return super.isModal();
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
