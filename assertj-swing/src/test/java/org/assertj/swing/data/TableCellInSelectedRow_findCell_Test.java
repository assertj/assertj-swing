/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;

import javax.swing.JTable;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.cell.JTableCellReader;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.exception.ActionFailedException;
import org.assertj.swing.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link TableCellInSelectedRow#findCell(JTable, JTableCellReader)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class TableCellInSelectedRow_findCell_Test extends TableCellFinder_TestCase {
  @Rule
  public ExpectedException thrown = none();

  private TableCellInSelectedRow finder;

  @Override
  void extraSetUp() {
    finder = TableCellInSelectedRow.selectedRow().column(2);
  }

  @Test
  public void should_Find_Cell_In_Selected_Row() {
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
  public void should_Throw_Error_If_JTable_Does_Not_Have_Selection() {
    thrown.expect(ActionFailedException.class, "The given JTable does not have any selection");
    finder.findCell(table, null);
  }
}
