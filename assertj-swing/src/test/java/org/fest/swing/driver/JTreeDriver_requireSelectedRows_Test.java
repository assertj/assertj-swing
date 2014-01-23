/*
 * Created on Jul 16, 2009
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

import org.junit.Test;

/**
 * Tests for {@link JTreeDriver#requireSelection(javax.swing.JTree, int[])}.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_requireSelectedRows_Test extends JTreeDriver_selectCell_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_array_of_indices_is_null() {
    driver.requireSelection(tree, (int[]) null);
  }

  @Test
  public void should_pass_if_single_cell_is_selected() {
    selectFirstChildOfRoot();
    int[] rowsToSelect = new int[] { 1 };
    driver.requireSelection(tree, rowsToSelect);
  }

  @Test
  public void should_fail_if_JTree_does_not_have_selection() {
    clearTreeSelection();
    try {
      driver.requireSelection(tree, new int[] { 3 });
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selection'").contains("No selection");
    }
  }

  @Test
  public void should_fail_if_selection_is_not_equal_to_expected() {
    selectFirstChildOfRoot();
    try {
      driver.requireSelection(tree, new int[] { 5 });
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selection'").contains("expecting selection:<[5]> but was:<[1]>");
    }
  }
}
