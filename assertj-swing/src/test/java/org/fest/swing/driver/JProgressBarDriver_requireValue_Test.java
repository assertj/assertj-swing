/*
 * Created on Dec 1, 2009
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import javax.swing.JProgressBar;

import org.junit.Test;

/**
 * Tests for {@link JProgressBarDriver#requireValue(JProgressBar, int)}.
 *
 * @author Alex Ruiz
 */
public class JProgressBarDriver_requireValue_Test extends JProgressBarDriver_TestCase {
  @Test
  public void should_pass_if_value_is_equal_to_expected() {
    driver.requireValue(progressBar, 60);
  }

  @Test
  public void should_fail_if_value_is_not_equal_to_expected() {
    try {
      driver.requireValue(progressBar, 50);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'value'")
                                .contains("expected:<[5]0> but was:<[6]0>");
    }
  }
}
