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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.driver.JInternalFrameIconQuery.isIconified;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.JInternalFrame;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link JInternalFrameDriver#iconify(JInternalFrame)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JInternalFrameDriver_iconify_Test extends JInternalFrameDriver_TestCase {
  @Test
  public void should_Not_Iconify_Already_Iconified_JInternalFrame() {
    showWindow();
    iconify();
    driver.iconify(internalFrame);
    assertThat(isIconified(internalFrame)).isTrue();
  }

  @Test
  public void should_Throw_Error_If_JInternalFrame_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.iconify(internalFrame);
  }

  @Test
  public void should_Throw_Error_When_Iconifying_Not_Iconfiable_JInternalFrame() {
    makeNotIconfiable();
    showWindow();
    thrown.expect(IllegalStateException.class, "The JInternalFrame <");
    thrown.expectMessageToContain("> is not iconifiable");
    driver.iconify(internalFrame);
  }

  @RunsInEDT
  private void makeNotIconfiable() {
    setIconifiable(internalFrame, false);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setIconifiable(final JInternalFrame internalFrame, final boolean iconfiable) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        internalFrame.setIconifiable(iconfiable);
      }
    });
  }
}
