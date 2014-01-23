/*
 * Created on Feb 2, 2008
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.fest.swing.driver.JTableCellEditableQuery.isCellEditable;
import static org.fest.swing.driver.JTableColumnCountQuery.columnCountOf;
import static org.fest.swing.driver.JTableContentsQuery.tableContents;
import static org.fest.swing.driver.JTableHasSelectionQuery.hasSelection;
import static org.fest.swing.driver.JTableHeaderQuery.tableHeader;
import static org.fest.swing.driver.JTableMatchingCellQuery.cellWithValue;
import static org.fest.swing.driver.JTableSingleRowCellSelectedQuery.isCellSelected;
import static org.fest.swing.driver.TextAssert.verifyThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.query.JTableColumnByIdentifierQuery.columnIndexByIdentifier;
import static org.fest.swing.util.ArrayPreconditions.checkNotNullOrEmpty;
import static org.fest.swing.util.Arrays.equal;
import static org.fest.util.Arrays.format;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;
import static org.fest.util.Strings.concat;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.util.regex.Pattern;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.data.TableCell;
import org.fest.swing.data.TableCellFinder;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.util.Arrays;
import org.fest.swing.util.Pair;
import org.fest.swing.util.PatternTextMatcher;
import org.fest.swing.util.StringTextMatcher;
import org.fest.util.InternalApi;
import org.fest.util.VisibleForTesting;

/**
 * <p>
 * Supports functional testing of {@code JTable}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.fest.swing.fixture} in your tests.
 * </p>
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@InternalApi
public class JTableDriver extends JComponentDriver {
  private static final String CONTENTS_PROPERTY = "contents";
  private static final String EDITABLE_PROPERTY = "editable";
  private static final String SELECTED_ROWS_PROPERTY = "selectedRows";
  private static final String SELECTION_PROPERTY = "selection";
  private static final String VALUE_PROPERTY = "value";

  private final JTableLocation location = new JTableLocation();
  private JTableCellReader cellReader;
  private JTableCellWriter cellWriter;

  /**
   * Creates a new {@link JTableDriver}.
   *
   * @param robot the robot to use to simulate user events.
   */
  public JTableDriver(@Nonnull Robot robot) {
    super(robot);
    replaceCellReader(new BasicJTableCellReader());
    replaceCellWriter(new BasicJTableCellWriter(robot));
  }

  /**
   * Returns the {@code JTableHeader} of the given {@code JTable}.
   *
   * @param table the given {@code JTable}.
   * @return the {@code JTableHeader} of the given {@code JTable}.
   */
  @RunsInEDT
  public @Nullable JTableHeader tableHeaderOf(@Nonnull JTable table) {
    return tableHeader(table);
  }

  /**
   * Returns the {@code String} representation of the value of the selected cell, using this driver's
   * {@link JTableCellReader}.
   *
   * @param table the target {@code JTable}.
   * @return the {@code String} representation of the value of the selected cell.
   * @see #replaceCellReader(JTableCellReader)
   */
  @RunsInEDT
  public @Nullable String selectionValue(@Nonnull JTable table) {
    return selectionValue(table, cellReader());
  }

  @RunsInEDT
  private static @Nullable String selectionValue(final @Nonnull JTable table,
      final @Nonnull JTableCellReader cellReader) {
    return execute(new GuiQuery<String>() {
      @Override
      protected String executeInEDT() {
        if (table.getSelectedRowCount() == 0) {
          return null;
        }
        return cellReader.valueAt(table, table.getSelectedRow(), table.getSelectedColumn());
      }
    });
  }

  /**
   * Returns a cell from the given {@code JTable} using the given cell finder.
   *
   * @param table the target {@code JTable}.
   * @param cellFinder knows how to find a cell.
   * @return the found cell, if any.
   * @throws NullPointerException if {@code cellFinder} is {@code null}.
   * @throws IndexOutOfBoundsException if the row or column indices in the found cell are out of bounds.
   * @throws ActionFailedException if a matching cell could not be found.
   */
  @RunsInEDT
  public @Nonnull TableCell cell(@Nonnull JTable table, @Nonnull TableCellFinder cellFinder) {
    checkNotNull(cellFinder);
    TableCell cell = cellFinder.findCell(table, cellReader());
    checkCellIndicesInBounds(table, cell);
    return cell;
  }

  /**
   * Returns a cell from the given {@code JTable} whose value matches the given one.
   *
   * @param table the target {@code JTable}.
   * @param value the value of the cell to look for. It can be a regular expression.
   * @return a cell from the given {@code JTable} whose value matches the given one.
   * @throws ActionFailedException if a cell with a matching value cannot be found.
   */
  @RunsInEDT
  public @Nonnull TableCell cell(@Nonnull JTable table, @Nullable String value) {
    return cellWithValue(table, new StringTextMatcher(value), cellReader());
  }

  /**
   * Returns a cell from the given {@code JTable} whose value matches the given regular expression pattern.
   *
   * @param table the target {@code JTable}.
   * @param pattern the regular expression pattern to match
   * @return a cell from the given {@code JTable} whose value matches the given one.
   * @throws NullPointerException if the given regular expression is {@code null}.
   * @throws ActionFailedException if a cell with a matching value cannot be found.
   * @since 1.2
   */
  @RunsInEDT
  public @Nonnull TableCell cell(@Nonnull JTable table, @Nonnull Pattern pattern) {
    return cellWithValue(table, new PatternTextMatcher(pattern), cellReader());
  }

  /**
   * Returns the {@code String} representation of the value at the given cell, using this driver's
   * {@link JTableCellReader}.
   *
   * @param table the target {@code JTable}.
   * @param cell the table cell.
   * @return the {@code String} representation of the value at the given cell.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @see #replaceCellReader(JTableCellReader)
   */
  @RunsInEDT
  public @Nullable String value(@Nonnull JTable table, @Nonnull TableCell cell) {
    checkNotNull(cell);
    return cellValue(table, cell, cellReader());
  }

  @RunsInEDT
  private static @Nullable String cellValue(final @Nonnull JTable table, final @Nonnull TableCell cell,
      final @Nonnull JTableCellReader cellReader) {
    return execute(new GuiQuery<String>() {
      @Override
      protected String executeInEDT() {
        JTableCellPreconditions.checkCellIndicesInBounds(table, cell);
        return cellReader.valueAt(table, cell.row, cell.column);
      }
    });
  }

  /**
   * Returns the {@code String} representation of the value at the given row and column, using this driver's
   * {@link JTableCellReader}.
   *
   * @param table the target {@code JTable}.
   * @param row the given row.
   * @param column the given column.
   * @return the {@code String} representation of the value at the given row and column.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @see #replaceCellReader(JTableCellReader)
   */
  @RunsInEDT
  public @Nullable String value(@Nonnull JTable table, int row, int column) {
    return cellValue(table, row, column, cellReader());
  }

  @RunsInEDT
  private static @Nullable String cellValue(final @Nonnull JTable table, final int row, final int column,
      final @Nonnull JTableCellReader cellReader) {
    return execute(new GuiQuery<String>() {
      @Override
      protected String executeInEDT() {
        JTableCellPreconditions.checkCellIndicesInBounds(table, row, column);
        return cellReader.valueAt(table, row, column);
      }
    });
  }

  /**
   * Returns the font of the given table cell.
   *
   * @param table the target {@code JTable}.
   * @param cell the table cell.
   * @return the font of the given table cell.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  @RunsInEDT
  public @Nullable Font font(@Nonnull JTable table, @Nonnull TableCell cell) {
    checkNotNull(cell);
    return cellFont(table, cell, cellReader());
  }

  @RunsInEDT
  private static @Nullable Font cellFont(final @Nonnull JTable table, final @Nonnull TableCell cell,
      final @Nonnull JTableCellReader cellReader) {
    return execute(new GuiQuery<Font>() {
      @Override
      protected Font executeInEDT() {
        JTableCellPreconditions.checkCellIndicesInBounds(table, cell);
        return cellReader.fontAt(table, cell.row, cell.column);
      }
    });
  }

  /**
   * Returns the background color of the given table cell.
   *
   * @param table the target {@code JTable}.
   * @param cell the table cell.
   * @return the background color of the given table cell.
   * @throws ActionFailedException if the cell is {@code null}.
   * @throws ActionFailedException if any of the indices (row and column) is out of bounds.
   */
  @RunsInEDT
  public Color background(@Nonnull JTable table, @Nonnull TableCell cell) {
    checkNotNull(cell);
    return cellBackground(table, cell, cellReader());
  }

  @RunsInEDT
  private static @Nullable Color cellBackground(final @Nonnull JTable table, final @Nonnull TableCell cell,
      final @Nonnull JTableCellReader cellReader) {
    return execute(new GuiQuery<Color>() {
      @Override
      protected Color executeInEDT() {
        JTableCellPreconditions.checkCellIndicesInBounds(table, cell);
        return cellReader.backgroundAt(table, cell.row, cell.column);
      }
    });
  }

  /**
   * Returns the foreground color of the given table cell.
   *
   * @param table the target {@code JTable}.
   * @param cell the table cell.
   * @return the foreground color of the given table cell.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  @RunsInEDT
  public @Nullable Color foreground(@Nonnull JTable table, @Nonnull TableCell cell) {
    checkNotNull(cell);
    return cellForeground(table, cell, cellReader());
  }

  @RunsInEDT
  private static @Nullable Color cellForeground(final @Nonnull JTable table, final @Nonnull TableCell cell,
      final @Nonnull JTableCellReader cellReader) {
    return execute(new GuiQuery<Color>() {
      @Override
      protected Color executeInEDT() {
        JTableCellPreconditions.checkCellIndicesInBounds(table, cell);
        return cellReader.foregroundAt(table, cell.row, cell.column);
      }
    });
  }

  /**
   * Selects the given cells of the {@code JTable}.
   *
   * @param table the target {@code JTable}.
   * @param cells the cells to select.
   * @throws NullPointerException if {@code cells} is {@code null} or empty.
   * @throws IllegalArgumentException if {@code cells} is {@code null} or empty.
   * @throws IllegalStateException if the {@code JTable} is disabled.
   * @throws IllegalStateException if the {@code JTable} is not showing on the screen.
   * @throws NullPointerException if any element in {@code cells} is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices of any of the {@code cells} are out of bounds.
   */
  public void selectCells(final @Nonnull JTable table, final @Nonnull TableCell[] cells) {
    checkNotNullOrEmpty(cells);
    new MultipleSelectionTemplate(robot) {
      @Override
      int elementCount() {
        return cells.length;
      }

      @Override
      void selectElement(int index) {
        selectCell(table, checkNotNull(cells[index]));
      }
    }.multiSelect();
  }

  /**
   * Verifies that the {@code JTable} does not have any selection.
   *
   * @param table the target {@code JTable}.
   * @throws AssertionError is the {@code JTable} has a selection.
   */
  @RunsInEDT
  public void requireNoSelection(@Nonnull JTable table) {
    assertNoSelection(table);
  }

  @RunsInEDT
  private static void assertNoSelection(final @Nonnull JTable table) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        if (!hasSelection(table)) {
          return;
        }
        String format = "[%s] expected no selection but was:<rows=%s, columns=%s>";
        String msg = String.format(format, propertyName(table, SELECTION_PROPERTY).value(),
            format(selectedRowsOf(table)), format(table.getSelectedColumns()));
        fail(msg);
      }
    });
  }

  /**
   * Selects the given cell, if it is not selected already.
   *
   * @param table the target {@code JTable}.
   * @param cell the cell to select.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if the {@code JTable} is disabled.
   * @throws IllegalStateException if the {@code JTable} is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  @RunsInEDT
  public void selectCell(@Nonnull JTable table, @Nonnull TableCell cell) {
    checkNotNull(cell);
    selectCell(table, cell.row, cell.column);
  }

  /**
   * Clicks the given cell, using the specified mouse button, the given number of times.
   *
   * @param table the target {@code JTable}.
   * @param cell the table cell.
   * @param mouseButton the mouse button to use.
   * @param times the number of times to click the cell.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if the {@code JTable} is disabled.
   * @throws IllegalStateException if the {@code JTable} is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  @RunsInEDT
  public void click(@Nonnull JTable table, @Nonnull TableCell cell, @Nonnull MouseButton mouseButton,
      @Nonnegative int times) {
    if (times <= 0) {
      throw new IllegalArgumentException("The number of times to click a cell should be greater than zero");
    }
    Point pointAtCell = scrollToPointAtCell(table, cell, location());
    robot.click(table, pointAtCell, mouseButton, times);
  }

  /**
   * Starts a drag operation at the location of the given table cell.
   *
   * @param table the target {@code JTable}.
   * @param cell the table cell.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if the {@code JTable} is disabled.
   * @throws IllegalStateException if the {@code JTable} is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  @RunsInEDT
  public void drag(@Nonnull JTable table, @Nonnull TableCell cell) {
    Point pointAtCell = scrollToPointAtCell(table, cell, location());
    drag(table, pointAtCell);
  }

  /**
   * Starts a drop operation at the location of the given table cell.
   *
   * @param table the target {@code JTable}.
   * @param cell the table cell.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if the {@code JTable} is disabled.
   * @throws IllegalStateException if the {@code JTable} is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  @RunsInEDT
  public void drop(@Nonnull JTable table, @Nonnull TableCell cell) {
    Point pointAtCell = scrollToPointAtCell(table, cell, location());
    drop(table, pointAtCell);
  }

  /**
   * Shows a pop-up menu at the given table cell.
   *
   * @param table the target {@code JTable}.
   * @param cell the table cell.
   * @return the displayed pop-up menu.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if the {@code JTable} is disabled.
   * @throws IllegalStateException if the {@code JTable} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  @RunsInEDT
  public @Nonnull JPopupMenu showPopupMenuAt(@Nonnull JTable table, @Nonnull TableCell cell) {
    Point pointAtCell = scrollToPointAtCell(table, cell, location());
    return robot.showPopupMenu(table, pointAtCell);
  }

  @RunsInEDT
  private static @Nonnull Point scrollToPointAtCell(final @Nonnull JTable table, final @Nonnull TableCell cell,
      final @Nonnull JTableLocation location) {
    checkNotNull(cell);
    Point result = execute(new GuiQuery<Point>() {
      @Override
      protected Point executeInEDT() {
        scrollToCell(table, cell, location);
        return location.pointAt(table, cell.row, cell.column);
      }
    });
    return checkNotNull(result);
  }

  @RunsInCurrentThread
  private static void scrollToCell(@Nonnull JTable table, @Nonnull TableCell cell, @Nonnull JTableLocation location) {
    checkEnabledAndShowing(table);
    JTableCellPreconditions.checkCellIndicesInBounds(table, cell);
    table.scrollRectToVisible(location.cellBounds(table, cell));
  }

  /**
   * Converts the given table cell into a coordinate pair.
   *
   * @param table the target {@code JTable}.
   * @param cell the table cell.
   * @return the coordinates of the given row and column.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  @RunsInEDT
  public @Nonnull Point pointAt(@Nonnull JTable table, @Nonnull TableCell cell) {
    return pointAtCell(table, cell, location());
  }

  @RunsInEDT
  private static @Nonnull Point pointAtCell(final @Nonnull JTable table, final @Nonnull TableCell cell,
      final @Nonnull JTableLocation location) {
    Point result = execute(new GuiQuery<Point>() {
      @Override
      protected Point executeInEDT() {
        JTableCellPreconditions.checkCellIndicesInBounds(table, cell);
        return location.pointAt(table, cell.row, cell.column);
      }
    });
    return checkNotNull(result);
  }

  /**
   * Asserts that the {@code String} representation of the cell values in the {@code JTable} is equal to the given
   * {@code String} array. This method uses this driver's {@link JTableCellReader} to read the values of the table cells
   * as {@code String}s.
   *
   * @param table the target {@code JTable}.
   * @param contents the expected {@code String} representation of the cell values in the {@code JTable}.
   * @see #replaceCellReader(JTableCellReader)
   */
  @RunsInEDT
  public void requireContents(@Nonnull JTable table, @Nonnull String[][] contents) {
    String[][] actual = contents(table);
    if (!equal(actual, contents)) {
      failNotEqual(actual, contents, propertyName(table, CONTENTS_PROPERTY));
    }
  }

  private static void failNotEqual(@Nonnull String[][] actual, @Nonnull String[][] expected,
      @Nullable Description description) {
    String descriptionValue = description != null ? description.value() : null;
    String message = descriptionValue == null ? "" : String.format("[%s] ", descriptionValue);
    fail(message + String.format("expected:<%s> but was<%s>", Arrays.format(expected), Arrays.format(actual)));
  }

  /**
   * Returns the {@code String} representation of the cells in the {@code JTable}, using this driver's
   * {@link JTableCellReader}.
   *
   * @param table the target {@code JTable}.
   * @return the {@code String} representation of the cells in the {@code JTable}.
   * @see #replaceCellReader(JTableCellReader)
   */
  @RunsInEDT
  public @Nonnull String[][] contents(@Nonnull JTable table) {
    return tableContents(table, cellReader());
  }

  /**
   * Asserts that the value of the given cell matches the given value.
   *
   * @param table the target {@code JTable}.
   * @param cell the given table cell.
   * @param value the expected value. It can be a regular expression.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws AssertionError if the value of the given cell does not match the given value.
   */
  @RunsInEDT
  public void requireCellValue(@Nonnull JTable table, @Nonnull TableCell cell, @Nullable String value) {
    verifyThat(value(table, cell)).as(cellValueProperty(table, cell)).isEqualOrMatches(value);
  }

  /**
   * Asserts that the value of the given cell matches the given regular expression pattern.
   *
   * @param table the target {@code JTable}.
   * @param cell the given table cell.
   * @param pattern the regular expression pattern to match.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the value of the given cell does not match the given regular expression pattern.
   * @since 1.2
   */
  @RunsInEDT
  public void requireCellValue(@Nonnull JTable table, @Nonnull TableCell cell, @Nonnull Pattern pattern) {
    verifyThat(value(table, cell)).as(cellValueProperty(table, cell)).matches(pattern);
  }

  @RunsInEDT
  private @Nonnull Description cellValueProperty(@Nonnull JTable table, @Nonnull TableCell cell) {
    return cellProperty(table, concat(VALUE_PROPERTY, " ", cell));
  }

  /**
   * Enters the given value in the given cell of the {@code JTable}, using this driver's {@link JTableCellWriter}.
   *
   * @param table the target {@code JTable}.
   * @param cell the given cell.
   * @param value the given value.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if the {@code JTable} is disabled.
   * @throws IllegalStateException if the {@code JTable} is not showing on the screen.
   * @throws IllegalStateException if the {@code JTable} cell is not editable.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws ActionFailedException if this driver's {@code JTableCellValueReader} is unable to enter the given value.
   * @see #replaceCellWriter(JTableCellWriter)
   */
  @RunsInEDT
  public void enterValueInCell(@Nonnull JTable table, @Nonnull TableCell cell, @Nonnull String value) {
    checkNotNull(cell);
    cellWriter.enterValue(table, cell.row, cell.column, value);
  }

  /**
   * Asserts that the given table cell is editable.
   *
   * @param table the target {@code JTable}.
   * @param cell the given table cell.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws AssertionError if the given table cell is not editable.
   */
  @RunsInEDT
  public void requireEditable(@Nonnull JTable table, @Nonnull TableCell cell) {
    requireEditableEqualTo(table, cell, true);
  }

  /**
   * Asserts that the given table cell is not editable.
   *
   * @param table the target {@code JTable}.
   * @param cell the given table cell.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws AssertionError if the given table cell is editable.
   */
  @RunsInEDT
  public void requireNotEditable(@Nonnull JTable table, @Nonnull TableCell cell) {
    requireEditableEqualTo(table, cell, false);
  }

  @RunsInEDT
  private static void requireEditableEqualTo(final @Nonnull JTable table, final @Nonnull TableCell cell,
      boolean editable) {
    checkNotNull(cell);
    boolean cellEditable = checkNotNull(execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return isCellEditable(table, cell);
      }
    }));
    assertThat(cellEditable).as(cellProperty(table, concat(EDITABLE_PROPERTY, " ", cell))).isEqualTo(editable);
  }

  @RunsInEDT
  private static @Nonnull Description cellProperty(@Nonnull JTable table, @Nonnull String propertyName) {
    return propertyName(table, propertyName);
  }

  /**
   * Returns the editor in the given cell of the {@code JTable}, using this driver's {@link JTableCellWriter}.
   *
   * @param table the target {@code JTable}.
   * @param cell the given cell.
   * @return the editor in the given cell of the {@code JTable}.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if the {@code JTable} cell is not editable.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @see #replaceCellWriter(JTableCellWriter)
   */
  @RunsInEDT
  public Component cellEditor(@Nonnull JTable table, @Nonnull TableCell cell) {
    checkNotNull(cell);
    return cellWriter.editorForCell(table, cell.row, cell.column);
  }

  /**
   * Starts editing the given cell of the {@code JTable}, using this driver's {@link JTableCellWriter}. This method
   * should be called before manipulating the {@code Component} returned by {@link #cellEditor(JTable, TableCell)}.
   *
   * @param table the target {@code JTable}.
   * @param cell the given cell.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if the {@code JTable} is disabled.
   * @throws IllegalStateException if the {@code JTable} is not showing on the screen.
   * @throws IllegalStateException if the {@code JTable} cell is not editable.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws ActionFailedException if this writer is unable to handle the underlying cell editor.
   * @see #replaceCellWriter(JTableCellWriter)
   */
  @RunsInEDT
  public void startCellEditing(@Nonnull JTable table, @Nonnull TableCell cell) {
    checkNotNull(cell);
    cellWriter.startCellEditing(table, cell.row, cell.column);
  }

  /**
   * Stops editing the given cell of the {@code JTable}, using this driver's {@link JTableCellWriter}. This method
   * should be called after manipulating the {@code Component} returned by {@link #cellEditor(JTable, TableCell)}.
   *
   * @param table the target {@code JTable}.
   * @param cell the given cell.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if the {@code JTable} is disabled.
   * @throws IllegalStateException if the {@code JTable} is not showing on the screen.
   * @throws IllegalStateException if the {@code JTable} cell is not editable.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws ActionFailedException if this writer is unable to handle the underlying cell editor.
   * @see #replaceCellWriter(JTableCellWriter)
   */
  @RunsInEDT
  public void stopCellEditing(@Nonnull JTable table, @Nonnull TableCell cell) {
    checkNotNull(cell);
    cellWriter.stopCellEditing(table, cell.row, cell.column);
  }

  /**
   * Cancels editing the given cell of the {@code JTable}, using this driver's {@link JTableCellWriter}. This method
   * should be called after manipulating the {@code Component} returned by {@link #cellEditor(JTable, TableCell)}.
   *
   * @param table the target {@code JTable}.
   * @param cell the given cell.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IllegalStateException if the {@code JTable} is disabled.
   * @throws IllegalStateException if the {@code JTable} is not showing on the screen.
   * @throws IllegalStateException if the {@code JTable} cell is not editable.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws ActionFailedException if this writer is unable to handle the underlying cell editor.
   * @see #replaceCellWriter(JTableCellWriter)
   */
  @RunsInEDT
  public void cancelCellEditing(@Nonnull JTable table, @Nonnull TableCell cell) {
    checkNotNull(cell);
    cellWriter.cancelCellEditing(table, cell.row, cell.column);
  }

  /**
   * Validates that the given table cell is non {@code null} and its indices are not out of bounds.
   *
   * @param table the target {@code JTable}.
   * @param cell to validate.
   * @throws NullPointerException if the cell is {@code null}.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  @RunsInEDT
  public void checkCellIndicesInBounds(final @Nonnull JTable table, final @Nonnull TableCell cell) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        JTableCellPreconditions.checkCellIndicesInBounds(table, cell);
      }
    });
  }

  /**
   * Updates the implementation of {@link JTableCellReader} to use when comparing internal values of a {@code JTable}
   * and the values expected in a test.
   *
   * @param newCellReader the new {@code JTableCellValueReader} to use.
   * @throws NullPointerException if {@code newCellReader} is {@code null}.
   */
  public void replaceCellReader(@Nonnull JTableCellReader newCellReader) {
    cellReader = checkNotNull(newCellReader);
  }

  /**
   * Updates the implementation of {@link JTableCellWriter} to use to edit cell values in a {@code JTable}.
   *
   * @param newCellWriter the new {@code JTableCellWriter} to use.
   * @throws NullPointerException if {@code newCellWriter} is {@code null}.
   */
  public void replaceCellWriter(JTableCellWriter newCellWriter) {
    cellWriter = checkNotNull(newCellWriter);
  }

  /**
   * Returns the number of rows that can be shown in the given {@code JTable}, given unlimited space.
   *
   * @param table the target {@code JTable}.
   * @return the number of rows shown in the given {@code JTable}.
   * @see JTable#getRowCount()
   */
  @RunsInEDT
  public int rowCountOf(@Nonnull JTable table) {
    return JTableRowCountQuery.rowCountOf(table);
  }

  /**
   * Returns the index of the column in the given {@code JTable} whose id matches the given one.
   *
   * @param table the target {@code JTable}.
   * @param columnId the id of the column to look for.
   * @return the index of the column whose id matches the given one.
   * @throws ActionFailedException if a column with a matching id could not be found.
   */
  @RunsInEDT
  public int columnIndex(@Nonnull JTable table, @Nonnull Object columnId) {
    return findColumnIndex(table, columnId);
  }

  @RunsInEDT
  private static int findColumnIndex(final @Nonnull JTable table, final @Nonnull Object columnId) {
    Integer result = execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        int index = columnIndexByIdentifier(table, columnId);
        if (index < 0) {
          failColumnIndexNotFound(columnId);
        }
        return index;
      }
    });
    return checkNotNull(result);
  }

  private static @Nonnull ActionFailedException failColumnIndexNotFound(@Nonnull Object columnId) {
    throw actionFailure(String.format("Unable to find a column with id '%s'", columnId.toString()));
  }

  /**
   * Asserts that the given {@code JTable} has the given number of rows.
   *
   * @param table the target {@code JTable}.
   * @param rowCount the expected number of rows.
   * @throws AssertionError if the given {@code JTable} does not have the given number of rows.
   */
  @RunsInEDT
  public void requireRowCount(@Nonnull JTable table, int rowCount) {
    assertThat(rowCountOf(table)).as(propertyName(table, "rowCount")).isEqualTo(rowCount);
  }

  /**
   * Asserts that the given {@code JTable} has the given number of columns.
   *
   * @param table the target {@code JTable}.
   * @param columnCount the expected number of columns.
   * @throws AssertionError if the given {@code JTable} does not have the given number of columns.
   */
  @RunsInEDT
  public void requireColumnCount(@Nonnull JTable table, int columnCount) {
    assertThat(columnCountOf(table)).as(propertyName(table, "columnCount")).isEqualTo(columnCount);
  }

  /**
   * Simulates a user selecting the given rows in the given {@code JTable}.
   *
   * @param table the target {@code JTable}.
   * @param rows the indices of the row to select.
   * @throws NullPointerException if the given array of indices is {@code null}.
   * @throws IllegalArgumentException if the given array of indices is empty.
   * @throws IllegalStateException if the {@code JTable} is disabled.
   * @throws IllegalStateException if the {@code JTable} is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the given indices is negative, or equal to or greater than the number
   *           of rows in the {@code JTable}.
   * @since 1.2
   */
  @RunsInEDT
  public void selectRows(final @Nonnull JTable table, final @Nonnull int... rows) {
    checkNotNullOrEmpty(rows);
    new MultipleSelectionTemplate(robot) {
      @Override
      int elementCount() {
        return rows.length;
      }

      @Override
      void selectElement(int index) {
        selectCell(table, rows[index], 0);
      }
    }.multiSelect();
  }

  @RunsInEDT
  private void selectCell(@Nonnull JTable table, int row, int column) {
    Pair<Boolean, Point> cellSelectionInfo = cellSelectionInfo(table, row, column, location);
    if (cellSelectionInfo.first) {
      return; // cell already selected
    }
    robot.click(table, checkNotNull(cellSelectionInfo.second), LEFT_BUTTON, 1);
  }

  @RunsInEDT
  private static @Nonnull Pair<Boolean, Point> cellSelectionInfo(final @Nonnull JTable table, final int row, final int column,
      final @Nonnull JTableLocation location) {
    Pair<Boolean, Point> result = execute(new GuiQuery<Pair<Boolean, Point>>() {
      @Override
      protected Pair<Boolean, Point> executeInEDT() {
        if (isCellSelected(table, row, column)) {
          return Pair.of(true, null);
        }
        scrollToCell(table, row, column, location);
        Point pointAtCell = location.pointAt(table, row, column);
        return Pair.of(false, pointAtCell);
      }
    });
    return checkNotNull(result);
  }

  @RunsInCurrentThread
  private static void scrollToCell(final @Nonnull JTable table, final int row, final int column,
      final @Nonnull JTableLocation location) {
    checkEnabledAndShowing(table);
    JTableCellPreconditions.checkCellIndicesInBounds(table, row, column);
    table.scrollRectToVisible(location.cellBounds(table, row, column));
  }

  /**
   * Asserts that the set of selected rows in the given {@code JTable} contains to the given row indices.
   *
   * @param table the target {@code JTable}.
   * @param rows the indices of the rows expected to be selected.
   * @throws AssertionError if the sets of selected rows in the given {@code JTable} (if any) do not contain the given
   *           row indices.
   * @since 1.2
   */
  @RunsInEDT
  public void requireSelectedRows(@Nonnull JTable table, @Nonnull int... rows) {
    int[] selectedRows = selectedRowsOf(table);
    assertThat(selectedRows).as(propertyName(table, SELECTED_ROWS_PROPERTY)).contains(rows);
  }

  @RunsInEDT
  private static @Nonnull int[] selectedRowsOf(final @Nonnull JTable table) {
    int[] result = execute(new GuiQuery<int[]>() {
      @Override
      protected int[] executeInEDT() {
        return table.getSelectedRows();
      }
    });
    return checkNotNull(result);
  }

  @VisibleForTesting
  @Nonnull JTableCellReader cellReader() {
    return cellReader;
  }

  private @Nonnull JTableLocation location() {
    return location;
  }
}
