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

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.query.ComponentHasFocusQuery.hasFocus;

import java.awt.Dimension;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link WindowMoveToFrontTask#toFront(java.awt.Window)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class WindowMoveToFrontTask_toFront_Test extends RobotBasedTestCase {
  private MyWindow windowOne;
  private MyWindow windowTwo;

  @Override
  protected void onSetUp() {
    windowOne = MyWindow.createNew();
    windowTwo = MyWindow.createNew();
    robot.showWindow(windowOne);
    robot.showWindow(windowTwo);
  }

  @Test
  public void should_Move_Window_To_Front() {
    assertThat(hasFocus(windowTwo)).isTrue();
    WindowMoveToFrontTask.toFront(windowOne);
    robot.waitForIdle();
    assertThat(hasFocus(windowOne)).isTrue();
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

    private static int counter;

    private MyWindow() {
      super(WindowMoveToFrontTask_toFront_Test.class);
      setTitle(concat(getTitle(), " - ", valueOf(++counter)));
      setPreferredSize(new Dimension(500, 300));
    }
  }
}
