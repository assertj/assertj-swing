/*
 * Created on Feb 25, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.junit.Test;

/**
 * Tests for {@link JSpinnerDriver#increment(javax.swing.JSpinner, int)}.
 * 
 * @author Alex Ruiz
 */
public class JSpinnerDriver_incrementWithTimes_Test extends JSpinnerDriver_TestCase {
  @Test
  public void should_increment_value() {
    showWindow();
    assertThatFirstValueIsSelected();
    driver.increment(spinner, 2);
    assertThatLastValueIsSelected();
  }

  @Test
  public void should_throw_error_if_JSpinner_is_disabled() {
    disableSpinner();
    try {
      driver.increment(spinner, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JSpinner_is_not_showing_on_the_screen() {
    try {
      driver.increment(spinner, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }
}
