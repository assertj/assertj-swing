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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.data.Index.atIndex;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Tests for
 * {@link JTabbedPaneDriver#requireTabTitle(javax.swing.JTabbedPane, java.util.regex.Pattern, org.fest.swing.data.Index)}
 * .
 * 
 * @author Alex Ruiz
 */
public class JTabbedPaneDriver_requireTabTitleAsPattern_Test extends JTabbedPaneDriver_TestCase {
  @Test
  public void should_fail_if_title_does_not_match_pattern() {
    try {
      driver.requireTabTitle(tabbedPane, Pattern.compile("Hello"), atIndex(0));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'titleAt'").contains(
          "actual value:<'One'> does not match pattern:<'Hello'>");
    }
  }

  @Test
  public void should_pass_if_title_matches_pattern() {
    driver.requireTabTitle(tabbedPane, Pattern.compile("O.*"), atIndex(0));
  }
}
