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
import static org.assertj.swing.test.task.ComponentSetVisibleTask.hide;

import javax.swing.JInternalFrame;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link JInternalFrameDriver#maximize(javax.swing.JInternalFrame)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JInternalFrameDriver_maximize_Test extends JInternalFrameDriver_TestCase {
  @Test
  public void should_Maximize_JInternalFrame() {
    showWindow();
    driver.maximize(internalFrame);
    assertThatIsMaximized();
  }

  @Test
  public void should_Maximize_Iconified_JInternalFrame() {
    showWindow();
    iconify();
    driver.maximize(internalFrame);
    assertThatIsMaximized();
  }

  @Test
  public void should_Maximize_Disabled_JInternalFrame() {
    disableInternalFrame();
    driver.maximize(internalFrame);
    assertThatIsMaximized();
  }

  @Test
  public void should_Throw_Error_If_JInternalFrame_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.maximize(internalFrame);
  }

  @Test
  public void should_Throw_Error_If_JInternalFrame_Is_Hidden() {
    hideInternalJFrame();
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.maximize(internalFrame);
  }

  @RunsInEDT
  private void hideInternalJFrame() {
    hide(internalFrame);
    robot.waitForIdle();
  }

  @Test
  public void should_Throw_Error_If_JInternalFrame_Is_Not_Maximizable() {
    makeNotMaximizable();
    showWindow();
    thrown.expect(IllegalStateException.class, "The JInternalFrame <");
    thrown.expectMessageToContain("> is not maximizable");
    driver.maximize(internalFrame);
  }

  @RunsInEDT
  private void makeNotMaximizable() {
    setMaximizable(internalFrame, false);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setMaximizable(final JInternalFrame internalFrame, final boolean maximizable) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        internalFrame.setMaximizable(maximizable);
      }
    });
  }
}
