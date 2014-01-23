/*
 * Created on Jul 12, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.swing.driver.ComponentDriver.propertyName;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Strings.concat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.fest.assertions.Description;
import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.data.TableCell;
import org.fest.swing.data.TableCellFinder;
import org.fest.swing.driver.BasicJTableCellReader;
import org.fest.swing.driver.JTableDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;

/**
 * <p>
 * Supports functional testing of {@code JTable}s.
 * </p>
 *
 * <p>
 * The conversion between the values given in tests and the values being displayed by a {@code JTable} renderer is
 * performed by a {@link JTableCellReader}. This fixture uses a {@link BasicJTableCellReader} by default.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Fabien Barbero
 * @author Andriy Tsykholyas
 */
public class JTableFixture extends AbstractJPopupMenuInvokerFixture<JTableFixture, JTable, JTableDriver> {
  /**
   * Creates a new {@link JTableFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JTable}.
   * @param target the {@code JTable} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JTableFixture(@Nonnull Robot robot, @Nonnull JTable target) {
    super(JTableFixture.class, robot, target);
  }

  /**
   * Creates a new {@link JTableFixture}.
   *
   * @param robot performs simulation of user events on a {@code JTable}.
   * @param tableName the name of the {@code JTable} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JTable} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JTable} is found.
   */
  public JTableFixture(@Nonnull Robot robot, @Nullable String tableName) {
    super(JTableFixture.class, robot, tableName, JTable.class);
  }

  @Override
  protected @Nonnull JTableDriver createDriver(@Nonnull Robot robot) {
    return new JTableDriver(robot);
  }

  /**
   * Returns a fixture that verifies the font of the given table cell.
   *
   * @param cell the given table cell.
   * @return a fixture that verifies the font of the given table cell.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public FontFixture fontAt(@Nonnull TableCell cell) {
    Font font = driver().font(target(), cell);
    return new FontFixture(checkNotNull(font), cellProperty(cell, FONT_PROPERTY));
  }

  /**
   * Returns a fixture that verifies the background color of the given table cell.
   *
   * @param cell the given table cell.
   * @return a fixture that verifies the background color of the given table cell.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public @Nonnull ColorFixture backgroundAt(@Nonnull TableCell cell) {
    Color background = driver().background(target(), cell);
    return new ColorFixture(checkNotNull(background), cellProperty(cell, BACKGROUND_PROPERTY));
  }

  /**
   * Returns a fixture that verifies the foreground color of the given table cell.
   *
   * @param cell the given table cell.
   * @return a fixture that verifies the foreground color of the given table cell.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public @Nonnull ColorFixture foregroundAt(@Nonnull TableCell cell) {
    Color foreground = driver().foreground(target(), cell);
    return new ColorFixture(checkNotNull(foreground), cellProperty(cell, FOREGROUND_PROPERTY));
  }

  private @Nonnull Description cellProperty(TableCell cell, String propertyName) {
    return propertyName(target(), concat(propertyName, " ", cell));
  }

  /**
   * Returns a {@link JTableCellFixture} wrapping the table cell whose value matches the given one.
   *
   * @param value the value of the cell to look for. It can be a regular expression.
   * @return a {@code JTableCellFixture} wrapping the table cell whose value matches the given one.
   * @throws ActionFailedException if a cell with a matching value cannot be found.
   */
  public @Nonnull JTableCellFixture cell(String value) {
    TableCell cell = driver().cell(target(), value);
    return new JTableCellFixture(this, cell);
  }

  /**
   * Returns a {@link JTableCellFixture} wrapping the table cell whose value matches the given regular expression
   * pattern.
   *
   * @param valuePattern the regular expression pattern to match.
   * @return a {@code JTableCellFixture} wrapping the table cell whose value matches the given regular expression
   *         pattern.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws ActionFailedException if a cell with a matching value cannot be found.
   * @since 1.2
   */
  public @Nonnull JTableCellFixture cell(@Nonnull Pattern valuePattern) {
    TableCell cell = driver().cell(target(), valuePattern);
    return new JTableCellFixture(this, cell);
  }

  /**
   * Returns a {@link JTableCellFixture} wrapping the table cell found by the given {@link TableCellFinder}.
   *
   * @param cellFinder knows how to find a cell.
   * @return a {@code JTableCellFixture} wrapping the table cell found by the given {@code TableCellFinder}
   * @throws NullPointerException if the {@code TableCellFinder} is {@code null}.
   * @throws ActionFailedException if a matching cell could not be found.
   * @throws IndexOutOfBoundsException if the row or column indices in the found cell are out of bounds.
   */
  public @Nonnull JTableCellFixture cell(@Nonnull TableCellFinder cellFinder) {
    TableCell cell = driver().cell(target(), cellFinder);
    return new JTableCellFixture(this, cell);
  }

  /**
   * Returns a {@link JTableCellFixture} wrapping the table cell specified by the given row and column.
   *
   * @param cell the cell of interest.
   * @return a {@code JTableCellFixture} wrapping the table cell specified by the given row and column.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public @Nonnull JTableCellFixture cell(@Nonnull TableCell cell) {
    driver().checkCellIndicesInBounds(target(), cell);
    return new JTableCellFixture(this, cell);
  }

  /**
   * @return a {@code JTableHeaderFixture} wrapping the {@code JTableHeader} in this fixture's {@code JTable}.
   * @throws AssertionError if the {@code JTableHeader} in this fixture's {@code JTable} is {@code null}.
   */
  public @Nonnull JTableHeaderFixture tableHeader() {
    JTableHeader tableHeader = driver().tableHeaderOf(target());
    return new JTableHeaderFixture(robot(), checkNotNull(tableHeader));
  }

  /**
   * Returns the {@code String} representation of the selected cell in this fixture's {@code JTable}, using this
   * fixture's {@link JTableCellReader}. Returns {@code null} if one can not be obtained or if the {@code JTable} does
   * not have any selected cell.
   *
   * @return the {@code String} representation of the selected cell.
   * @see #replaceCellReader(JTableCellReader)
   */
  public @Nullable String selectionValue() {
    return driver().selectionValue(target());
  }

  /**
   * Converts the given cell into a coordinate pair.
   *
   * @param cell the given cell.
   * @return the coordinates of the given cell.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public @Nonnull Point pointAt(@Nonnull TableCell cell) {
    return driver().pointAt(target(), cell);
  }

  /**
   * @return the {@code String} representation of the cells in the cells in this fixture's {@code JTable}, using this
   * fixture's {@link JTableCellReader}.
   * @see #replaceCellReader(JTableCellReader)
   */
  public @Nonnull String[][] contents() {
    return driver().contents(target());
  }

  /**
   * Returns the number of rows that can be shown in this fixture's {@code JTable}, given unlimited space.
   *
   * @return the number of rows shown in this fixture's {@code JTable}.
   * @see JTable#getRowCount()
   */
  public int rowCount() {
    return driver().rowCountOf(target());
  }

  /**
   * Returns the {@code String} representation of the value of a cell in this fixture's {@code JTable}, using this
   * fixture's {@link JTableCellReader}.
   *
   * @param cell the given cell.
   * @return the {@code String} representation of the value of a cell in this fixture's {@code JTable}.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @see #replaceCellReader(JTableCellReader)
   */
  public @Nullable String valueAt(@Nonnull TableCell cell) {
    return driver().value(target(), cell);
  }

  /**
   * Simulates a user selecting the given cell (row and column) of this fixture's {@code JTable}.
   *
   * @param cell the cell to select.
   * @return this fixture.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public @Nonnull JTableFixture selectCell(@Nonnull TableCell cell) {
    driver().selectCell(target(), cell);
    return this;
  }

  /**
   * Simulates a user selecting the given cells of this fixture's {@code JTable}.
   *
   * @param cells the cells to select.
   * @return this fixture.
   * @throws NullPointerException if {@code cells} is {@code null} or empty.
   * @throws IllegalArgumentException if {@code cells} is {@code null} or empty.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   * @throws NullPointerException if any element in {@code cells} is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices of any of the {@code cells} are out of bounds.
   */
  public @Nonnull JTableFixture selectCells(@Nonnull TableCell... cells) {
    driver().selectCells(target(), cells);
    return this;
  }

  /**
   * Simulates a user selecting the given rows in this fixture's {@code JTable}.
   *
   * @param rows the indices of the row to select.
   * @return this fixture.
   * @throws NullPointerException if the given array of indices is {@code null}.
   * @throws IllegalArgumentException if the given array of indices is empty.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the given indices is out of bounds.
   * @since 1.2
   */
  public @Nonnull JTableFixture selectRows(@Nonnull int... rows) {
    driver().selectRows(target(), rows);
    return this;
  }

  /**
   * Simulates a user dragging an item from this fixture's {@code JTable}.
   *
   * @param cell the cell to drag.
   * @return this fixture.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public @Nonnull JTableFixture drag(@Nonnull TableCell cell) {
    driver().drag(target(), cell);
    return this;
  }

  /**
   * Simulates a user dropping an item to this fixture's {@code JTable}.
   *
   * @param cell the cell to drop the dragging item into.
   * @return this fixture.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public @Nonnull JTableFixture drop(@Nonnull TableCell cell) {
    driver().drop(target(), cell);
    return this;
  }

  /**
   * Simulates a user clicking a cell in this fixture's {@code JTable} once, using the specified mouse button.
   *
   * @param cell the cell to click.
   * @param button the mouse button to use.
   * @return this fixture.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public @Nonnull JTableFixture click(@Nonnull TableCell cell, @Nonnull MouseButton button) {
    click(cell, button, 1);
    return this;
  }

  /**
   * Simulates a user clicking a cell in this fixture's {@code JTable}, using the specified mouse button the given
   * number of times.
   *
   * @param cell the cell to click.
   * @param mouseClickInfo specifies the mouse button to use and how many times to click.
   * @return this fixture.
   * @throws NullPointerException if the given {@code MouseClickInfo} is {@code null}.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public @Nonnull JTableFixture click(@Nonnull TableCell cell, @Nonnull MouseClickInfo mouseClickInfo) {
    checkNotNull(mouseClickInfo);
    click(cell, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }

  void click(@Nonnull TableCell cell, @Nonnull MouseButton button, int times) {
    driver().click(target(), cell, button, times);
  }

  /**
   * Enters the given value in the given cell of this fixture's {@code JTable}, using this fixture's
   * {@link JTableCellWriter}. If you need more flexibility for editing cell, please see
   * {@link JTableCellFixture#editor()}.
   *
   * @param cell the given cell.
   * @param value the given value.
   * @return this fixture.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   * @throws IllegalStateException if this fixture's {@code JTable} is not editable.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws ActionFailedException if this fixture's {@code JTableCellValueReader} is unable to enter the given value.
   * @see #replaceCellWriter(JTableCellWriter)
   * @see JTableCellFixture#editor()
   */
  public @Nonnull JTableFixture enterValue(@Nonnull TableCell cell, @Nonnull String value) {
    driver().enterValueInCell(target(), cell, value);
    return this;
  }

  public void replaceCellReader(@Nonnull JTableCellReader cellReader) {
    driver().replaceCellReader(cellReader);
  }

  /**
   * Asserts that this fixture's {@code JTable} has the given number of rows.
   *
   * @param expected the expected number of rows.
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code JTable} does not have the given number of rows.
   */
  public @Nonnull JTableFixture requireRowCount(int expected) {
    driver().requireRowCount(target(), expected);
    return this;
  }

  /**
   * Asserts that the set of selected rows in this fixture's {@code JTable} contains to the given row indices. The given
   * row indices can be a subset of all the selected rows in a {@code JTable}.
   *
   * @param rows the indices of the rows expected to be selected.
   * @return this fixture.
   * @throws AssertionError if the set of selected rows in this fixture's {@code JTable} (if any) do not contain the
   *           given indices.
   * @since 1.2
   */
  public @Nonnull JTableFixture requireSelectedRows(@Nonnull int... rows) {
    driver().requireSelectedRows(target(), rows);
    return this;
  }

  /**
   * Asserts that this fixture's {@code JTable} has the given number of columns.
   *
   * @param expected the expected number of columns.
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code JTable} does not have the given number of columns.
   */
  public @Nonnull JTableFixture requireColumnCount(int expected) {
    driver().requireColumnCount(target(), expected);
    return this;
  }

  /**
   * Asserts that the given cell in this fixture's {@code JTable} is editable.
   *
   * @param cell the given cell.
   * @return this fixture.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws AssertionError if the given cell is not editable.
   */
  public @Nonnull JTableFixture requireEditable(@Nonnull TableCell cell) {
    driver().requireEditable(target(), cell);
    return this;
  }

  /**
   * Asserts that the given cell in this fixture's {@code JTable} is not editable.
   *
   * @param cell the given cell.
   * @return this fixture.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws AssertionError if the given cell is editable.
   */
  public @Nonnull JTableFixture requireNotEditable(@Nonnull TableCell cell) {
    driver().requireNotEditable(target(), cell);
    return this;
  }

  /**
   * Verifies that this fixture's {@code JTable} does not have any selection.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code JTable} has a selection.
   */
  public @Nonnull JTableFixture requireNoSelection() {
    driver().requireNoSelection(target());
    return this;
  }

  /**
   * Asserts that the value of the given cell matches the given value.
   *
   * @param cell the given table cell.
   * @param value the expected value. It can be a regular expression.
   * @return this fixture.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws AssertionError if the value of the given cell does not match the given value.
   */
  public @Nonnull JTableFixture requireCellValue(@Nonnull TableCell cell, @Nullable String value) {
    driver().requireCellValue(target(), cell, value);
    return this;
  }

  /**
   * Asserts that the value of the given cell matches the given regular expression pattern.
   *
   * @param cell the given table cell.
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws AssertionError if the value of the given cell does not match the given regular expression pattern.
   * @since 1.2
   */
  public @Nonnull JTableFixture requireCellValue(@Nonnull TableCell cell, @Nonnull Pattern pattern) {
    driver().requireCellValue(target(), cell, pattern);
    return this;
  }

  /**
   * Asserts that the {@code String} representation of the cell values in this fixture's {@code JTable} is equal to the
   * given {@code String} array. This method uses this fixture's {@link JTableCellReader} to read the values of the
   * table cells as {@code String}s.
   *
   * @param contents the expected {@code String} representation of the cell values in this fixture's {@code JTable}.
   * @return this fixture.
   * @see #replaceCellReader(JTableCellReader)
   */
  public @Nonnull JTableFixture requireContents(@Nonnull String[][] contents) {
    driver().requireContents(target(), contents);
    return this;
  }

  public void replaceCellWriter(JTableCellWriter cellWriter) {
    driver().replaceCellWriter(cellWriter);
  }

  /**
   * Returns the index of the column in this fixture's {@code JTable} whose name matches the given one.
   *
   * @param columnName the name of the column to look for.
   * @return the index of the column whose name matches the given one.
   * @throws ActionFailedException if a column with a matching name could not be found.
   */
  public int columnIndexFor(@Nonnull Object columnName) {
    return driver().columnIndex(target(), columnName);
  }

  /**
   * Shows a pop-up menu at the given cell.
   *
   * @param cell the table cell where to show the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public @Nonnull JPopupMenuFixture showPopupMenuAt(@Nonnull TableCell cell) {
    return new JPopupMenuFixture(robot(), driver().showPopupMenuAt(target(), cell));
  }
}
