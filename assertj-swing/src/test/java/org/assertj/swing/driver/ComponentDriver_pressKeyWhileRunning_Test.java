/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2018 the original author or authors.
 */
package org.assertj.swing.driver;

import static java.awt.event.KeyEvent.VK_A;
import static org.mockito.Mockito.mock;

import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#pressKeyWhileRunning(java.awt.Component, int, Runnable)}.
 *
 * @author Christian Rösch
 */
public class ComponentDriver_pressKeyWhileRunning_Test extends ComponentDriver_TestCase {
  @Test
  public void should_Throw_Error_If_Component_Is_Disabled() {
    disableTextField();
    thrown.expectIllegalStateIsDisabledComponent();
    try {
      driver.pressKeyWhileRunning(window.textField, VK_A, mock(Runnable.class));
    } finally {
      assertThatTextFieldIsEmpty();
    }
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
    assertThatTextFieldIsEmpty();
    thrown.expectIllegalStateIsNotShowingComponent();
    try {
      driver.pressKeyWhileRunning(window.textField, VK_A, mock(Runnable.class));
    } finally {
      assertThatTextFieldIsEmpty();
    }
  }
}
