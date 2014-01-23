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

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Arrays.format;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Strings.concat;

import javax.annotation.Nonnull;
import javax.swing.JTable;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ActionFailedException;

/**
 * <p>
 * Looks up a cell in the first found row in {@code JTable} whose values match the given ones.
 * </p>
 * 
 * <p>
 * Example:
 * 
 * <pre>
 * // import static org.fest.swing.data.TableCellInSelectedRow.row;
 * {@link TableCell} cell = dialog.table("records").cell({@link TableCellInRowByValue#rowWithValue(String...) rowWithValue}("column1", "column2", "column3").column(2));
 * </pre>
 * 
 * </p>
 * 
 * @author Alex Ruiz
 * 
 * @since 1.2
 */
public class TableCellInRowByValue implements TableCellFinder {
  /**
   * <p>
   * Starting point for the creation of a {@link TableCellInRowByValue}.
   * </p>
   * 
   * <p>
   * Example:
   * 
   * <pre>
   * // import static org.fest.swing.data.TableCellInRowByValue.rowWithValue;
   * TableCellByColumnId cell = rowWithValue(&quot;column1&quot;, &quot;column2&quot;, &quot;column3&quot;).column(3);
   * </pre>
   * 
   * </p>
   * 
   * @param values the values in the cells of the row we are looking for.
   * @return the created builder.
   * @throws NullPointerException if the given array of values is {@code null}.
   */
  public static @Nonnull TableCellBuilder rowWithValue(@Nonnull String... values) {
    return new TableCellBuilder(values);
  }

  /**
   * Builder of {@link TableCellInSelectedRow}s.
   * 
   * @author Alex Ruiz
   */
  public static class TableCellBuilder {
    private final String[] values;

    /**
     * Creates a new {@link TableCellBuilder}.
     * 
     * @param values the values of the cells of the row to find.
     */
    TableCellBuilder(@Nonnull String[] values) {
      this.values = checkNotNull(values);
    }

    /**
     * Creates a new table cell finder using the row cell values specified in
     * {@link TableCellInRowByValue#rowWithValue(String...)} and the column index specified as the argument in this
     * method.
     * 
     * @param column the index of the column in the table cell to find.
     * @return the created finder.
     */
    public @Nonnull TableCellInRowByValue column(int column) {
      return new TableCellInRowByValue(values, column);
    }
  }

  private final String[] values;
  private final int column;

  /**
   * Creates a new {@link TableCellInRowByValue}.
   * 
   * @param values the values in the cells of the row we are looking for.
   * @param column the index of the column in the table cell to find.
   */
  protected TableCellInRowByValue(@Nonnull String[] values, int column) {
    this.values = values;
    this.column = column;
  }

  /**
   * Finds a cell in the given {@code JTable} that:
   * <ol>
   * <li>is located in the first row whose values match the given ones</li>
   * <li>has a matching row index</li>
   * </ol>
   * 
   * @param table the target {@code JTable}.
   * @param cellReader knows how to read the contents of a cell in a {@code JTable}.
   * @return the cell found, if any.
   * @throws IllegalStateException if the size of values to look up is not equal to the number of columns in the given
   *           {@code JTable}.
   * @throws ActionFailedException if a matching cell could not be found.
   */
  @RunsInEDT
  @Override
  public @Nonnull TableCell findCell(@Nonnull JTable table, @Nonnull JTableCellReader cellReader) {
    int row = findRowIndex(table, cellReader, values);
    if (row == -1) {
      throw actionFailure(concat("Unable to find a row with values:<", format(values), ">"));
    }
    return new TableCell(row, column);
  }

  @RunsInEDT
  private static int findRowIndex(final @Nonnull JTable table, final @Nonnull JTableCellReader cellReader,
      final @Nonnull String[] values) {
    Integer result = execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        validateEqualSize(table, values);
        int rowCount = table.getRowCount();
        for (int row = 0; row < rowCount; row++) {
          if (matchingRow(table, cellReader, values, row)) {
            return row;
          }
        }
        return -1;
      }
    });
    return checkNotNull(result);
  }

  @RunsInCurrentThread
  private static void validateEqualSize(final @Nonnull JTable table, final @Nonnull String[] values) {
    int columnCount = table.getColumnCount();
    if (values.length != columnCount) {
      throw new IllegalStateException(concat("The array of values should have size:<", columnCount, ">"));
    }
  }

  @RunsInCurrentThread
  private static boolean matchingRow(@Nonnull JTable table, @Nonnull JTableCellReader cellReader,
      @Nonnull String[] values, int row) {
    int columnCount = table.getColumnCount();
    for (int col = 0; col < columnCount; col++) {
      if (!areEqual(cellReader.valueAt(table, row, col), values[col])) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString() {
    return String.format("%s[values=%s, column=%d]", getClass().getName(), format(values), column);
  }
}
