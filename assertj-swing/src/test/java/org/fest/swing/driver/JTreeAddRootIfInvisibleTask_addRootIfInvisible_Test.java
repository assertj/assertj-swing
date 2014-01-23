/*
 * Created on Aug 6, 2009
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.swing.TreeNodeFactory.node;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestTree;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JTreeAddRootIfInvisibleTask#addRootIfInvisible(javax.swing.JTree, javax.swing.tree.TreePath)}.
 *
 * @author Alex Ruiz
 */
public class JTreeAddRootIfInvisibleTask_addRootIfInvisible_Test extends RobotBasedTestCase {
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew(getClass());
  }

  @Test
  public void should_not_add_root_to_path_if_root_is_invisible_but_path_already_contains_root() {
    TreePath treePath = new TreePath(window.root);
    TreePath newTreePath = JTreeAddRootIfInvisibleTask.addRootIfInvisible(window.tree, treePath);
    assertThat(newTreePath).isEqualTo(treePath);
  }

  static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    final MutableTreeNode root = createRoot();
    final TestTree tree = new TestTree(nodes(root));

    private static MutableTreeNode createRoot() {
      MutableTreeNode root = node("root", node("branch1", node("branch1.1"), node("branch1.2")), node("branch2"));
      return root;
    }

    private static TreeModel nodes(MutableTreeNode root) {
      return new DefaultTreeModel(root);
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      tree.setRootVisible(false);
      add(decorate(tree));
    }

    private static Component decorate(JTree tree) {
      JScrollPane scrollPane = new JScrollPane(tree);
      scrollPane.setPreferredSize(new Dimension(200, 100));
      return scrollPane;
    }
  }
}
