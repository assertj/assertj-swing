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

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.exception.ActionFailedException.actionFailure;

import javax.annotation.Nonnull;
import javax.swing.JTable;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.cell.JTableCellReader;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.exception.ActionFailedException;

/**
 * <p>
 * Looks up a cell in the first selected row of a {@code JTable}.
 * </p>
 * 
 * <p>
 * Example:
 * 
 * <pre>
 * // import static org.assertj.swing.data.TableCellInSelectedRow.row;
 * {@link TableCell} cell = dialog.table("records").cell({@link TableCellInSelectedRow#selectedRow() selectedRow}().column(2));
 * </pre>
 * 
 * </p>
 * 
 * @author Alex Ruiz
 */
public class TableCellInSelectedRow implements TableCellFinder {
  /**
   * <p>
   * Starting point for the creation of a {@link TableCellInSelectedRow}.
   * </p>
   * 
   * <p>
   * Example:
   * 
   * <pre>
   * // import static org.assertj.swing.data.TableCellInSelectedRow.row;
   * TableCellInSelectedRow cell = selectedRow().column(2);
   * </pre>
   * 
   * </p>
   * 
   * @return the created builder.
   */
  public static @Nonnull TableCellBuilder selectedRow() {
    return new TableCellBuilder();
  }

  /**
   * Factory of {@link TableCellInSelectedRow}s.
   * 
   * @author Alex Ruiz
   */
  public static class TableCellBuilder {
    /**
     * Creates a new table cell finder.
     * 
     * @param column the column index of the cell to find.
     * @return the created finder.
     */
    public @Nonnull TableCellInSelectedRow column(int column) {
      return new TableCellInSelectedRow(column);
    }
  }

  private final int column;

  protected TableCellInSelectedRow(int column) {
    this.column = column;
  }

  /**
   * Finds a cell in the given {@code JTable} that belongs to the first selected row and has a matching column index.
   * 
   * @param table the target {@code JTable}.
   * @param cellReader knows how to read the contents of a cell in a {@code JTable}.
   * @return the cell found, if any.
   * @throws ActionFailedException if a matching cell could not be found.
   */
  @Override
  public @Nonnull TableCell findCell(@Nonnull JTable table, @Nonnull JTableCellReader cellReader) {
    int selectedRow = selectedRowOf(table);
    if (selectedRow == -1) {
      throw actionFailure("The given JTable does not have any selection");
    }
    return new TableCell(selectedRow, column);
  }

  @RunsInEDT
  private static int selectedRowOf(final JTable table) {
    Integer result = execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return table.getSelectedRow();
      }
    });
    return checkNotNull(result);
  }

  @Override
  public String toString() {
    return String.format("%s[column=%d]", getClass().getName(), column);
  }
}
