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

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.task.ComponentSetEnabledTask.disable;

import java.awt.Dimension;
import java.util.Collection;

import javax.swing.JList;
import javax.swing.JSplitPane;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
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
  @Rule
  public ExpectedException thrown = none();

  private JSplitPaneDriver driver;

  private final int orientation;

  @Parameters
  public static Collection<Object[]> orientations() {
    return newArrayList(new Object[][] { { VERTICAL_SPLIT }, { HORIZONTAL_SPLIT } });
  }

  public JSplitPaneDriver_moveDividerTo_Test(int orientation) {
    this.orientation = orientation;
  }

  @Override
  protected final void onSetUp() {
    driver = new JSplitPaneDriver(robot);
  }

  @Test
  public void should_Move_Divider_To_Given_Location() {
    MyWindow window = createAndShowWindow();
    int newLocation = window.splitPane.getDividerLocation() + 100;
    driver.moveDividerTo(window.splitPane, newLocation);
    assertThat(window.splitPane.getDividerLocation()).isEqualTo(newLocation);
  }

  @Test
  public void should_Throw_Error_If_JSplitPane_Is_Disabled() {
    MyWindow window = createWindow();
    disable(window.splitPane);
    robot.waitForIdle();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.moveDividerTo(window.splitPane, 100);
  }

  @Test
  public void should_Throw_Error_If_JSplitPane_Is_Not_Showing_On_The_Screen() {
    MyWindow window = createWindow();
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.moveDividerTo(window.splitPane, 100);
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
