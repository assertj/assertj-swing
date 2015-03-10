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
import static org.assertj.swing.driver.JTreeSetEditableTask.setEditable;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.util.Collection;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

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
 * Tests for {@link JTreeEditableQuery#isEditable(JTree)}.
 * 
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class JTreeEditableQuery_isEditable_Test extends RobotBasedTestCase {
  private MyTree tree;
  private final boolean editable;

  @Parameters
  public static Collection<Object[]> booleans() {
    return newArrayList(BooleanProvider.booleans());
  }

  public JTreeEditableQuery_isEditable_Test(boolean editable) {
    this.editable = editable;
  }

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    tree = window.tree;
  }

  @Test
  public void should_Indicate_If_JTree_Is_Editable() {
    setEditable(tree, editable);
    robot.waitForIdle();
    tree.startRecording();
    assertThat(JTreeEditableQuery.isEditable(tree)).isEqualTo(editable);
    tree.requireInvoked("isEditable");
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

    final MyTree tree = new MyTree();

    private MyWindow() {
      super(JTreeEditableQuery_isEditable_Test.class);
      addComponents(tree);
    }
  }

  private static class MyTree extends JTree {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyTree() {
      super(new DefaultMutableTreeNode("root"));
    }

    @Override
    public boolean isEditable() {
      if (recording) {
        methodInvocations.invoked("isEditable");
      }
      return super.isEditable();
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
