/*
 * Created on Jul 8, 2008
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
import static org.fest.swing.test.builder.JFrames.frame;
import static org.fest.swing.test.recorder.ClickRecorder.attachTo;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Strings.concat;

import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.recorder.ClickRecorder;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=159" target="_blank">Bug 159</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug159_moveParentToFrontWhenClickingMenuItem_Test extends RobotBasedTestCase {
  private static final int DELAY_BEFORE_SHOWING_MENU = 2000;

  private static Logger logger = Logger.getAnonymousLogger();

  private JFrame frameToFocus;
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    window.display();
    frameToFocus = frame().withTitle("To Focus").createNew();
    robot.showWindow(frameToFocus, new Dimension(300, 200));
    robot.focus(frameToFocus);
  }

  @Test
  public void should_select_menu_item_from_menu_bar() {
    JMenuItem menuItem = window.menuItemFromMenuBar;
    JMenuItemFixture fixture = fixtureFor(menuItem);
    pauseBeforeShowingMenu();
    ClickRecorder clickRecorder = attachTo(menuItem);
    fixture.click();
    assertThat(clickRecorder).wasClicked();
  }

  @Test
  public void should_select_menu_item_from_popup_menu() {
    JMenuItem menuItem = window.menuItemFromPopupMenu;
    JMenuItemFixture fixture = fixtureFor(menuItem);
    pauseBeforeShowingMenu();
    robot.showPopupMenu(window.textField);
    ClickRecorder clickRecorder = attachTo(menuItem);
    fixture.click();
    assertThat(clickRecorder).wasClicked();
  }

  private void pauseBeforeShowingMenu() {
    int delay = DELAY_BEFORE_SHOWING_MENU;
    logger.info(concat("Pausing for ", delay, " ms before showing menu"));
    pause(delay);
  }

  private JMenuItemFixture fixtureFor(JMenuItem menuItem) {
    return new JMenuItemFixture(robot, menuItem);
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

    final JMenuItem menuItemFromMenuBar = new JMenuItem("New");
    final JMenuItem menuItemFromPopupMenu = new JMenuItem("Cut");
    final JTextField textField;

    private MyWindow() {
      super(Bug159_moveParentToFrontWhenClickingMenuItem_Test.class);
      setJMenuBar(new JMenuBar());
      JMenu menuFile = new JMenu("File");
      menuFile.add(menuItemFromMenuBar);
      getJMenuBar().add(menuFile);
      setPreferredSize(new Dimension(200, 100));
      textField = new JTextField(20);
      textField.setComponentPopupMenu(popupMenu());
      add(textField);
    }

    private JPopupMenu popupMenu() {
      JPopupMenu popupMenu = new JPopupMenu();
      JMenu menuEdit = new JMenu("Edit");
      menuEdit.add(menuItemFromPopupMenu);
      popupMenu.add(menuEdit);
      popupMenu.setName("popupMenu");
      return popupMenu;
    }
  }
}
