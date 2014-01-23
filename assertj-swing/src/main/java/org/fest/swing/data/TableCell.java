/*
 * Created on Mar 2, 2008
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
package org.fest.swing.data;

import static org.fest.util.Objects.HASH_CODE_PRIME;

import javax.annotation.Nonnull;

/**
 * A cell in a {@code JTable}.
 *
 * @author Alex Ruiz
 */
public class TableCell {
  /** The row of the cell. */
  public final int row;

  /** The column of the cell. */
  public final int column;

  /**
   * <p>
   * Starting point for the creation of a {@link TableCell}.
   * </p>
   *
   * <p>
   * Example:
   * <pre>
   * // import static org.fest.swing.data.TableCell.row;
   * TableCell cell = row(5).column(3);
   * </pre>
   * </p>
   *
   * @param row the row index of the table cell to create.
   * @return the created builder.
   */
  public static @Nonnull TableCellBuilder row(int row) {
    return new TableCellBuilder(row);
  }

  /**
   * Factory of {@link TableCell}s.
   *
   * @author Alex Ruiz
   */
  public static class TableCellBuilder {
    private final int row;

    TableCellBuilder(int row) {
      this.row = row;
    }

    /**
     * Creates a new table cell using the row index specified in {@link TableCellBuilder#row(int)} and the
     * column index specified as the argument in this method.
     *
     * @param column the column index of the table cell to create.
     * @return the created table cell.
     */
    public @Nonnull TableCell column(int column) {
      return new TableCell(row, column);
    }
  }

  /**
   * Creates a new {@link TableCell}.
   *
   * @param row the row of the cell.
   * @param column the column of the cell.
   */
  protected TableCell(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /** ${@inheritDoc} */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof TableCell)) {
      return false;
    }
    TableCell other = (TableCell) obj;
    if (row != other.row) {
      return false;
    }
    return column == other.column;
  }

  /** ${@inheritDoc} */
  @Override
  public int hashCode() {
    int result = 1;
    result = HASH_CODE_PRIME * result + column;
    result = HASH_CODE_PRIME * result + row;
    return result;
  }

  @Override
  public String toString() {
    return String.format("[row=%d, column=%d]", row, column);
  }
}
