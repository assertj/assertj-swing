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

import static java.awt.Event.SHIFT_MASK;
import static java.awt.event.KeyEvent.VK_A;
import static org.fest.swing.core.KeyPressInfo.keyCode;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#pressAndReleaseKey(java.awt.Component, org.fest.swing.core.KeyPressInfo)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_pressAndReleaseKeyWithPressInfo_Test extends ComponentDriver_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_KeyPressInfo_is_null() {
    driver.pressAndReleaseKey(window.button, null);
  }

  @Test
  public void should_press_and_release_key_in_given_KeyPressInfo() {
    showWindow();
    assertThatTextFieldIsEmpty();
    driver.pressAndReleaseKey(window.textField, keyCode(VK_A).modifiers(SHIFT_MASK));
    assertThatTextInTextFieldIs("A");
  }

  @Test
  public void should_throw_error_if_Component_is_disabled() {
    disableTextField();
    try {
      driver.pressAndReleaseKey(window.textField, keyCode(VK_A).modifiers(SHIFT_MASK));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

  @Test
  public void should_throw_error_if_Component_is_not_showing_on_the_screen() {
    try {
      driver.pressAndReleaseKey(window.textField, keyCode(VK_A).modifiers(SHIFT_MASK));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }
}
