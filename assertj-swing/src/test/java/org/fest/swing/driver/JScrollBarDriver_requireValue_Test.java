/*
 * Created on Feb 24, 2008
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JScrollBarSetValueTask.setValue;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.junit.Test;

/**
 * Tests for {@link JScrollBarDriver#requireValue(javax.swing.JScrollBar, int)}.
 *
 * @author Alex Ruiz
 */
public class JScrollBarDriver_requireValue_Test extends JScrollBarDriver_TestCase {
  @Test
  public void should_pass_if_value_is_equal_to_expected() {
    setValue(scrollBar, 30);
    robot.waitForIdle();
    driver.requireValue(scrollBar, 30);
  }

  @Test
  public void should_fail_if_value_is_not_equal_to_expected() {
    setValue(scrollBar, 30);
    robot.waitForIdle();
    try {
      driver.requireValue(scrollBar, 20);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'value'")
                                .contains("expected:<[2]0> but was:<[3]0>");
    }
  }
}
