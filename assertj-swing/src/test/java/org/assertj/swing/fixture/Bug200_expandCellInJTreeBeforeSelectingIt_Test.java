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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=200" target="_blank">Bug 200</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug200_expandCellInJTreeBeforeSelectingIt_Test extends RobotBasedTestCase {
  private FrameFixture frame;

  @Override
  protected void onSetUp() {
    frame = new FrameFixture(robot, MyWindow.createNew());
    frame.show();
  }

  @Test
  public void should_Expand_Cell_And_Show_Popup_Menu() {
    JPopupMenuFixture popupMenu = frame.tree("tree").showPopupMenuAt("root/node");
    assertThat(popupMenu).isNotNull();
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    private MyWindow() {
      super(Bug200_expandCellInJTreeBeforeSelectingIt_Test.class);
      JScrollPane scrollPane = new JScrollPane(tree());
      scrollPane.setPreferredSize(new Dimension(300, 200));
      addComponents(scrollPane);
    }

    private static JTree tree() {
      DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
      DefaultMutableTreeNode node = new DefaultMutableTreeNode("node");
      root.add(node);
      JTree tree = new JTree(root);
      tree.collapseRow(0);
      tree.setName("tree");
      tree.addMouseListener(new MyMouseListener(popupMenu()));
      return tree;
    }

    private static JPopupMenu popupMenu() {
      JPopupMenu popupMenu = new JPopupMenu();
      popupMenu.add(new JMenuItem("Test"));
      return popupMenu;
    }
  }

  private static class MyMouseListener extends MouseAdapter {
    private final JPopupMenu popupMenu;

    MyMouseListener(JPopupMenu popupMenu) {
      this.popupMenu = popupMenu;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      showMe(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
      showMe(e);
    }

    private void showMe(MouseEvent e) {
      if (!e.isPopupTrigger()) {
        return;
      }
      popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }
  }
}
