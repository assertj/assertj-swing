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
import static org.assertj.swing.driver.JTreeExpandedPathQuery.isExpanded;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JTreeToggleExpandStateTask#toggleExpandState(JTree, Point)}.
 * 
 * @author Yvonne Wang
 */
public class JTreeToggleExpandStateTask_toggleExpandState_Test extends RobotBasedTestCase {
  private JTree tree;
  private TreePath rootPath;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    tree = window.tree;
    rootPath = new TreePath(window.treeRoot);
  }

  @Test
  public void should_Toggle_Expand_State() {
    assertThat(isRootExpanded()).isFalse();
    toggleExpandState(tree, rootPath);
    robot.waitForIdle();
    assertThat(isRootExpanded()).isTrue();
  }

  @RunsInEDT
  private static void toggleExpandState(final JTree tree, final TreePath rootPath) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        Rectangle pathBounds = tree.getPathBounds(rootPath);
        Point p = new Point(pathBounds.x + pathBounds.width / 2, pathBounds.y + pathBounds.height / 2);
        JTreeToggleExpandStateTask.toggleExpandState(tree, p);
      }
    });
  }

  @RunsInEDT
  private boolean isRootExpanded() {
    return isExpanded(tree, rootPath);
  }

  private static class MyWindow extends TestWindow {
    final JTree tree;
    final TreeNode treeRoot;

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
      super(JTreeToggleExpandStateTask_toggleExpandState_Test.class);
      treeRoot = createRoot();
      tree = new JTree(treeRoot);
      tree.setPreferredSize(new Dimension(200, 100));
      addComponents(tree);
      tree.collapsePath(new TreePath(treeRoot));
    }

    private static TreeNode createRoot() {
      DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
      DefaultMutableTreeNode child = new DefaultMutableTreeNode("child");
      root.add(child);
      return root;
    }
  }
}
