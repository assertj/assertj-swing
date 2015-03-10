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

import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.builder.JButtons.button;
import static org.assertj.swing.test.builder.JDialogs.dialog;
import static org.assertj.swing.test.builder.JFrames.frame;
import static org.mockito.Mockito.mock;

import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestMdiWindow;
import org.assertj.swing.test.task.ComponentSetEnabledTask;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link ContainerDriver#checkCanResize(Container)}.
 * 
 * @author Alex Ruiz
 */
public class ContainerDriver_checkCanResize_Test extends RobotBasedTestCase {
  private ContainerDriver driver;

  @Rule
  public ExpectedException thrown = none();

  @Override
  protected void onSetUp() {
    driver = new ContainerDriver(mock(Robot.class)) {
    };
  }

  @Test
  public void should_Pass_If_Frame_Is_Resizable() {
    JFrame f = frame().createNew();
    robot.showWindow(f);
    checkCanResize(f);
  }

  @Test
  public void should_Fail_If_Frame_Is_Not_Resizable() {
    JFrame f = frame().resizable(false).createNew();
    thrown.expectIllegalStateIsNotResizableComponent();
    checkCanResize(f);
  }

  @Test
  public void should_Fail_If_Frame_Is_Resizable_But_Disabled() {
    JFrame f = frame().createNew();
    disable(f);
    thrown.expectIllegalStateIsDisabledComponent();
    checkCanResize(f);
  }

  @Test
  public void should_Fail_If_Frame_Is_Resizable_But_Not_Showing_On_The_Screen() {
    JFrame f = frame().createNew();
    thrown.expectIllegalStateIsNotShowingComponent();
    checkCanResize(f);
  }

  @Test
  public void should_Pass_If_Dialog_Is_Resizable() {
    JDialog d = dialog().createNew();
    robot.showWindow(d);
    checkCanResize(d);
  }

  @Test
  public void should_Fail_If_Dialog_Is_Not_Resizable() {
    JDialog d = dialog().resizable(false).createNew();
    thrown.expectIllegalStateIsNotResizableComponent();
    checkCanResize(d);
  }

  @Test
  public void should_Fail_If_Dialog_Is_Resizable_But_Disabled() {
    JDialog d = dialog().createNew();
    disable(d);
    thrown.expectIllegalStateIsDisabledComponent();
    checkCanResize(d);
  }

  @RunsInEDT
  private void disable(Container c) {
    ComponentSetEnabledTask.disable(c);
    robot.waitForIdle();
  }

  @Test
  public void should_Fail_If_Dialog_Is_Resizable_But_Not_Showing_On_The_Screen() {
    JDialog d = dialog().createNew();
    thrown.expectIllegalStateIsNotShowingComponent();
    checkCanResize(d);
  }

  @Test
  public void should_Pass_If_JInternalFrame_Is_Resizable() {
    TestMdiWindow w = TestMdiWindow.createNewWindow(getClass());
    robot.showWindow(w);
    checkCanResize(w.internalFrame());
  }

  @Test
  public void should_Pass_If_JInternalFrame_Is_ResizableAndDisabled() {
    TestMdiWindow w = TestMdiWindow.createNewWindow(getClass());
    robot.showWindow(w);
    disable(w.internalFrame());
    checkCanResize(w.internalFrame());
  }

  @Test
  public void should_Fail_If_JInternalFrame_Is_Not_Resizable() {
    TestMdiWindow w = TestMdiWindow.createNewWindow(getClass());
    robot.showWindow(w);
    JInternalFrame i = w.internalFrame();
    makeNotResizable(i);
    robot.waitForIdle();
    thrown.expectIllegalStateIsNotResizableComponent();
    checkCanResize(i);
  }

  @RunsInEDT
  private static void makeNotResizable(final JInternalFrame internalFrame) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        internalFrame.setResizable(false);
      }
    });
  }

  @Test
  public void should_Fail_If_JInternalFrame_Is_Resizable_But_Not_Showing_On_The_Screen() {
    TestMdiWindow w = TestMdiWindow.createNewWindow(getClass());
    thrown.expectIllegalStateIsNotShowingComponent();
    checkCanResize(w.internalFrame());
  }

  @Test
  public void should_Fail_If_Component_Is_Not_Window() {
    thrown.expectIllegalStateIsNotResizableComponent();
    checkCanResize(button().createNew());
  }

  @RunsInEDT
  private void checkCanResize(final Container c) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        driver.checkCanResize(c);
      }
    });
  }
}
