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

import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#pressKey(java.awt.Component, int)} and
 * {@link ComponentDriver#releaseKey(java.awt.Component, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_releaseKey_Test extends ComponentDriver_TestCase {
  @Override
  protected void onTearDown() {
    assertThatTextFieldIsEmpty();
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Disabled() {
    disableTextField();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.pressKey(window.textField, VK_A);
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
    assertThatTextFieldIsEmpty();
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.pressKey(window.textField, VK_A);
  }

  @Test
  public void shouldThrowErrorWhenReleasingKeyInDisabledComponent() {
    disableTextField();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.releaseKey(window.textField, VK_A);
  }

  @Test
  public void shouldThrowErrorWhenReleasingKeyInNotShowingComponent() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.releaseKey(window.textField, VK_A);
  }
}
