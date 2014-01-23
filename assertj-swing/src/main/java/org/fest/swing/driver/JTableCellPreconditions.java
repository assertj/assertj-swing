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

import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.swing.JTable;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.data.TableCell;

/**
 * Verifies correct argument values and state of {@code JTable} cells.
 * 
 * @author Alex Ruiz
 */
public final class JTableCellPreconditions {
  /**
   * <p>
   * Verifies that the table cell in the given coordinates is editable.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param table the target {@code JTable}.
   * @param row the row index of the cell to validate.
   * @param column the column index of the cell to validate.
   * @throws IllegalStateException if the table cell in the given coordinates is not editable.
   */
  @RunsInCurrentThread
  public static void validateCellIsEditable(@Nonnull JTable table, int row, int column) {
    if (!table.isCellEditable(row, column)) {
      String msg = String.format("Expecting cell [%d, %d] to be editable", row, column);
      throw new IllegalStateException(msg);
    }
  }

  /**
   * <p>
   * Verifies that the given table cell is non {@code null} and its indices are not out of bounds.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param table the target {@code JTable}.
   * @param cell the cell to validate.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  @RunsInCurrentThread
  public static void checkCellIndicesInBounds(@Nonnull JTable table, @Nonnull TableCell cell) {
    checkNotNull(cell);
    checkCellIndicesInBounds(table, cell.row, cell.column);
  }

  /**
   * <p>
   * Verifies that the given indices regarding the given table.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param table the {@code JTable} to use to validate the given indices.
   * @param row the row index to validate.
   * @param column the column index to validate.
   * @throws IndexOutOfBoundsException if any of the indices is out of bounds or if the {@code JTable} does not have any
   *           rows.
   */
  @RunsInCurrentThread
  public static void checkCellIndicesInBounds(@Nonnull JTable table, int row, int column) {
    if (table.getRowCount() == 0) {
      throw new IndexOutOfBoundsException("Table does not contain any rows");
    }
    checkRowInBounds(table, row);
    checkColumnInBounds(table, column);
  }

  /**
   * <p>
   * Verifies that the given row index exists in the given table.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param table the table the given table.
   * @param row the row to validate.
   * @throws IndexOutOfBoundsException if the row index is out of bounds.
   */
  @RunsInCurrentThread
  public static void checkRowInBounds(@Nonnull JTable table, int row) {
    checkIndexInBounds(row, table.getRowCount(), "row");
  }

  /**
   * <p>
   * Verifies that the given column index exists in the given table.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param table the table the given table.
   * @param column the column to validate.
   * @throws IndexOutOfBoundsException if the column index is out of bounds.
   */
  @RunsInCurrentThread
  public static void checkColumnInBounds(@Nonnull JTable table, int column) {
    checkIndexInBounds(column, table.getColumnCount(), "column");
  }

  @RunsInCurrentThread
  private static void checkIndexInBounds(int index, int itemCount, @Nonnull String indexName) {
    if (index >= 0 && index < itemCount) {
      return;
    }
    String msg = String.format("%s <%d> should be between <0> and <%d>", indexName, index, itemCount - 1);
    throw new IndexOutOfBoundsException(msg);
  }

  private JTableCellPreconditions() {}
}
