/*
 * Created on Oct 13, 2008
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

import static org.fest.swing.test.builder.JTables.table;

import org.fest.swing.data.TableCell;
import org.junit.Test;

/**
 * Tests for {@link JTableCellPreconditions#checkCellIndicesInBounds(javax.swing.JTable, TableCell)}.
 * 
 * @author Alex Ruiz
 */
public class JTableCellValidator_validateCellIndices_Test {
  @Test(expected = IndexOutOfBoundsException.class)
  public void should_throw_error_if_JTable_is_empty() {
    TableCell cell = TableCell.row(2).column(3);
    JTableCellPreconditions.checkCellIndicesInBounds(table().createNew(), cell);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void should_throw_error_if_row_is_negative() {
    TableCell cell = TableCell.row(-2).column(3);
    JTableCellPreconditions.checkCellIndicesInBounds(table().withRowCount(4).withColumnCount(3).createNew(), cell);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void should_throw_error_if_column_is_negative() {
    TableCell cell = TableCell.row(2).column(-3);
    JTableCellPreconditions.checkCellIndicesInBounds(table().withRowCount(4).withColumnCount(3).createNew(), cell);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void should_throw_error_if_row_is_out_of_bounds() {
    TableCell cell = TableCell.row(4).column(2);
    JTableCellPreconditions.checkCellIndicesInBounds(table().withRowCount(4).withColumnCount(3).createNew(), cell);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void should_throw_error_if_column_is_out_of_bounds() {
    TableCell cell = TableCell.row(0).column(3);
    JTableCellPreconditions.checkCellIndicesInBounds(table().withRowCount(4).withColumnCount(3).createNew(), cell);
  }
}
