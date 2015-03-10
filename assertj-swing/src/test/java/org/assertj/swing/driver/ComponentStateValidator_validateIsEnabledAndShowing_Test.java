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
import static org.assertj.swing.test.task.ComponentSetEnabledTask.disable;

import java.awt.Component;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link ComponentPreconditions#checkEnabledAndShowing(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class ComponentStateValidator_validateIsEnabledAndShowing_Test extends RobotBasedTestCase {
  private TestWindow window;

  @Rule
  public ExpectedException thrown = none();

  @Override
  protected void onSetUp() {
    window = TestWindow.createNewWindow(getClass());
  }

  @Test
  public void should_Not_Throw_Error_If_Component_Is_Enabled_And_Showing_On_The_Screen() {
    robot.showWindow(window);
    validateWindowIsEnabledAndShowing(window);
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Disabled() {
    robot.showWindow(window);
    disable(window);
    robot.waitForIdle();
    thrown.expectIllegalStateIsDisabledComponent();
    validateWindowIsEnabledAndShowing(window);
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    validateWindowIsEnabledAndShowing(window);
  }

  @RunsInEDT
  private static void validateWindowIsEnabledAndShowing(final Component c) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        ComponentPreconditions.checkEnabledAndShowing(c);
      }
    });
  }
}
