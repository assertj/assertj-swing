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

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.swing.timing.Pause.pause;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.junit.Test;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=289">Bug 289</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug289_robotShouldFocusWindowBeforeClosing_Test extends RobotBasedTestCase {
  @Test
  public void should_Give_Focus_To_Window_Before_Closing_It() {
    MyWindow window1 = MyWindow.createNew();
    WindowCloseMonitor monitor1 = new WindowCloseMonitor();
    window1.addWindowListener(monitor1);
    robot.showWindow(window1);
    pause(500);
    MyWindow window2 = MyWindow.createNew();
    robot.showWindow(window2);
    pause(500);
    robot.close(window2);
    pause(500);
    robot.close(window1);
    assertThat(monitor1.closingWindowHadFocus()).isTrue();
  }

  private static class MyWindow extends JFrame {
    private static int instanceCounter;

    static MyWindow createNew() {
      return GuiActionRunner.execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      String title = Bug289_robotShouldFocusWindowBeforeClosing_Test.class.getSimpleName();
      setTitle(concat(title, " ", valueOf(++instanceCounter)));
      setPreferredSize(new Dimension(400, 100));
    }
  }

  private static class WindowCloseMonitor extends WindowAdapter {
    private boolean hasFocus;

    @Override
    public void windowClosing(WindowEvent e) {
      hasFocus = e.getWindow().hasFocus();
    }

    boolean closingWindowHadFocus() {
      return hasFocus;
    }
  }
}
