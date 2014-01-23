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

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_E;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#pressAndReleaseKeys(java.awt.Component, int...)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_pressAndReleaseKeys_Test extends ComponentDriver_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_array_of_keys_is_null() {
    int[] keyCodes = null;
    driver.pressAndReleaseKeys(window.button, keyCodes);
  }

  @Test
  public void should_press_and_release_keys() {
    showWindow();
    assertThatTextFieldIsEmpty();
    int[] keyCodes = { VK_A, VK_C, VK_E };
    driver.pressAndReleaseKeys(window.textField, keyCodes);
    assertThatTextInTextFieldIs("ace");
  }

  @Test
  public void should_throw_error_if_Component_is_disabled() {
    disableTextField();
    try {
      driver.pressAndReleaseKeys(window.textField, VK_A);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

  @Test
  public void should_throw_error_if_Component_is_not_showing_on_the_screen() {
    try {
      driver.pressAndReleaseKeys(window.textField, VK_A);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }
}
