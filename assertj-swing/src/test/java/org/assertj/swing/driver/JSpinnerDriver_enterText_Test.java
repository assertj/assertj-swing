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
import static org.assertj.swing.driver.JTextComponentTextQuery.textOf;

import javax.swing.text.JTextComponent;

import org.assertj.swing.exception.ActionFailedException;
import org.junit.Test;

/**
 * Tests for {@link JSpinnerDriver#enterText(javax.swing.JSpinner, String)}.
 * 
 * @author Alex Ruiz
 */
public class JSpinnerDriver_enterText_Test extends JSpinnerDriver_TestCase {
  @Test(expected = ActionFailedException.class)
  public void should_Throw_Error_If_JTextComponentEditor_Not_Found() {
    setJLabelAsEditor();
    showWindow();
    driver.enterText(spinner, "hello");
  }

  @Test
  public void should_Enter_Text() {
    showWindow();
    updateValue("Frodo");
    driver.enterText(spinner, "Gandalf");
    JTextComponent editor = driver.editor(spinner);
    assertThat(textOf(editor)).isEqualTo("Gandalf");
    assertThatValueIs("Frodo");
  }

  @Test
  public void should_Throw_Error_If_JSpinner_Is_Disabled() {
    disableSpinner();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.enterText(spinner, "Gandalf");
  }

  @Test
  public void should_Throw_Error_If_JSpinner_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.enterText(spinner, "Gandalf");
  }
}
