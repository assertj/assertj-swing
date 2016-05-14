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

import static org.assertj.swing.awt.AWT.centerOf;

import org.assertj.swing.test.recorder.ClickRecorder;
import org.assertj.swing.test.recorder.ClickRecorderManager;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#click(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_clickComponent_Test extends ComponentDriver_TestCase {
  @Rule
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  @Test
  public void should_Click_Component() {
    showWindow();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    driver.click(window.button);
    recorder.wasClicked().clickedAt(centerOf(window.button)).timesClicked(1);
  }

  @Test
  public void should_Click_Disabled_Component() {
    showWindow();
    disableButton();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    driver.click(window.button);
    recorder.wasClicked().clickedAt(centerOf(window.button)).timesClicked(1);
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Disabled_And_ClickOnDisabledAllowd_Is_False() {
    robot.settings().clickOnDisabledComponentsAllowed(false);
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    disableButton();
    thrown.expectIllegalStateIsDisabledComponent();
    try {
      driver.click(window.button);
    } finally {
      recorder.wasNotClicked();
    }
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    thrown.expectIllegalStateIsNotShowingComponent();
    try {
      driver.click(window.button);
    } finally {
      recorder.wasNotClicked();
    }
  }
}
