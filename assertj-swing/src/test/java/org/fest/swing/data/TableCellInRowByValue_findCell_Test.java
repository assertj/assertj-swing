/*
 * Created on Dec 28, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.data;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Strings.concat;

import java.awt.Color;
import java.awt.Font;

import javax.annotation.Nonnull;
import javax.swing.JTable;

import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.exception.ActionFailedException;
import org.junit.Test;

/**
 * Tests for {@link TableCellInRowByValue#findCell(JTable, JTableCellReader)}.
 *
 * @author Alex Ruiz
 */
public class TableCellInRowByValue_findCell_Test extends TableCellFinder_TestCase {
  @Test
  public void should_find_cell_in_selected_row() {
    TableCellInRowByValue finder = TableCellInRowByValue.rowWithValue("1-0", "1-1", "1-2", "1-3", "1-4", "1-5").column(
        2);
    TableCell cell = finder.findCell(table, new JTableCellReaderStub());
    assertThat(cell.row).isEqualTo(1);
    assertThat(cell.column).isEqualTo(2);
  }

  @Test(expected = IllegalStateException.class)
  public void should_throw_error_if_size_of_value_array_is_not_equal_to_number_of_columns_in_JTable() {
    TableCellInRowByValue finder = TableCellInRowByValue.rowWithValue("1-0", "1-1", "1-2").column(2);
    finder.findCell(table, new JTableCellReaderStub());
  }

  @Test(expected = ActionFailedException.class)
  public void should_throw_error_if_cell_not_found() {
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
