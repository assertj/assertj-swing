/*
 * Created on Nov 28, 2009
 *
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Arrays.array;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
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
  public void should_scroll_one_unit_down_and_up() {
    driver.requireValue(verticalScrollBar, 0);
    driver.scrollUnitDown(verticalScrollBar);
    driver.requireValue(verticalScrollBar, 1);
    driver.scrollUnitUp(verticalScrollBar);
    driver.requireValue(verticalScrollBar, 0);
  }

  @Test
  public void should_scroll_one_unit_right_and_left() {
    driver.requireValue(horizontalScrollBar, 0);
    driver.scrollUnitDown(horizontalScrollBar);
    driver.requireValue(horizontalScrollBar, 1);
    driver.scrollUnitUp(horizontalScrollBar);
    driver.requireValue(horizontalScrollBar, 0);
  }

  @Test
  public void should_scroll_one_block_down_and_up() {
    // TODO(Alex): Test in Windows
    driver.requireValue(verticalScrollBar, 0);
    driver.scrollBlockDown(verticalScrollBar);
    driver.requireValue(verticalScrollBar, 6);
    driver.scrollBlockUp(verticalScrollBar);
    driver.requireValue(verticalScrollBar, 0);
  }

  @Test
  public void should_scroll_one_block_right_and_left() {
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
      scrollPane.setPreferredSize(new Dimension(50, 120));
      addComponents(scrollPane);
    }

    private static JList newList() {
      return new JList(array("1. One", "2. Two", "3. Three", "4. Four", "5. Five", "6. Six"));
    }
  }
}
