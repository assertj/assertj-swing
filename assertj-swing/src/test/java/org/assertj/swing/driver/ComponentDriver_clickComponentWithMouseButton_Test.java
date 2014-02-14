/*
 * Created on Jul 19, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.assertj.swing.driver;

import static org.assertj.swing.awt.AWT.centerOf;
import static org.assertj.swing.core.MouseButton.LEFT_BUTTON;
import static org.assertj.swing.core.MouseButton.MIDDLE_BUTTON;
import static org.assertj.swing.core.MouseButton.RIGHT_BUTTON;

import org.assertj.swing.core.MouseButton;
import org.assertj.swing.test.recorder.ClickRecorder;
import org.assertj.swing.test.recorder.ClickRecorderManager;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#click(java.awt.Component, org.assertj.swing.core.MouseButton)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_clickComponentWithMouseButton_Test extends ComponentDriver_TestCase {
  @Rule
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  @Test
  public void should_click_Component_with_left_mouse_button_once() {
    shouldClickComponentWith(LEFT_BUTTON);
  }

  @Test
  public void should_click_Component_with_middle_mouse_button_once() {
    shouldClickComponentWith(MIDDLE_BUTTON);
  }

  @Test
  public void should_click_Component_with_right_mouse_button_once() {
    shouldClickComponentWith(RIGHT_BUTTON);
  }

  private void shouldClickComponentWith(MouseButton mouseButton) {
    showWindow();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    driver.click(window.button, mouseButton);
    recorder.wasClickedWith(mouseButton).clickedAt(centerOf(window.button)).timesClicked(1);
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_MouseButton_is_null() {
    driver.click(window.button, (MouseButton) null);
  }

  @Test
  public void should_throw_error_if_Component_is_disabled() {
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    disableButton();
    thrown.expectIllegalStateIsDisabledComponent();
    try {
      driver.click(window.button, RIGHT_BUTTON);
    } finally {
      recorder.wasNotClicked();
    }
  }

  @Test
  public void should_throw_error_when_clicking_Component_not_showing() {
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    thrown.expectIllegalStateIsNotShowingComponent();
    try {
      driver.click(window.button, RIGHT_BUTTON);
    } finally {
      recorder.wasNotClicked();
    }
  }
}
