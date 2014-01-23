/*
 * Created on Dec 25, 2009
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

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import javax.swing.JTable;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link JTableDriver#requireSelectedRows(javax.swing.JTable, int[])}.
 *
 * @author Alex Ruiz
 */
public class JTableDriver_requireSelectedRows_Test extends JTableDriver_TestCase {
  @Test
  public void should_fail_if_JTable_does_not_have_the_expected_selected_rows() {
    selectRows(6, 8);
    try {
      driver.requireSelectedRows(table, 0, 1);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedRows'")
                                .contains("<[6, 7, 8]> does not contain element(s):<[0, 1]>");
    }
  }

  @Test
  public void should_pass_if_JTable_has_expected_selected_rows() {
    selectRows(6, 8);
    driver.requireSelectedRows(table, 6, 7, 8);
  }

  @Test
  public void should_pass_if_JTable_has_expected_selected_rows_in_different_order() {
    selectRows(6, 8);
    driver.requireSelectedRows(table, 8, 7, 6);
  }

  @Test
  public void should_pass_if_expected_selected_rows_are_subset_of_all_selected_rows() {
    selectRows(6, 8);
    driver.requireSelectedRows(table, 6, 7);
  }

  @RunsInEDT
  private void selectRows(int from, int to) {
    selectRows(table, from, to);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void selectRows(final JTable table, final int from, final int to) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        if (from != to) {
          table.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
        }
        table.setRowSelectionInterval(from, to);
      }
    });
  }
}
