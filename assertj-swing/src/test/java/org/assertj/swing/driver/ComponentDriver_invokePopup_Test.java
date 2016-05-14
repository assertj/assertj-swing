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

import static org.assertj.core.api.Assertions.assertThat;

import javax.swing.JPopupMenu;

import org.junit.Test;

/**
 * Test for {@link ComponentDriver#invokePopupMenu(java.awt.Component)}.
 *
 * @author Alex Ruiz
 */
public class ComponentDriver_invokePopup_Test extends ComponentDriver_invokePopup_TestCase {
  @Test
  public void should_Show_JPopupMenu() {
    showWindow();
    JPopupMenu p = driver.invokePopupMenu(window.textField);
    assertThat(p).isSameAs(popupMenu);
  }

  @Test
  public void should_Show_JPopupMenu_On_Disabled_Component() {
    showWindow();
    disableTextField();
    JPopupMenu p = driver.invokePopupMenu(window.textField);
    assertThat(p).isSameAs(popupMenu);
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Disabled_And_ClickOnDisabledAllowd_Is_False() {
    robot.settings().clickOnDisabledComponentsAllowed(false);
    disableTextField();
    thrown.expectIllegalStateIsDisabledComponent();
    try {
      driver.invokePopupMenu(window.textField);
    } finally {
      assertThatTextFieldIsEmpty();
    }
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    try {
      driver.invokePopupMenu(window.textField);
    } finally {
      assertThatTextFieldIsEmpty();
    }
  }
}
