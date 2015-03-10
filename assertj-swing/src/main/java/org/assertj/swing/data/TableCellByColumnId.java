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

import static java.lang.String.valueOf;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.core.util.Strings.quote;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.exception.ActionFailedException.actionFailure;
import static org.assertj.swing.query.JTableColumnByIdentifierQuery.columnIndexByIdentifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTable;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.cell.JTableCellReader;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.exception.ActionFailedException;

/**
 * <p>
 * Looks up a cell in a {@code JTable} by column identifier.
 * </p>
 * 
 * <p>
 * Example:
 * 
 * <pre>
 * // import static org.assertj.swing.data.TableCellByColumnId.row;
 * {@link TableCell} cell = dialog.table("records").cell({@link TableCellByColumnId#row(int) row}(3).columnId("firstColumn"));
 * </pre>
 * 
 * </p>
 * 
 * @author Alex Ruiz
 */
public class TableCellByColumnId implements TableCellFinder {
  public final int row;
  public final Object columnId;

  /**
   * Starting point for the creation of a {@link TableCellByColumnId}.
   * <p>
   * Example:
   * 
   * <pre>
   * // import static org.assertj.swing.data.TableCellByColumnId.row;
   * TableCellByColumnId cell = row(5).columnId(&quot;hobbyColumn&quot;);
   * </pre>
   * 
   * </p>
   * 
   * @param row the row index of the table cell to find.
   * @return the created builder.
   */
  public static TableCellBuilder row(int row) {
    return new TableCellBuilder(row);
  }

  /**
   * Factory of {@link TableCellByColumnId}s.
   * 
   * @author Alex Ruiz
   */
  public static class TableCellBuilder {
    private final int row;

    TableCellBuilder(int row) {
      this.row = row;
    }

    /**
     * Creates a new table cell finder using the row index specified in {@link TableCellByColumnId#row(int)} and the
     * column id specified as the argument in this method.
     * 
     * @param columnId the name of the column in the table cell to find.
     * @return the created finder.
     */
    public TableCellByColumnId columnId(@Nonnull Object columnId) {
      return new TableCellByColumnId(row, columnId);
    }
  }

  protected TableCellByColumnId(int row, @Nonnull Object columnId) {
    this.row = row;
    this.columnId = columnId;
  }

  /**
   * Finds a cell in the given {@code JTable} that has a matching row index and column id.
   * 
   * @param table the target {@code JTable}.
   * @param cellReader knows how to read the contents of a cell in a {@code JTable}.
   * @return the cell found, if any.
   * @throws ActionFailedException if a matching cell could not be found.
   */
  @RunsInEDT
  @Override
  public @Nonnull TableCell findCell(@Nonnull JTable table, @Nonnull JTableCellReader cellReader) {
    return findCell(table, row, columnId);
  }

  @RunsInEDT
  private static @Nonnull TableCell findCell(final @Nonnull JTable table, final int row, final @Nonnull Object columnId) {
    TableCell result = execute(new GuiQuery<TableCell>() {
      @Override
      protected @Nullable TableCell executeInEDT() {
        int column = columnIndexByIdentifier(table, columnId);
        if (column == -1) {
          failColumnIndexNotFound(columnId);
        }
        table.convertColumnIndexToView(table.getColumn(columnId).getModelIndex());
        return new TableCell(row, column);
      }
    });
    return checkNotNull(result);
  }

  private static ActionFailedException failColumnIndexNotFound(Object columnId) {
    throw actionFailure(concat("Unable to find a column with id ", quote(columnId)));
  }

  @Override
  public String toString() {
    return concat(getClass().getName(), "[row=", valueOf(row), ", columnId=", quote(columnId), "]");
  }
}