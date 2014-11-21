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

import javax.swing.*;
import com.jidesoft.grid.JideTable;
import org.fest.swing.core.Robot;
import org.fest.swing.jide.grids.driver.JideTableCellReader;
import org.fest.swing.fixture.JTableFixture;

/**
 * A Fixture for dealing with {@link JideTable}s.
 * @author Peter Murray
 */
public class JideTableFixture extends JTableFixture implements ExtendedTableFixture {

  public JideTableFixture(Robot robot, String name) {
    super(robot, name);
    checkTableInstance();
    cellReader(new JideTableCellReader());
  }

  public JideTableFixture(Robot robot, JTable table) {
    super(robot, table);
    checkTableInstance();
    cellReader(new JideTableCellReader());
  }

  public JideTable targetAsJideTable() {
    return (JideTable)target;
  }

  public int selectedRow() {
    return target.getSelectedRow();
  }

  public int[] selectedRows() {
    return target.getSelectedRows();
  }

  public int rows() {
    return target.getRowCount();
  }

  public int columns() {
    return target.getColumnCount();
  }

  public JideTableFixture requireSelection(int... rows) {
    FestTableUtil.requireSelection(rows, selectedRows());
    return this;
  }

  /*-------------------------------------------------------------------------*\
   * PRIVATE METHODS
  \*-------------------------------------------------------------------------*/

  private void checkTableInstance() {
    if (!(target instanceof JideTable)) {
      throw new UnsupportedOperationException("The table instance is not a JideTable");
    }
  }
}
