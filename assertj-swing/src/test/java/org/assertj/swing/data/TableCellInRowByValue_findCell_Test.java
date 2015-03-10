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
import static org.assertj.core.util.Strings.concat;

import java.awt.Color;
import java.awt.Font;

import javax.annotation.Nonnull;
import javax.swing.JTable;

import org.assertj.swing.cell.JTableCellReader;
import org.assertj.swing.exception.ActionFailedException;
import org.junit.Test;

/**
 * Tests for {@link TableCellInRowByValue#findCell(JTable, JTableCellReader)}.
 * 
 * @author Alex Ruiz
 */
public class TableCellInRowByValue_findCell_Test extends TableCellFinder_TestCase {
  @Test
  public void should_Find_Cell_In_Selected_Row() {
    TableCellInRowByValue finder = TableCellInRowByValue.rowWithValue("1-0", "1-1", "1-2", "1-3", "1-4", "1-5").column(
        2);
    TableCell cell = finder.findCell(table, new JTableCellReaderStub());
    assertThat(cell.row).isEqualTo(1);
    assertThat(cell.column).isEqualTo(2);
  }

  @Test(expected = IllegalStateException.class)
  public void should_Throw_Error_If_Size_Of_Value_Array_Is_Not_Equal_To_Number_Of_Columns_In_JTable() {
    TableCellInRowByValue finder = TableCellInRowByValue.rowWithValue("1-0", "1-1", "1-2").column(2);
    finder.findCell(table, new JTableCellReaderStub());
  }

  @Test(expected = ActionFailedException.class)
  public void should_Throw_Error_If_Cell_Not_Found() {
    TableCellInRowByValue finder = TableCellInRowByValue.rowWithValue("0", "1", "2", "3", "4", "5").column(2);
    finder.findCell(table, new JTableCellReaderStub());
  }

  private static class JTableCellReaderStub implements JTableCellReader {
    @Override
    public String valueAt(@Nonnull JTable table, int row, int column) {
      return concat(row, "-", column);
    }

    @Override
    public Color foregroundAt(@Nonnull JTable table, int row, int column) {
      return null;
    }

    @Override
    public Font fontAt(@Nonnull JTable table, int row, int column) {
      return null;
    }

    @Override
    public Color backgroundAt(@Nonnull JTable table, int row, int column) {
      return null;
    }
  }
}
