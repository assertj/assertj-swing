/*
 * Created on Aug 9, 2008
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

import static java.lang.String.valueOf;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentHasFocusQuery.hasFocus;
import static org.fest.util.Strings.concat;

import java.awt.Dimension;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
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
  public void should_move_Window_to_front() {
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
