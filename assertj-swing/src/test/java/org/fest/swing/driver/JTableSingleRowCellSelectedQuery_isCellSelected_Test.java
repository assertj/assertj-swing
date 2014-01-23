/*
 * Created on Aug 10, 2008
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
import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.driver.JTableSelectCellsTask.selectCells;
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JTable;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.data.TableCell;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestTable;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JTableSingleRowCellSelectedQuery#isCellSelected(JTable, int, int)}.
 * 
 * @author Alex Ruiz
 */
public class JTableSingleRowCellSelectedQuery_isCellSelected_Test extends RobotBasedTestCase {
  private JTable table;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    table = window.table;
  }

  @Test
  public void should_return_true_if_only_one_row_and_only_one_column_are_selected() {
    TableCell cell = row(0).column(2);
    selectCells(table, cell, cell);
    robot.waitForIdle();
    assertThat(isCellSelected(table, 0, 2)).isTrue();
  }

  @Test
  public void should_return_false_if_cell_is_not_selected() {
    assertThat(isCellSelected(table, 0, 2)).isFalse();
  }

  @Test
  public void should_return_false_if_whole_row_is_selected() {
    selectRow(table, 0);
    robot.waitForIdle();
    assertThat(isCellSelected(table, 0, 2)).isFalse();
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
  public void should_false_if_multiple_row_are_selected() {
    selectCells(table, row(0).column(2), row(0).column(4));
    robot.waitForIdle();
    assertThat(isCellSelected(table, 0, 2)).isFalse();
  }

  @RunsInEDT
  private static boolean isCellSelected(final JTable table, final int row, final int column) {
    return execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return JTableSingleRowCellSelectedQuery.isCellSelected(table, row, column);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    final JTable table = new TestTable(3, 6);

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JTableSingleRowCellSelectedQuery_isCellSelected_Test.class);
      addComponents(table);
    }
  }
}
