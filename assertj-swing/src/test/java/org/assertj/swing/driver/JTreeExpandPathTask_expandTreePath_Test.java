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
import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.driver.JTreeExpandedPathQuery.isExpanded;
import static org.assertj.swing.driver.JTreeSetRootVisibleTask.setRootVisible;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JTreeExpandPathTask#expandTreePath(JTree, TreePath)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JTreeExpandPathTask_expandTreePath_Test extends RobotBasedTestCase {
  private MyWindow window;
  private JTree tree;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    tree = window.tree;
  }

  @Test
  public void should_Expand_Path() {
    TreePath rootPath = new TreePath(window.root);
    assertThat(isExpanded(tree, rootPath)).isFalse();
    JTreeExpandPathTask.expandTreePath(tree, rootPath);
    robot.waitForIdle();
    assertThat(isExpanded(tree, rootPath)).isTrue();
  }

  @Test
  public void should_Expand_Path_If_Root_Is_Invisible() {
    hideRoot();
    TreePath nodePath = new TreePath(array(window.root, window.node));
    assertThat(isExpanded(tree, nodePath)).isFalse();
    JTreeExpandPathTask.expandTreePath(tree, new TreePath(window.node));
    robot.waitForIdle();
    assertThat(isExpanded(tree, nodePath)).isTrue();
  }

  @RunsInEDT
  private void hideRoot() {
    setRootVisible(tree, false);
    robot.waitForIdle();
  }

  private static class MyWindow extends TestWindow {
    final JTree tree;
    final DefaultMutableTreeNode root;
    final DefaultMutableTreeNode node;

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
      super(JTreeExpandPathTask_expandTreePath_Test.class);
      root = new DefaultMutableTreeNode("root");
      node = new DefaultMutableTreeNode("node");
      node.add(new DefaultMutableTreeNode("node1"));
      tree = new JTree(root);
      root.add(node);
      tree.setPreferredSize(new Dimension(300, 200));
      addComponents(tree);
      tree.collapsePath(new TreePath(root));
    }
  }
}
