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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.awt.AWT.centerOf;
import static org.fest.swing.core.MouseClickInfo.leftButton;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.test.recorder.ClickRecorder;
import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#click(java.awt.Component, org.fest.swing.core.MouseClickInfo)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_clickComponentWithMouseClickInfo_Test extends ComponentDriver_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_MouseClickInfo_is_null() {
    driver.click(window.button, (MouseClickInfo) null);
  }

  @Test
  public void should_click_Component_with_given_MouseClickInfo() {
    showWindow();
    ClickRecorder clickRecorder = ClickRecorder.attachTo(window.button);
    MouseClickInfo mouseClickInfo = leftButton().times(3);
    driver.click(window.button, mouseClickInfo);
    assertThat(clickRecorder).wasClickedWith(mouseClickInfo.button()).clickedAt(centerOf(window.button))
    .timesClicked(mouseClickInfo.times());
  }

  @Test
  public void should_throw_error_if_Component_is_disabled() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(window.button);
    disableButton();
    try {
      driver.click(window.button, leftButton());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  @Test
  public void should_throw_error_if_Component_is_not_showing_on_the_screen() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(window.button);
    try {
      driver.click(window.button, leftButton());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }
}
