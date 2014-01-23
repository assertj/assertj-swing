/*
 * Created on Aug 23, 2008
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
import static org.fest.swing.driver.JTreeSetEditableTask.setEditable;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.data.BooleanProvider;
import org.fest.swing.test.swing.TestWindow;
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
  public void should_indicate_if_JTree_is_editable() {
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
