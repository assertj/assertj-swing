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
package org.assertj.swing.fixture;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=108">Bug 108</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug108_findShowingContainerOnly_Test extends RobotBasedTestCase {
  private MyWindow window;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew();
    robot.showWindow(window, new Dimension(400, 300));
  }

  @Test
  public void should_Find_Only_Showing_Container() {
    robot.waitForIdle();
    JInternalFrameFixture fixture = new JInternalFrameFixture(robot, "target");
    assertThat(fixture.target()).isSameAs(window.visibleFrame);
  }

  private static class MyWindow extends TestWindow {
    final MyInternalFrame invisibleFrame = MyInternalFrame.createInvisible();
    final MyInternalFrame visibleFrame = MyInternalFrame.createVisible();

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
      super(Bug108_findShowingContainerOnly_Test.class);
      JDesktopPane desktop = new JDesktopPane();
      setContentPane(desktop);
      desktop.add(invisibleFrame);
      desktop.add(visibleFrame);
    }
  }

  private static class MyInternalFrame extends JInternalFrame {
    private static int instanceCounter;

    static MyInternalFrame createVisible() {
      return new MyInternalFrame(true);
    }

    static MyInternalFrame createInvisible() {
      return new MyInternalFrame(false);
    }

    private MyInternalFrame(boolean visible) {
      super(concat("Internal Frame ", valueOf(instanceCounter++)), true, true, true, true);
      setName("target");
      setSize(200, 100);
      setVisible(visible);
    }
  }
}
