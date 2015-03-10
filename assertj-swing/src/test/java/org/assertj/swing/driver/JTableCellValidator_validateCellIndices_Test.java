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

import static org.assertj.swing.test.builder.JTables.table;

import org.assertj.swing.data.TableCell;
import org.junit.Test;

/**
 * Tests for {@link JTableCellPreconditions#checkCellIndicesInBounds(javax.swing.JTable, TableCell)}.
 * 
 * @author Alex Ruiz
 */
public class JTableCellValidator_validateCellIndices_Test {
  @Test(expected = IndexOutOfBoundsException.class)
  public void should_Throw_Error_If_JTable_Is_Empty() {
    TableCell cell = TableCell.row(2).column(3);
    JTableCellPreconditions.checkCellIndicesInBounds(table().createNew(), cell);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void should_Throw_Error_If_Row_Is_Negative() {
    TableCell cell = TableCell.row(-2).column(3);
    JTableCellPreconditions.checkCellIndicesInBounds(table().withRowCount(4).withColumnCount(3).createNew(), cell);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void should_Throw_Error_If_Column_Is_Negative() {
    TableCell cell = TableCell.row(2).column(-3);
    JTableCellPreconditions.checkCellIndicesInBounds(table().withRowCount(4).withColumnCount(3).createNew(), cell);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void should_Throw_Error_If_Row_Is_Out_Of_Bounds() {
    TableCell cell = TableCell.row(4).column(2);
    JTableCellPreconditions.checkCellIndicesInBounds(table().withRowCount(4).withColumnCount(3).createNew(), cell);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void should_Throw_Error_If_Column_Is_Out_Of_Bounds() {
    TableCell cell = TableCell.row(0).column(3);
    JTableCellPreconditions.checkCellIndicesInBounds(table().withRowCount(4).withColumnCount(3).createNew(), cell);
  }
}
