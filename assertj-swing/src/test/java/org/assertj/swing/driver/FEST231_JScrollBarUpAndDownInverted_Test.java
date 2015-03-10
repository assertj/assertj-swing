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

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-231" target="_blank">FEST-231</a>
 * 
 * @author Alex Ruiz
 */
public class FEST231_JScrollBarUpAndDownInverted_Test extends RobotBasedTestCase {
  private JScrollBarDriver driver;
  private JScrollBar verticalScrollBar;
  private JScrollBar horizontalScrollBar;

  @Override
  protected void onSetUp() {
    driver = new JScrollBarDriver(robot);
    MyWindow window = MyWindow.createNew();
    verticalScrollBar = window.verticalScrollBar;
    horizontalScrollBar = window.horizontalScrollBar;
    robot.showWindow(window);
  }

  @Test
  public void should_Scroll_One_Unit_Down_And_Up() {
    driver.requireValue(verticalScrollBar, 0);
    driver.scrollUnitDown(verticalScrollBar);
    driver.requireValue(verticalScrollBar, 1);
    driver.scrollUnitUp(verticalScrollBar);
    driver.requireValue(verticalScrollBar, 0);
  }

  @Test
  public void should_Scroll_One_Unit_Right_And_Left() {
    driver.requireValue(horizontalScrollBar, 0);
    driver.scrollUnitDown(horizontalScrollBar);
    driver.requireValue(horizontalScrollBar, 1);
    driver.scrollUnitUp(horizontalScrollBar);
    driver.requireValue(horizontalScrollBar, 0);
  }

  @Test
  public void should_Scroll_One_Block_Down_And_Up() {
    // TODO(Alex): Test in Windows
    driver.requireValue(verticalScrollBar, 0);
    driver.scrollBlockDown(verticalScrollBar);
    driver.requireValue(verticalScrollBar, 10);
    driver.scrollBlockUp(verticalScrollBar);
    driver.requireValue(verticalScrollBar, 0);
  }

  @Test
  public void should_Scroll_One_Block_Right_And_Left() {
    driver.requireValue(horizontalScrollBar, 0);
    driver.scrollBlockDown(horizontalScrollBar);
    driver.requireValue(horizontalScrollBar, 10);
    driver.scrollBlockUp(horizontalScrollBar);
    driver.requireValue(horizontalScrollBar, 0);
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

    final JScrollPane scrollPane = new JScrollPane(newList(), VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
    final JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
    final JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();

    private MyWindow() {
      super(FEST231_JScrollBarUpAndDownInverted_Test.class);
      scrollPane.setPreferredSize(new Dimension(50, 50));
      addComponents(scrollPane);
    }

    private static JList newList() {
      return new JList(array("1. One", "2. Two", "3. Three", "4. Four", "5. Five", "6. Six"));
    }
  }
}
