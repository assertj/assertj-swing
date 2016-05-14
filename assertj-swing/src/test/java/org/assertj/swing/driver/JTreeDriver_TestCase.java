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

import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.swing.TreeNodeFactory.node;
import static org.assertj.swing.test.task.ComponentSetEnabledTask.disable;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestTree;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;

/**
 * Base test case for {@link JTreeDriver}.
 *
 * @author Alex Ruiz
 */
public abstract class JTreeDriver_TestCase extends RobotBasedTestCase {
  MyWindow window;
  JTree tree;
  JTreeDriver driver;

  @Rule
  public ExpectedException thrown = none();

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew(getClass());
    tree = window.tree;
    driver = new JTreeDriver(robot);
    extraSetUp();
  }

  void extraSetUp() {
  }

  @RunsInEDT
  final void disableTree() {
    disable(tree);
    robot.waitForIdle();
  }

  final void showWindow() {
    robot.showWindow(window);
  }

  @RunsInEDT
  final String textOf(TreePath path) {
    return textOf(path, driver.separator());
  }

  @RunsInEDT
  private static String textOf(final TreePath path, final String separator) {
    return execute(() -> pathText(path, separator));
  }

  @RunsInCurrentThread
  static String pathText(TreePath path, String separator) {
    if (path == null) {
      return null;
    }
    Object[] values = path.getPath();
    StringBuilder b = new StringBuilder();
    for (int i = 0; i < values.length; i++) {
      if (i != 0) {
        b.append(separator);
      }
      Object value = values[i];
      if (value instanceof DefaultMutableTreeNode) {
        b.append(((DefaultMutableTreeNode) value).getUserObject());
      }
    }
    return b.toString();
  }

  public static final Object[][] outOfBoundRowIndices() {
    return new Object[][] { { -1 }, { 6 }, { 100 } };
  }

  @RunsInEDT
  final DefaultMutableTreeNode firstChildInRoot() {
    return firstChildInRootOf(tree);
  }

  @RunsInEDT
  private static DefaultMutableTreeNode firstChildInRootOf(final JTree tree) {
    return execute(() -> {
      TreeNode root = (TreeNode) tree.getModel().getRoot();
      return (DefaultMutableTreeNode) root.getChildAt(0);
    });
  }

  @RunsInEDT
  static int childCountOf(final TreeNode node) {
    return execute(() -> node.getChildCount());
  }

  @RunsInEDT
  static DefaultMutableTreeNode firstChildOf(final TreeNode node) {
    return execute(() -> (DefaultMutableTreeNode) node.getChildAt(0));
  }

  @RunsInEDT
  static DefaultMutableTreeNode firstChildOfRootIn(final JTree tree) {
    return execute(() -> {
      TreeNode root = (TreeNode) tree.getModel().getRoot();
      return (DefaultMutableTreeNode) root.getChildAt(0);
    });
  }

  @RunsInEDT
  static String textOf(final DefaultMutableTreeNode node) {
    return execute(() -> (String) node.getUserObject());
  }

  @RunsInEDT
  static DefaultMutableTreeNode rootOf(final JTree tree) {
    return execute(() -> (DefaultMutableTreeNode) tree.getModel().getRoot());
  }

  static class MyWindow extends TestWindow {
    static final Dimension TREE_SIZE = new Dimension(200, 100);

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
    }

    final TestTree tree = new TestTree(nodes());

    private static TreeModel nodes() {
      MutableTreeNode root = node("root",
                                  node("branch1", node("branch1.1", node("branch1.1.1"), node("branch1.1.2")),
                                       node("branch1.2")),
                                  node("branch2"), node("branch3"), node("branch4"), node("branch5", node("branch5.1")));
      return new DefaultTreeModel(root);
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      add(decorate(tree));
    }

    @RunsInCurrentThread
    static Component decorate(JTree tree) {
      JScrollPane scrollPane = new JScrollPane(tree);
      scrollPane.setPreferredSize(TREE_SIZE);
      return scrollPane;
    }
  }
}
