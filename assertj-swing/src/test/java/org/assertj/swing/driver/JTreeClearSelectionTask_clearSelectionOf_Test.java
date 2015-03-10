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

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JTreeClearSelectionTask#clearSelectionOf(JTree)}.
 * 
 * @author Alex Ruiz
 */
public class JTreeClearSelectionTask_clearSelectionOf_Test extends RobotBasedTestCase {
  static final String TEXTBOX_TEXT = "Hello World";

  private MyTree tree;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    tree = window.tree;
  }

  @Test
  public void should_Clear_Selection_In_JTree() {
    requireSelectionCount(1);
    tree.startRecording();
    JTreeClearSelectionTask.clearSelectionOf(tree);
    robot.waitForIdle();
    requireSelectionCount(0);
    tree.requireInvoked("clearSelection");
  }

  @RunsInEDT
  private void requireSelectionCount(int expected) {
    assertThat(selectionCountOf(tree)).isEqualTo(expected);
  }

  @RunsInEDT
  private static int selectionCountOf(final MyTree tree) {
    return execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return tree.getSelectionCount();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    final MyTree tree = new MyTree();

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
      super(JTreeClearSelectionTask_clearSelectionOf_Test.class);
      add(tree);
    }
  }

  private static class MyTree extends JTree {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyTree() {
      super(new DefaultMutableTreeNode("root"));
      setSelectionRow(0);
    }

    @Override
    public void clearSelection() {
      if (recording) {
        methodInvocations.invoked("clearSelection");
      }
      super.clearSelection();
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
