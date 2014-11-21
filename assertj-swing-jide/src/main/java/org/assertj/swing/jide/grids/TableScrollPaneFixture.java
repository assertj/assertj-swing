/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2008-2010 the original author or authors.
 */

package org.fest.swing.jide.grids;

import com.jidesoft.grid.TableScrollPane;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JTableDriver;
import org.fest.swing.fixture.JScrollPaneFixture;
import org.fest.swing.data.TableCell;

import static org.junit.Assert.assertEquals;

/**
 * A FEST fixture for driving a {@link TableScrollPane}.
 * @author Peter Murray
 */
public class TableScrollPaneFixture extends JScrollPaneFixture
        implements ExtendedTableFixture {

  protected JTableDriver _tableDriver;

  public TableScrollPaneFixture(Robot robot, TableScrollPane pane) {
    super(robot, pane);
    checkTableInstance();
  }

  public TableScrollPaneFixture(Robot robot, String name) {
    super(robot, name);
    checkTableInstance();
  }

  public JideTableFixture mainTable() {
    return new JideTableFixture(robot, targetAsTableScrollPane().getMainTable());
  }

  public JideTableFixture rowHeaderTable() {
    return new JideTableFixture(robot, targetAsTableScrollPane().getRowHeaderTable());
  }

  public int rows() {
    return targetAsTableScrollPane().getRowCount();
  }

  public int columns() {
    return targetAsTableScrollPane().getColumnCount();
  }

  public int selectedRow() {
    return targetAsTableScrollPane().getSelectedRow();
  }

  public int[] selectedRows() {
    return targetAsTableScrollPane().getSelectedRows();
  }

  public void requireNoSelection() {
    assertEquals("There should be no selection in the table",
                 -1,
                 targetAsTableScrollPane().getSelectedRow());
  }

  public TableScrollPaneFixture requireSelection(int... rows) {
    FestTableUtil.requireSelection(rows, targetAsTableScrollPane().getSelectedRows());
    return this;
  }

  public TableScrollPaneFixture selectRow(int row) {
    mainTable().selectCell(TableCell.row(row).column(0));
    return this;
  }

  public TableScrollPane targetAsTableScrollPane() {
    return (TableScrollPane)target;
  }

//  public TJideTableScrollPane targetAsTJideTableScrollPane() {
//    if (target instanceof TJideTableScrollPane) {
//      return (TJideTableScrollPane)target;
//    }
//    throw new IllegalStateException("The target is not an instance of a TJideTableScrollPane.");
//  }

  private void checkTableInstance() {
    if (!(target instanceof TableScrollPane)) {
      throw new IllegalStateException("The target instance is not a TableScrollPane");
    }
  }
}
