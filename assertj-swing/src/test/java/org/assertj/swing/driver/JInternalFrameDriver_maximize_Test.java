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
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.assertj.swing.test.core.CommonAssertions.failWhenExpectingException;
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
  public void should_maximize_JInternalFrame() {
    showWindow();
    driver.maximize(internalFrame);
    assertThatIsMaximized();
  }

  @Test
  public void should_maximize_iconified_JInternalFrame() {
    showWindow();
    iconify();
    driver.maximize(internalFrame);
    assertThatIsMaximized();
  }

  @Test
  public void should_maximize_disabled_JInternalFrame() {
    disableInternalFrame();
    driver.maximize(internalFrame);
    assertThatIsMaximized();
  }

  @Test
  public void should_throw_error_if_JInternalFrame_is_not_showing_on_the_screen() {
    try {
      driver.maximize(internalFrame);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JInternalFrame_is_hidden() {
    hideInternalJFrame();
    try {
      driver.maximize(internalFrame);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  @RunsInEDT
  private void hideInternalJFrame() {
    hide(internalFrame);
    robot.waitForIdle();
  }

  @Test
  public void should_throw_error_if_JInternalFrame_is_not_maximizable() {
    makeNotMaximizable();
    showWindow();
    try {
      driver.maximize(internalFrame);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("The JInternalFrame <").contains("> is not maximizable");
    }
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
