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
package org.assertj.swing.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for bug <a href="http://jira.codehaus.org/browse/FEST-331" target="_blank">FEST_331</a>.
 *
 * @author Yvonne Wang
 */
public class FEST331_ShowPopupForNonFocusableComponents_Test extends RobotBasedTestCase {
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    robot.showWindow(window);
  }

  @Test
  public void should_Show_JPopupMenu_For_NonFocusable_Component() {
    JPopupMenu popupMenu = robot.showPopupMenu(window.nonFocusableTextField);
    assertThat(popupMenu).isEqualTo(window.popupMenu);
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final JTextField nonFocusableTextField = new JTextField(20);
    final JPopupMenu popupMenu = new JPopupMenu();

    @RunsInCurrentThread
    private MyWindow() {
      super(FEST331_ShowPopupForNonFocusableComponents_Test.class);
      nonFocusableTextField.setFocusable(false);
      popupMenu.add(new JMenuItem("Hello"));
      nonFocusableTextField.setComponentPopupMenu(popupMenu);
      addComponents(new JButton("Click Me"), nonFocusableTextField);
    }
  }
}
