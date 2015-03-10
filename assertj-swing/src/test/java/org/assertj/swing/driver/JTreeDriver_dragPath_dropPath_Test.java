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
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.swing.TreeNodeFactory.node;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link JTreeDriver#drag(javax.swing.JTree, String)} and {@link JTreeDriver#drop(javax.swing.JTree, String)}
 * .
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_dragPath_dropPath_Test extends JTreeDriver_dragAndDrop_TestCase {
  @Test
  public void should_Drag_And_Drop() {
    showWindow();
    driver.drag(tree, "root/branch1/branch1.1");
    driver.drop(dropTree, "root");
    // the first child in drag-source tree is no longer branch1.1 but branch1.2
    DefaultMutableTreeNode branch1 = firstChildInRoot();
    assertThat(childCountOf(branch1)).isEqualTo(1);
    assertThat(textOf(firstChildOf(branch1))).isEqualTo("branch1.2");
    // the only child in the root of the drop-target tree is now branch1.1
    DefaultMutableTreeNode root = rootOf(dropTree);
    assertThat(childCountOf(root)).isEqualTo(1);
    assertThat(textOf(firstChildOf(root))).isEqualTo("branch1.1");
  }

  @Test
  public void should_Drag_And_Drop_When_Root_Is_Invisible() {
    // FEST-246
    addNodeToRootAndHideRoot(dropTree);
    robot.waitForIdle();
    showWindow();
    driver.drag(tree, "root/branch1/branch1.1");
    driver.drop(dropTree, "child");
    // the first child in drag-source tree is no longer branch1.1 but branch1.2
    DefaultMutableTreeNode branch1 = firstChildInRoot();
    assertThat(childCountOf(branch1)).isEqualTo(1);
    assertThat(textOf(firstChildOf(branch1))).isEqualTo("branch1.2");
    // the only child in the root of the drop-target tree is now branch1.1
    DefaultMutableTreeNode rootChild = firstChildOfRootIn(dropTree);
    assertThat(childCountOf(rootChild)).isEqualTo(1);
    assertThat(textOf(firstChildOf(rootChild))).isEqualTo("branch1.1");
  }

  @RunsInEDT
  private static void addNodeToRootAndHideRoot(final JTree tree) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        Object root = tree.getModel().getRoot();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) root;
        rootNode.add(node("child"));
        tree.setRootVisible(false);
      }
    });
  }
}
