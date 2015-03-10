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

import javax.swing.JTable;

import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.JTableFixture;
import org.assertj.swing.jide.grids.driver.JideTableCellReader;

import com.jidesoft.grid.JideTable;

/**
 * A Fixture for dealing with {@link JideTable}s.
 * 
 * @author Peter Murray
 */
public class JideTableFixture extends JTableFixture implements ExtendedTableFixture {

  public JideTableFixture(Robot robot, String name) {
	super(robot, name);
	checkTableInstance();
	driver().replaceCellReader(new JideTableCellReader());
  }

  public JideTableFixture(Robot robot, JTable table) {
	super(robot, table);
	checkTableInstance();
	driver().replaceCellReader(new JideTableCellReader());
  }

  public JideTable targetAsJideTable() {
	return (JideTable) target();
  }

  @Override
  public int selectedRow() {
	return target().getSelectedRow();
  }

  @Override
  public int[] selectedRows() {
	return target().getSelectedRows();
  }

  @Override
  public int rows() {
	return target().getRowCount();
  }

  @Override
  public int columns() {
	return target().getColumnCount();
  }

  @Override
  public JideTableFixture requireSelection(int... rows) {
	AssertJSwingTableUtil.requireSelection(rows, selectedRows());
	return this;
  }

  private void checkTableInstance() {
	if (!(target() instanceof JideTable)) {
	  throw new UnsupportedOperationException("The table instance is not a JideTable");
	}
  }
}
