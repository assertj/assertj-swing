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

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_E;

import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#pressAndReleaseKeys(java.awt.Component, int...)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_pressAndReleaseKeys_Test extends ComponentDriver_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Array_Of_Keys_Is_Null() {
    int[] keyCodes = null;
    driver.pressAndReleaseKeys(window.button, keyCodes);
  }

  @Test
  public void should_Press_And_Release_Keys() {
    showWindow();
    assertThatTextFieldIsEmpty();
    int[] keyCodes = { VK_A, VK_C, VK_E };
    driver.pressAndReleaseKeys(window.textField, keyCodes);
    assertThatTextInTextFieldIs("ace");
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Disabled() {
    disableTextField();
    thrown.expectIllegalStateIsDisabledComponent();
    try {
      driver.pressAndReleaseKeys(window.textField, VK_A);
    } finally {
      assertThatTextFieldIsEmpty();
    }
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    try {
      driver.pressAndReleaseKeys(window.textField, VK_A);
    } finally {
      assertThatTextFieldIsEmpty();
    }
  }
}
