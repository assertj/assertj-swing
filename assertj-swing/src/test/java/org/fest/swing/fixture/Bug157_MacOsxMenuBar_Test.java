/*
 * Created on Jun 15, 2008
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.finder.WindowFinder.findFrame;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test for <a href="http://code.google.com/p/fest/issues/detail?id=157" target="_blank">issue 157</a>.
 *
 * @author Andriy Tsykholyas
 * @author Yvonne Wang
 */
public class Bug157_MacOsxMenuBar_Test extends RobotBasedTestCase {
  private JMenuItemFixture menuItemFixture;

  @Override
  protected void onSetUp() {
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    robot.showWindow(MyWindow.createNew());
    FrameFixture frameFixture = findFrame("myWindow").withTimeout(2000).using(robot);
    menuItemFixture = frameFixture.menuItem("menuItem");
  }

  @Test
  public void should_select_menu() {
    final boolean[] selected = new boolean[1];
    JMenuItem menu = menuItemFixture.target();
    menu.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        selected[0] = true;
      }
    });
    menuItemFixture.click();
    assertThat(selected[0]).isTrue();
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

    private MyWindow() {
      super(Bug157_MacOsxMenuBar_Test.class);
      setName("myWindow");
      setJMenuBar(menuBar(menu(menuItem())));
      setPreferredSize(new Dimension(200, 100));
    }

    private JMenuItem menuItem() {
      JMenuItem menuItem = new JMenuItem("Menu Item");
      menuItem.setName("menuItem");
      return menuItem;
    }

    private JMenu menu(JMenuItem menuItem) {
      JMenu menu = new JMenu("Menu");
      menu.setName("menu");
      menu.add(menuItem);
      return menu;
    }

    private JMenuBar menuBar(JMenu menu) {
      JMenuBar menuBar = new JMenuBar();
      menuBar.add(menu);
      return menuBar;
    }
  }
}
