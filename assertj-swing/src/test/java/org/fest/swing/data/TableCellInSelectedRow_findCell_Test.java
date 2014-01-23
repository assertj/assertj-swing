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
package org.fest.swing.data;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import javax.swing.JTable;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.junit.Test;

/**
 * Tests for {@link TableCellInSelectedRow#findCell(JTable, JTableCellReader)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class TableCellInSelectedRow_findCell_Test extends TableCellFinder_TestCase {
  private TableCellInSelectedRow finder;

  @Override
  void extraSetUp() {
    finder = TableCellInSelectedRow.selectedRow().column(2);
  }

  @Test
  public void should_find_cell_in_selected_row() {
    selectRow(1);
    TableCell cell = finder.findCell(table, null);
    assertThat(cell.row).isEqualTo(1);
    assertThat(cell.column).isEqualTo(2);
  }

  @RunsInEDT
  private void selectRow(int row) {
    selectRow(table, row);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void selectRow(final JTable table, final int row) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        table.setRowSelectionInterval(row, row);
      }
    });
  }

  @Test
  public void should_throw_error_if_JTable_does_not_have_selection() {
    try {
      finder.findCell(table, null);
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertThat(e.getMessage()).isEqualTo("The given JTable does not have any selection");
    }
  }
}
