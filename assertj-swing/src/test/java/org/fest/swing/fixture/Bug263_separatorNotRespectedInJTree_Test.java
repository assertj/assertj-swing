/*
 * Created on Dec 17, 2008
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
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=263">Bug 263</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug263_separatorNotRespectedInJTree_Test extends RobotBasedTestCase {
  private JTreeFixture treeFixture;
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    treeFixture = new JTreeFixture(robot, window.tree);
    robot.showWindow(window);
  }

  @Test
  public void should_use_specified_path_separator() {
    treeFixture.replaceSeparator("*");
    assertThat(treeFixture.separator()).isEqualTo("*");
    treeFixture.selectPath("root*node1*node11*node111");
    Object selection = treeFixture.target().getSelectionPath().getLastPathComponent();
    assertThat(selection).isSameAs(window.nodeToSelect);
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

    final JTree tree = new JTree();
    final MutableTreeNode nodeToSelect = node("node111");

    private MyWindow() {
      super(Bug263_separatorNotRespectedInJTree_Test.class);
      tree.setModel(new DefaultTreeModel(root()));
      tree.setPreferredSize(new Dimension(200, 200));
      add(tree);
    }

    private MutableTreeNode root() {
      return node("root", node("node1", node("node11", nodeToSelect, node("node112"))));
    }
  }
}
