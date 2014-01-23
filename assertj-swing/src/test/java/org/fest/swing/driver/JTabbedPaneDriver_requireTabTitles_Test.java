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
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.util.Arrays.array;

import org.junit.Test;

/**
 * Tests for {@link JTabbedPaneDriver#requireTabTitles(javax.swing.JTabbedPane, String[])}.
 * 
 * @author Alex Ruiz
 */
public class JTabbedPaneDriver_requireTabTitles_Test extends JTabbedPaneDriver_TestCase {
  @Test
  public void should_fail_if_titles_are_not_equal_to_expected() {
    try {
      driver.requireTabTitles(tabbedPane, array("Three", "Four"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'tabTitles'").contains(
          "expected:<['Three', 'Four']> but was:<['One', 'Two']>");
    }
  }

  @Test
  public void should_pass_if_titles_are_equal_to_expected() {
    driver.requireTabTitles(tabbedPane, array("One", "Two"));
  }
}
