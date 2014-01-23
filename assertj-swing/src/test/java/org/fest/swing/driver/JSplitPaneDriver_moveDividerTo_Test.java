/*
 * Created on Feb 25, 2008
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
package org.fest.swing.driver;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.util.Lists.newArrayList;

import java.awt.Dimension;
import java.util.Collection;

import javax.swing.JList;
import javax.swing.JSplitPane;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JSplitPaneDriver#moveDividerTo(JSplitPane, int)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class JSplitPaneDriver_moveDividerTo_Test extends RobotBasedTestCase {
  private JSplitPaneDriver driver;

  private final int orientation;

  @Parameters
  public static Collection<Object[]> orientations() {
    return newArrayList(new Object[][] {
        { VERTICAL_SPLIT }, { HORIZONTAL_SPLIT }
      });
  }

  public JSplitPaneDriver_moveDividerTo_Test(int orientation) {
    this.orientation = orientation;
  }

  @Override
  protected final void onSetUp() {
    driver = new JSplitPaneDriver(robot);
  }

  @Test
  public void should_move_divider_to_given_location() {
    MyWindow window = createAndShowWindow();
    int newLocation = window.splitPane.getDividerLocation() + 100;
    driver.moveDividerTo(window.splitPane, newLocation);
    assertThat(window.splitPane.getDividerLocation()).isEqualTo(newLocation);
  }

  @Test
  public void should_throw_error_if_JSplitPane_is_disabled() {
    MyWindow window = createWindow();
    disable(window.splitPane);
    robot.waitForIdle();
    try {
      driver.moveDividerTo(window.splitPane, 100);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JSplitPane_is_not_showing_on_the_screen() {
    MyWindow window = createWindow();
    try {
      driver.moveDividerTo(window.splitPane, 100);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  private MyWindow createAndShowWindow() {
    MyWindow window = createWindow();
    robot.showWindow(window);
    return window;
  }

  private MyWindow createWindow() {
    return MyWindow.createNew(orientation, getClass());
  }

  private static class MyWindow extends TestWindow {
    final JSplitPane splitPane;

    @RunsInEDT
    static MyWindow createNew(final int orientation, final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(orientation, testClass);
        }
      });
    }

    private MyWindow(int orientation, Class<?> testClass) {
      super(testClass);
      splitPane = new JSplitPane(orientation, new JList(), new JList());
      splitPane.setDividerLocation(150);
      splitPane.setPreferredSize(new Dimension(300, 300));
      add(splitPane);
    }
  }
}
