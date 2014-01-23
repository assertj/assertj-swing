/*
 * Created on Mar 27, 2009
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
package org.fest.swing.core;

import static java.awt.event.InputEvent.CTRL_MASK;
import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_M;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.KeyStroke.getKeyStroke;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.finder.JOptionPaneFinder.findOptionPane;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JOptionPaneFixture;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for bug <a href="http://jira.codehaus.org/browse/FEST-103" target="_blank">FEST_103</a>.
 *
 * @author Alex Ruiz
 */
public class FEST103_modifierNotBeingPressed_Test extends RobotBasedTestCase {
  private FrameFixture frameFixture;
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    frameFixture = new FrameFixture(robot, window);
    frameFixture.show();
  }

  @Test
  public void should_press_key_and_modifier() {
    frameFixture.moveToFront(); // ensure the window is active
    robot.pressAndReleaseKey(VK_M, CTRL_MASK);
    JOptionPaneFixture optionPane = findOptionPane().using(robot);
    optionPane.requireInformationMessage().requireMessage("Hello World");
  }

  @Test
  public void should_press_shift_as_modifier() {
    frameFixture.moveToFront();
    robot.focus(window.textField);
    robot.pressAndReleaseKey(VK_A, SHIFT_MASK);
    frameFixture.textBox().requireText("A");
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

    final JTextField textField = new JTextField(5);

    private MyWindow() {
      super(FEST103_modifierNotBeingPressed_Test.class);
      setJMenuBar(menuBar());
      addComponents(textField);
      setPreferredSize(new Dimension(200, 100));
    }

    private JMenuBar menuBar() {
      JMenuBar menuBar = new JMenuBar();
      JMenu viewMenu = new JMenu("View");
      JMenuItem viewMessageMenu = new JMenuItem("Message");
      viewMessageMenu.setAccelerator(getKeyStroke(VK_M, CTRL_MASK));
      viewMessageMenu.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          showMessageDialog(MyWindow.this, "Hello World", "My Message", INFORMATION_MESSAGE);
        }
      });
      viewMenu.add(viewMessageMenu);
      menuBar.add(viewMenu);
      return menuBar;
    }
  }
}
