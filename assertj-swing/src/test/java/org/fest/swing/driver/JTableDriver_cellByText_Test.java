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

import org.fest.swing.data.TableCell;
import org.fest.swing.exception.ActionFailedException;
import org.junit.Test;

/**
 * Tests for {@link JTableDriver#cell(javax.swing.JTable, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableDriver_cellByText_Test extends JTableDriver_TestCase {
  @Test
  public void should_find_cell_having_value_that_matches_given_pattern() {
    TableCell cell = driver.cell(table, "1.*");
    assertThat(cell.row).isEqualTo(1);
    assertThat(cell.column).isEqualTo(0);
    assertThatCellReaderWasCalled();
  }

  @Test
  public void should_throw_error_if_a_matching_cell_was_not_found() {
    try {
      driver.cell(table, "Hello World");
      failWhenExpectingException();
    } catch (ActionFailedException expected) {
      assertThat(expected.getMessage()).contains("Unable to find cell matching value 'Hello World'");
    }
  }
}
