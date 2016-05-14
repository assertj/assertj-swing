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
import static org.assertj.swing.driver.JTreeSetRootVisibleTask.setRootVisible;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.swing.TreeNodeFactory.node;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.exception.LocationUnavailableException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestTree;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link JTreeMatchingPathQuery#matchingPathFor(javax.swing.JTree, String, JTreePathFinder)}.
 * 
 * @author Alex Ruiz
 */
public class JTreeMatchingPathQuery_matchingPathFor_Test extends RobotBasedTestCase {
  @Rule
  public ExpectedException thrown = none();

  private MyWindow window;
  private JTreePathFinder pathFinder;

  @Override
  protected void onSetUp() {
    pathFinder = new JTreePathFinder();
    window = MyWindow.createNew(getClass());
  }

  @Test
  public void should_Find_Matching_Path_When_Root_Is_Invisible() {
    TreePath treePath = JTreeMatchingPathQuery.matchingPathFor(window.tree, "branch1/branch1.1", pathFinder);
    Object[] path = treePath.getPath();
    assertThat(path).hasSize(3);
    assertThatIsTreeNodeWithGivenText(path[0], "root");
    assertThatIsTreeNodeWithGivenText(path[1], "branch1");
    assertThatIsTreeNodeWithGivenText(path[2], "branch1.1");
  }

  @Test
  public void should_Find_Matching_Path_When_Root_Is_Visible() {
    makeTreeRootVisible();
    TreePath treePath = JTreeMatchingPathQuery.matchingPathFor(window.tree, "root/branch1/branch1.1", pathFinder);
    Object[] path = treePath.getPath();
    assertThat(path).hasSize(3);
    assertThatIsTreeNodeWithGivenText(path[0], "root");
    assertThatIsTreeNodeWithGivenText(path[1], "branch1");
    assertThatIsTreeNodeWithGivenText(path[2], "branch1.1");
  }

  private void makeTreeRootVisible() {
    setRootVisible(window.tree, true);
    robot.waitForIdle();
  }

  private static void assertThatIsTreeNodeWithGivenText(Object o, String text) {
    assertThat(o).isInstanceOf(DefaultMutableTreeNode.class);
    DefaultMutableTreeNode node = (DefaultMutableTreeNode) o;
    assertThat(node.getUserObject()).isEqualTo(text);
  }

  @Test
  public void should_Throw_Error_If_Path_Not_Found() {
    thrown.expect(LocationUnavailableException.class, "Unable to find path 'hello'");
    JTreeMatchingPathQuery.matchingPathFor(window.tree, "hello", pathFinder);
  }

  static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
    }

    final MutableTreeNode root = createRoot();
    final TestTree tree = new TestTree(nodes(root));

    private static MutableTreeNode createRoot() {
      MutableTreeNode root = node("root",
          node("branch1", node("branch1.1", node("branch1.1.1"), node("branch1.1.2")), node("branch1.2")),
          node("branch2"));
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
