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
import static org.assertj.swing.test.builder.JLabels.label;
import static org.assertj.swing.test.builder.JToolBars.toolBar;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.CustomCellRenderer;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link BasicJTreeCellReader#valueAt(JTree, Object)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJTreeCellReader_valueAt_Test extends RobotBasedTestCase {
  private JTree tree;
  private BasicJTreeCellReader reader;
  private DefaultMutableTreeNode root;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    root = window.root;
    tree = window.tree;
    reader = new BasicJTreeCellReader();
  }

  @Test
  public void should_Return_Text_From_CellRenderer_If_Renderer_Is_JLabel() {
    JLabel label = label().withText("First").createNew();
    setCellRendererComponent(tree, label);
    robot.waitForIdle();
    Object value = reader.valueAt(tree, root);
    assertThat(value).isEqualTo("First");
  }

  @Test
  public void should_Return_Text_From_JTree_If_CellRenderer_Is_Not_JLabel() {
    setCellRendererComponent(tree, unrecognizedRenderer());
    robot.waitForIdle();
    Object value = reader.valueAt(tree, root);
    assertThat(value).isEqualTo(root.getUserObject());
  }

  @Test
  public void should_Return_Null_If_Model_Does_Not_Implement_ToString() {
    class Person {
    }
    root = new DefaultMutableTreeNode(new Person());
    setRootInTree(tree, root);
    setCellRendererComponent(tree, unrecognizedRenderer());
    robot.waitForIdle();
    Object value = reader.valueAt(tree, root);
    assertThat(value).isNull();
  }

  @RunsInEDT
  private static void setRootInTree(final JTree tree, final DefaultMutableTreeNode root) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        ((DefaultTreeModel) tree.getModel()).setRoot(root);
      }
    });
  }

  @RunsInEDT
  private static void setCellRendererComponent(final JTree tree, final Component renderer) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        tree.setCellRenderer(new CustomCellRenderer(renderer));
      }
    });
  }

  @RunsInEDT
  private static Component unrecognizedRenderer() {
    return toolBar().createNew();
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

    final JTree tree;
    final DefaultMutableTreeNode root;

    private MyWindow() {
      super(BasicJTreeCellReader_valueAt_Test.class);
      root = newRoot();
      tree = new JTree(root);
      addComponents(tree);
    }

    private static DefaultMutableTreeNode newRoot() {
      DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
      rootNode.add(new DefaultMutableTreeNode("Node1"));
      return rootNode;
    }
  }
}
