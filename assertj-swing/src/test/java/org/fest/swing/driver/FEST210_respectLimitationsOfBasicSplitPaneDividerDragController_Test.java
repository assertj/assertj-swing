/*
 * Created on Nov 25, 2009
 *
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.util.Lists.newArrayList;

import java.awt.Dimension;
import java.util.Collection;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-210" target="_blank">FEST-210</a>
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class FEST210_respectLimitationsOfBasicSplitPaneDividerDragController_Test extends RobotBasedTestCase {
  private static final int MINIMUM_SIZE = 20;

  private JSplitPaneDriver driver;
  private MyWindow window;

  private final int orientation;

  @Parameters
  public static Collection<Object[]> orientations() {
    return newArrayList(new Object[][] {
        { VERTICAL_SPLIT },
        { HORIZONTAL_SPLIT }
      });
  }

  public FEST210_respectLimitationsOfBasicSplitPaneDividerDragController_Test(int orientation) {
    this.orientation = orientation;
  }

  @Override
  protected void onSetUp() {
    driver = new JSplitPaneDriver(robot);
    window = MyWindow.createNew(orientation);
    robot.showWindow(window);
  }

  @Test
  public void should_respect_minimum_size_when_moving_divider() {
    driver.moveDividerTo(window.splitPane, 30);
    driver.moveDividerTo(window.splitPane, 10);
    assertThatIsGreaterOrLessThanMinimumSize(sizeOf(window.leftPanel));
  }

  private void assertThatIsGreaterOrLessThanMinimumSize(Dimension actual) {
    assertThat(actual.width).isGreaterThanOrEqualTo(MINIMUM_SIZE);
    assertThat(actual.height).isGreaterThanOrEqualTo(MINIMUM_SIZE);
  }

  private static class MyWindow extends TestWindow {
    static MyWindow createNew(final int orientation) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(orientation);
        }
      });
    }

    final JPanel leftPanel = newPanel();
    final JSplitPane splitPane = new JSplitPane(VERTICAL_SPLIT, leftPanel, newPanel());

    MyWindow(int orientation) {
      super(FEST210_respectLimitationsOfBasicSplitPaneDividerDragController_Test.class);
      splitPane.setOrientation(orientation);
      splitPane.setPreferredSize(new Dimension(100, 100));
      addComponents(splitPane);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setPreferredSize(new Dimension(200, 200));
    }

    private static JPanel newPanel() {
      JPanel panel = new JPanel();
      panel.setMinimumSize(new Dimension(MINIMUM_SIZE, MINIMUM_SIZE));
      return panel;
    }
  }
}
