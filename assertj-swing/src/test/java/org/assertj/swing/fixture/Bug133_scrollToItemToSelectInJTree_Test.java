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
package org.assertj.swing.fixture;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.timing.Pause.pause;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.exception.LocationUnavailableException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestTree;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=133">Bug 133</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug133_scrollToItemToSelectInJTree_Test extends RobotBasedTestCase {
  private FrameFixture frame;
  private MyWindow window;
  @Rule
  public ExpectedException thrown = none();

  @Override
  public void onSetUp() {
    window = MyWindow.createNew();
    frame = new FrameFixture(robot, window);
    frame.show();
  }

  @Test
  public void should_Scroll_To_Cell_When_Selecting_By_Path() {
    frame.tree("drag").selectPath("root/100/100.1");
    assertThat(selectionOf(window.dragTree)).isEqualTo("100.1");
  }

  @Test
  public void should_Scroll_To_Cell_When_Selecting_By_Row_Index() {
    frame.tree("drag").selectRow(99);
    assertThat(selectionOf(window.dragTree)).isEqualTo("99");
  }

  @Test
  public void should_Scroll_To_Cells_When_Dragging_And_Dropping_By_Path() {
    frame.tree("drag").drag("root/99");
    frame.tree("drop").drop("root/90");
    assertPathNotFoundInDragTree("root/99");
    pause(500);
    frame.tree("drop").selectPath("root/90/99");
  }

  @Test
  public void should_Scroll_To_Cells_When_Dragging_And_Dropping_By_Row_Index() {
    frame.tree("drag").drag(99);
    frame.tree("drop").drop(90);
    assertPathNotFoundInDragTree("root/99");
    pause(500);
    frame.tree("drop").selectPath("root/90/99");
  }

  private void assertPathNotFoundInDragTree(String path) {
    thrown.expect(LocationUnavailableException.class);
    thrown.expectMessageToContain(path);
    frame.tree("drag").selectPath(path);
  }

  @RunsInEDT
  private static Object selectionOf(final JTree tree) {
    return execute(new GuiQuery<Object>() {
      @Override
      protected Object executeInEDT() {
        Object lastPathComponent = tree.getSelectionPath().getLastPathComponent();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) lastPathComponent;
        return node.getUserObject();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    final JTree dragTree = new TestTree("drag");
    final JTree dropTree = new TestTree("drop");

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
      super(Bug133_scrollToItemToSelectInJTree_Test.class);
      dragTree.setModel(model());
      add(scrollPaneFor(dragTree));
      dropTree.setModel(model());
      add(scrollPaneFor(dropTree));
      setPreferredSize(new Dimension(300, 150));
    }

    private static JScrollPane scrollPaneFor(JTree tree) {
      JScrollPane scrollPane = new JScrollPane(tree);
      scrollPane.setPreferredSize(new Dimension(100, 100));
      return scrollPane;
    }

    private static DefaultTreeModel model() {
      return new DefaultTreeModel(rootWith100Nodes());
    }

    private static DefaultMutableTreeNode rootWith100Nodes() {
      DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
      for (int i = 0; i < 100; i++) {
        DefaultMutableTreeNode node = nodeWithIndex(i);
        if (i == 99) {
          node.add(new DefaultMutableTreeNode("100.1"));
        }
        root.add(node);
      }
      return root;
    }

    private static DefaultMutableTreeNode nodeWithIndex(int i) {
      return new DefaultMutableTreeNode(valueOf(i + 1));
    }
  }
}
