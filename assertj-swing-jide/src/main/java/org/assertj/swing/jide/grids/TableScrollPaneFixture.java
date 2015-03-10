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
package org.assertj.swing.jide.grids;

import static org.junit.Assert.assertEquals;

import org.assertj.swing.core.Robot;
import org.assertj.swing.data.TableCell;
import org.assertj.swing.driver.JTableDriver;
import org.assertj.swing.fixture.JScrollPaneFixture;

import com.jidesoft.grid.TableScrollPane;

/**
 * A FEST fixture for driving a {@link TableScrollPane}.
 * 
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
	return new JideTableFixture(robot(), targetAsTableScrollPane().getMainTable());
  }

  public JideTableFixture rowHeaderTable() {
	return new JideTableFixture(robot(), targetAsTableScrollPane().getRowHeaderTable());
  }

  @Override
  public int rows() {
	return targetAsTableScrollPane().getRowCount();
  }

  @Override
  public int columns() {
	return targetAsTableScrollPane().getColumnCount();
  }

  @Override
  public int selectedRow() {
	return targetAsTableScrollPane().getSelectedRow();
  }

  @Override
  public int[] selectedRows() {
	return targetAsTableScrollPane().getSelectedRows();
  }

  public void requireNoSelection() {
	assertEquals("There should be no selection in the table",
	             -1,
	             targetAsTableScrollPane().getSelectedRow());
  }

  @Override
  public TableScrollPaneFixture requireSelection(int... rows) {
	AssertJSwingTableUtil.requireSelection(rows, targetAsTableScrollPane().getSelectedRows());
	return this;
  }

  public TableScrollPaneFixture selectRow(int row) {
	mainTable().selectCell(TableCell.row(row).column(0));
	return this;
  }

  public TableScrollPane targetAsTableScrollPane() {
	return (TableScrollPane) target();
  }

  private void checkTableInstance() {
	if (!(target() instanceof TableScrollPane)) {
	  throw new IllegalStateException("The target instance is not a TableScrollPane");
	}
  }
}
