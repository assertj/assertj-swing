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
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.junit.Test;

/**
 * Tests for {@link JListDriver#requireSelectedItems(javax.swing.JList, int...)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver_requireSelectedItemsByIndex_Test extends JListDriver_TestCase {
  @Test
  public void should_fail_if_there_is_no_selection() {
    clearSelection();
    try {
      driver.requireSelectedItems(list, 0, 1);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndices'").contains("expected:<[0, 1]> but was:<[]>");
    }
  }

  @Test
  public void should_fail_if_selection_is_not_equal_to_expected() {
    select(2);
    try {
      driver.requireSelectedItems(list, 0);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndices'").contains("expected:<[0]> but was:<[2]>");
    }
  }

  @Test
  public void should_pass_if_selection_is_equal_to_expected() {
    select(0, 1);
    driver.requireSelectedItems(list, 0, 1);
  }
}
