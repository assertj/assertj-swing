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

import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.recorder.ClickRecorder;
import org.assertj.swing.test.recorder.ClickRecorderManager;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link JMenuItemDriver}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JMenuItemDriver_click_Test extends RobotBasedTestCase {
  @Rule
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  private JMenuItem menuItem;
  private JMenuItemDriver driver;

  @Override
  protected void onSetUp() {
    driver = new JMenuItemDriver(robot);
    MyWindow window = MyWindow.createNew();
    robot.showWindow(window);
    menuItem = window.menuNew;
  }

  @Test
  public void should_Click_Menu() {
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(menuItem);
    driver.click(menuItem);
    recorder.wasClicked();
  }

  private static class MyWindow extends TestWindow {
    final JMenu menuFile = new JMenu("File");
    final JMenuItem menuNew = new JMenuItem("New");

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
      super(JMenuItemDriver_click_Test.class);
      setJMenuBar(new JMenuBar());
      menuFile.add(menuNew);
      getJMenuBar().add(menuFile);
      setPreferredSize(new Dimension(80, 60));
    }
  }
}
