/*
 * Created on Oct 24, 2008
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

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;

import java.awt.Component;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link ComponentPreconditions#checkEnabledAndShowing(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class ComponentStateValidator_validateIsEnabledAndShowing_Test extends RobotBasedTestCase {
  private TestWindow window;

  @Override
  protected void onSetUp() {
    window = TestWindow.createNewWindow(getClass());
  }

  @Test
  public void should_not_throw_error_if_Component_is_enabled_and_showing_on_the_screen() {
    robot.showWindow(window);
    validateWindowIsEnabledAndShowing(window);
  }

  @Test
  public void should_throw_error_if_Component_is_disabled() {
    robot.showWindow(window);
    disable(window);
    robot.waitForIdle();
    try {
      validateWindowIsEnabledAndShowing(window);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_Component_is_not_showing_on_the_screen() {
    try {
      validateWindowIsEnabledAndShowing(window);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
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
