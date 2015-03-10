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

import org.junit.Test;

/**
 * Tests for {@link JSpinnerDriver#increment(javax.swing.JSpinner)}.
 * 
 * @author Alex Ruiz
 */
public class JSpinnerDriver_increment_Test extends JSpinnerDriver_TestCase {
  @Test
  public void should_Increment_Value() {
    showWindow();
    assertThatFirstValueIsSelected();
    driver.increment(spinner);
    assertThatValueIs("Sam");
  }

  @Test
  public void should_Throw_Error_If_JSpinner_Is_Disabled() {
    disableSpinner();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.increment(spinner);
  }

  @Test
  public void should_Throw_Error_If_JSpinner_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.increment(spinner);
  }
}
