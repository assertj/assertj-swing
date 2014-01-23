/*
 * Created on Feb 4, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.swing.TreeNodeFactory.node;

import java.awt.Dimension;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=293">Bug 293</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug293_errorWhenSelectingPathIfJTreeRootIsInvisible_Test extends RobotBasedTestCase {
  @Test
  public void should_select_path_if_root_is_invisible() {
    MyWindow window = MyWindow.createNewWithTreeRootInvisible();
    robot.showWindow(window);
    JTreeFixture treeFixture = new JTreeFixture(robot, window.tree);
    treeFixture.selectPath("node1/node11/node111");
    Object selection = treeFixture.target().getSelectionPath().getLastPathComponent();
    assertThat(selection).isSameAs(window.nodeToSelect);
  }

  @Test
  public void should_select_path_if_root_is_visible() {
    MyWindow window = MyWindow.createNewWithTreeRootVisible();
    robot.showWindow(window);
    JTreeFixture treeFixture = new JTreeFixture(robot, window.tree);
    treeFixture.selectPath("root/node1/node11/node111");
    Object selection = treeFixture.target().getSelectionPath().getLastPathComponent();
    assertThat(selection).isSameAs(window.nodeToSelect);
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNewWithTreeRootVisible() {
      return createNew(true);
    }

    @RunsInEDT
    static MyWindow createNewWithTreeRootInvisible() {
      return createNew(false);
    }

    @RunsInEDT
    private static MyWindow createNew(final boolean treeRootVisible) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(treeRootVisible);
        }
      });
    }

    final JTree tree = new JTree();
    final MutableTreeNode nodeToSelect = node("node111");

    private MyWindow(boolean treeRootVisible) {
      super(Bug293_errorWhenSelectingPathIfJTreeRootIsInvisible_Test.class);
      tree.setModel(new DefaultTreeModel(root()));
      tree.setPreferredSize(new Dimension(200, 200));
      tree.setRootVisible(treeRootVisible);
      add(tree);
    }

    private MutableTreeNode root() {
      return node("root", node("node1", node("node11", nodeToSelect, node("node112"))));
    }
  }
}
