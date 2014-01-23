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
import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.driver.JTableCellValueQuery.cellValueOf;
import static org.fest.swing.driver.JTableDriver_TestCase.MyWindow.COLUMN_COUNT;
import static org.fest.swing.driver.JTableDriver_TestCase.MyWindow.ROW_COUNT;
import static org.fest.swing.driver.JTableRowCountQuery.rowCountOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.swing.TestTable.columnNames;
import static org.fest.swing.test.swing.TestTable.createCellValueFrom;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestTable;
import org.junit.Test;

/**
 * Tests for {@link JTableDriver#drag(javax.swing.JTable, org.fest.swing.data.TableCell)} and
 * {@link JTableDriver#drop(javax.swing.JTable, org.fest.swing.data.TableCell)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableDriver_drag_drop_Test extends JTableDriver_TestCase {
  private static final int DROP_TABLE_ROW_COUNT = 2;

  private TestTable dropTable;

  @Override
  void extraSetUp() {
    dropTable = execute(new GuiQuery<TestTable>() {
      @Override
      protected TestTable executeInEDT() {
        TestTable t = new TestTable(dropTableData(DROP_TABLE_ROW_COUNT, COLUMN_COUNT), columnNames(COLUMN_COUNT));
        window.addTable(t);
        return t;
      }
    });
  }

  private static Object[][] dropTableData(int rowCount, int columnCount) {
    Object[][] data = new Object[rowCount][columnCount];
    for (int i = 0; i < rowCount; i++) {
      for (int j = 0; j < columnCount; j++) {
        data[i][j] = createCellValueFrom(ROW_COUNT + i, j);
      }
    }
    return data;
  }

  @Test
  public void should_drag_and_drop() {
    showWindow();
    driver.drag(table, row(3).column(0));
    driver.drop(dropTable, row(1).column(0));
    assertThatRowWasDraggedFromTable();
    assertThatRowWasDroppedAtTable();
  }

  private void assertThatRowWasDraggedFromTable() {
    assertThat(rowCountOf(table)).isEqualTo(ROW_COUNT - 1);
    assertThat(cellValueOf(table, 3, 0)).isEqualTo(createCellValueFrom(4, 0));
  }

  private void assertThatRowWasDroppedAtTable() {
    assertThat(rowCountOf(dropTable)).isEqualTo(DROP_TABLE_ROW_COUNT + 1);
    assertThat(cellValueOf(dropTable, 2, 0)).isEqualTo(createCellValueFrom(3, 0));
  }
}
