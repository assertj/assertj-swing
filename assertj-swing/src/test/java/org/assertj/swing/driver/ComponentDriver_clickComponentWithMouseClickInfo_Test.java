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
import static org.assertj.swing.core.MouseClickInfo.leftButton;

import org.assertj.swing.core.MouseClickInfo;
import org.assertj.swing.test.recorder.ClickRecorder;
import org.assertj.swing.test.recorder.ClickRecorderManager;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#click(java.awt.Component, org.assertj.swing.core.MouseClickInfo)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_clickComponentWithMouseClickInfo_Test extends ComponentDriver_TestCase {
  @Rule
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_MouseClickInfo_Is_Null() {
    driver.click(window.button, (MouseClickInfo) null);
  }

  @Test
  public void should_Click_Component_With_Given_MouseClickInfo() {
    showWindow();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    MouseClickInfo mouseClickInfo = leftButton().times(3);
    driver.click(window.button, mouseClickInfo);
    recorder.wasClickedWith(mouseClickInfo.button()).clickedAt(centerOf(window.button))
        .timesClicked(mouseClickInfo.times());
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Disabled() {
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    disableButton();
    thrown.expectIllegalStateIsDisabledComponent();
    try {
      driver.click(window.button, leftButton());
    } finally {
      recorder.wasNotClicked();
    }
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    thrown.expectIllegalStateIsNotShowingComponent();
    try {
      driver.click(window.button, leftButton());
    } finally {
      recorder.wasNotClicked();
    }
  }
}
