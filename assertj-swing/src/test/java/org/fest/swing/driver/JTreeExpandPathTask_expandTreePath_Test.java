/*
 * Created on Aug 19, 2008
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
import static org.fest.swing.driver.JTreeExpandedPathQuery.isExpanded;
import static org.fest.swing.driver.JTreeSetRootVisibleTask.setRootVisible;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Arrays.array;

import java.awt.Dimension;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
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
  public void should_expand_path() {
    TreePath rootPath = new TreePath(window.root);
    assertThat(isExpanded(tree, rootPath)).isFalse();
    JTreeExpandPathTask.expandTreePath(tree, rootPath);
    robot.waitForIdle();
    assertThat(isExpanded(tree, rootPath)).isTrue();
  }

  @Test
  public void should_expand_path_if_root_is_invisible() {
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
