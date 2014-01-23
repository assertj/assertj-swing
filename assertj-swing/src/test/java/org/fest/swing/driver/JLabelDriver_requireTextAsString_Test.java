/*
 * Created on Apr 5, 2008
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
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import javax.swing.JLabel;

import org.junit.Test;

/**
 * Tests for {@link JLabelDriver#requireText(JLabel, String)}.
 * 
 * @author Yvonne Wang
 */
public class JLabelDriver_requireTextAsString_Test extends JLabelDriver_TestCase {
  @Test
  public void should_pass_if_text_is_equal_to_expected() {
    driver.requireText(label, "Hi");
  }

  @Test
  public void should_pass_if_text_matches_expected_pattern() {
    driver.requireText(label, "H.*");
  }

  @Test
  public void should_fail_if_text_is_not_equal_to_expected() {
    try {
      driver.requireText(label, "Bye");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'text'").contains(
          "actual value:<'Hi'> is not equal to or does not match pattern:<'Bye'>");
    }
  }
}
