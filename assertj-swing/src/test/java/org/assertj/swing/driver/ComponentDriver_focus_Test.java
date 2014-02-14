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

import static org.assertj.swing.query.ComponentHasFocusQuery.hasFocus;
import static org.assertj.swing.timing.Pause.pause;

import org.assertj.swing.timing.Condition;
import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#focus(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_focus_Test extends ComponentDriver_TestCase {
  @Test
  public void should_give_focus_to_Component() {
    showWindow();
    driver.focus(window.button);
    pause(new Condition("Component has focus") {
      @Override
      public boolean test() {
        return hasFocus(window.button);
      }
    });
  }

  @Test
  public void should_throw_error_if_Component_is_disabled() {
    disableButton();
    thrown.expectIllegalStateIsDisabledComponent();
    try {
      driver.focus(window.button);
    } finally {
      assertThatButtonDoesNotHaveFocus();
    }
  }

  @Test
  public void should_throw_error_if_Component_is_not_showing_on_the_screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    try {
      driver.focus(window.button);
    } finally {
      assertThatButtonDoesNotHaveFocus();
    }
  }
}
