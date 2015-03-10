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
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.data.TableCell.row;
import static org.assertj.swing.driver.JTableSelectCellsTask.selectCells;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.JTable;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.data.TableCell;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestTable;
import org.assertj.swing.test.swing.TestWindow;
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
  public void should_Return_True_If_Only_One_Row_And_Only_One_Column_Are_Selected() {
    TableCell cell = row(0).column(2);
    selectCells(table, cell, cell);
    robot.waitForIdle();
    assertThat(isCellSelected(table, 0, 2)).isTrue();
  }

  @Test
  public void should_Return_False_If_Cell_Is_Not_Selected() {
    assertThat(isCellSelected(table, 0, 2)).isFalse();
  }

  @Test
  public void should_Return_False_If_Whole_Row_Is_Selected() {
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
  public void should_False_If_Multiple_Row_Are_Selected() {
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
