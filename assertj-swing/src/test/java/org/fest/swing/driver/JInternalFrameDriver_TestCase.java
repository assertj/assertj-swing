/*
 * Created on Feb 24, 2008
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
import static org.fest.swing.driver.JInternalFrameAction.ICONIFY;
import static org.fest.swing.driver.JInternalFrameSetIconTask.setIcon;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.util.Strings.concat;

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;

/**
 * Base test case for {@link JInternalFrameDriver}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class JInternalFrameDriver_TestCase extends RobotBasedTestCase {
  MyWindow window;
  JInternalFrame internalFrame;
  JDesktopPane desktopPane;
  JInternalFrameDriver driver;

  @Override
  protected final void onSetUp() {
    driver = new JInternalFrameDriver(robot);
    window = MyWindow.createNew();
    internalFrame = window.internalFrame;
    desktopPane = window.desktopPane;
  }

  @RunsInEDT
  final void disableInternalFrame() {
    showWindow();
    disable(internalFrame);
    robot.waitForIdle();
  }

  final void showWindow() {
    robot.showWindow(window);
  }

  @RunsInEDT
  final void iconify() {
    setIcon(internalFrame, ICONIFY);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void assertThatIsMaximized() {
    assertThat(isMaximized()).isTrue();
  }

  @RunsInEDT
  final boolean isMaximized() {
    return isMaximized(internalFrame);
  }

  @RunsInEDT
  private static boolean isMaximized(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return internalFrame.isMaximum();
      }
    });
  }

  @RunsInEDT
  final int zOrder() {
    return zOrder(desktopPane, internalFrame);
  }

  @RunsInEDT
  private static int zOrder(final JDesktopPane desktopPane, final JInternalFrame internalFrame) {
    return execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return desktopPane.getComponentZOrder(internalFrame);
      }
    });
  }

  static class MyWindow extends TestWindow {
    final JDesktopPane desktopPane;
    final JInternalFrame internalFrame;

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
      super(JInternalFrameDriver_TestCase.class);
      MyInternalFrame.resetIndex();
      desktopPane = new JDesktopPane();
      internalFrame = new MyInternalFrame();
      addInternalFrames();
      setContentPane(desktopPane);
      setPreferredSize(new Dimension(400, 300));
    }

    private void addInternalFrames() {
      addInternalFrame(new MyInternalFrame());
      addInternalFrame(internalFrame);
    }

    private void addInternalFrame(JInternalFrame f) {
      desktopPane.add(f);
      f.toFront();
      f.setVisible(true);
    }
  }

  private static class MyInternalFrame extends JInternalFrame {
    private static int index;

    static void resetIndex() {
      index = 0;
    }

    MyInternalFrame() {
      super(concat("Internal Frame ", valueOf(index++)), true, true, true, true);
      setSize(200, 100);
    }
  }
}
