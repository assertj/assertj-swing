/*
 * Created on Dec 22, 2009
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

import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.test.ExpectedException.none;

import org.fest.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link JTableDriver#selectRows(javax.swing.JTable, int...)}.
 *
 * @author Alex Ruiz
 */
public class JTableDriver_selectRows_Test extends JTableDriver_TestCase {
  @Rule
  public ExpectedException thrown = none();

  @Test
  public void should_throw_error_if_index_is_negative() {
    showWindow();
    thrown.expect(IndexOutOfBoundsException.class, "row <-1> should be between <0> and <9>");
    driver.selectRows(table, -1);
  }

  @Test
  public void should_throw_error_if_index_is_equal_to_the_number_of_rows() {
    showWindow();
    thrown.expect(IndexOutOfBoundsException.class, "row <10> should be between <0> and <9>");
    driver.selectRows(table, 10);
  }

  @Test
  public void should_select_rows() {
    enableMultipleSelection();
    showWindow();
    driver.selectRows(table, 0, 2);
    requireCellSelected(0, 0);
    requireCellSelected(2, 0);
  }

  @Test
  public void should_select_row() {
    enableMultipleSelection();
    showWindow();
    driver.selectRows(table, 0);
    requireCellSelected(0, 0);
  }

  @Test
  public void should_throw_error_if_JTable_is_disabled() {
    disableTable();
    try {
      driver.selectRows(table, 0, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JTable_is_not_showing_on_the_screen() {
    try {
      driver.selectRows(table, 0, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }
}
