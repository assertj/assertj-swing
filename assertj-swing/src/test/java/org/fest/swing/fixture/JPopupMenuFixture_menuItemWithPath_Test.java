/*
 * Created on Jul 18, 2008
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
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.recorder.ClickRecorder.attachTo;
import static org.fest.swing.util.Platform.isOSX;

import java.awt.Dimension;

import javax.swing.JMenu;
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
 * Tests for {@link JPopupMenuFixture#menuItemWithPath(String...)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
// TODO(Alex): Test JMenuItemFinder instead.
public class JPopupMenuFixture_menuItemWithPath_Test extends RobotBasedTestCase {
  private MyWindow window;
  private JPopupMenuFixture fixture;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew();
    robot.showWindow(window, new Dimension(200, 200));
    JTextComponentFixture textBox = new JTextComponentFixture(robot, "textField");
    textBox.click();
    fixture = textBox.showPopupMenu();
  }

  @Test
  public void should_find_first_level_JMenuItem_by_path() {
    ClickRecorder recorder = attachTo(window.fileMenu);
    JMenuItemFixture menuItem = fixture.menuItemWithPath("File");
    menuItem.click();
    assertThat(recorder).clicked(LEFT_BUTTON).timesClicked(1);
  }

  @Test
  public void should_find_second_level_JMenuItem_by_path() {
    ClickRecorder recorder = attachTo(window.openMenu);
    if (isOSX()) {
      fixture.menuItemWithPath("File").click();
    }
    JMenuItemFixture menuItem = fixture.menuItemWithPath("File", "Open");
    menuItem.click();
    assertThat(recorder).clicked(LEFT_BUTTON).timesClicked(1);
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

    final JPopupMenu popupMenu = new JPopupMenu();
    final JMenu fileMenu = new JMenu("File");
    final JMenuItem openMenu = new JMenuItem("Open");

    private MyWindow() {
      super(JPopupMenuFixture_menuItemWithPath_Test.class);
      JTextField textField = new JTextField(5);
      textField.setName("textField");
      textField.setComponentPopupMenu(popupMenu);
      add(textField);
      popupMenu.add(fileMenu);
      fileMenu.add(openMenu);
    }
  }
}
