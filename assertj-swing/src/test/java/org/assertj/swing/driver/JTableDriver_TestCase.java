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

import static java.lang.Integer.parseInt;
import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.driver.JTableClearSelectionTask.clearSelectionOf;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.task.ComponentSetEnabledTask.disable;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;

import javax.annotation.Nonnull;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.data.TableCell;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestTable;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;

/**
 * Base test case for {@link JTableDriver}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class JTableDriver_TestCase extends RobotBasedTestCase {
  JTableCellReaderStub cellReader;
  MyWindow window;
  TestTable table;
  JTableDriver driver;
  @Rule
  public ExpectedException thrown = none();

  @Override
  protected final void onSetUp() {
    driver = new JTableDriver(robot);
    cellReader = new JTableCellReaderStub();
    driver.replaceCellReader(cellReader);
    window = MyWindow.createNew(getClass());
    table = window.table;
    extraSetUp();
  }

  void extraSetUp() {
  }

  final void showWindow() {
    robot.showWindow(window);
  }

  static Object[][] tableCells() {
    return new Object[][] { { 6, 5 }, { 0, 0 }, { 8, 3 }, { 5, 2 } };
  }

  static Object[][] columnIds() {
    return new Object[][] { { "0" }, { "1" }, { "2" }, { "3" } };
  }

  static int columnIndexFrom(String columnId) {
    return parseInt(columnId);
  }

  final void assertThatCellReaderWasCalled() {
    cellReader.requireInvoked("valueAt");
  }

  @RunsInEDT
  final void disableTable() {
    disable(table);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void requireCellSelected(int row, int column) {
    assertThat(isCellSelected(row, column)).isTrue();
  }

  @RunsInEDT
  final void requireCellNotSelected(int row, int column) {
    assertThat(isCellSelected(row, column)).isFalse();
  }

  @RunsInEDT
  final boolean isCellSelected(int row, int column) {
    return isCellSelected(table, row, column);
  }

  @RunsInEDT
  private static boolean isCellSelected(final JTable table, final int row, final int column) {
    return execute(() -> table.isCellSelected(row, column));
  }

  @RunsInEDT
  final void enableMultipleSelection() {
    setMultipleIntervalSelectionTo(table);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setMultipleIntervalSelectionTo(final JTable table) {
    execute(() -> table.setSelectionMode(MULTIPLE_INTERVAL_SELECTION));
  }

  @RunsInEDT
  final void selectCell(int row, int column) {
    selectCell(table, row, column);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void selectCell(final JTable table, final int row, final int column) {
    execute(() -> table.changeSelection(row, column, false, false));
  }

  @RunsInEDT
  final void clearSelection() {
    clearSelectionOf(table);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void assertThatCellWasClicked(TableCell cell, Point pointClicked) {
    Point pointAtCell = new JTableLocation().pointAt(table, cell.row, cell.column);
    assertThat(pointClicked).isEqualTo(pointAtCell);
  }

  @RunsInEDT
  final void makeFirstCellEditable() {
    makeFirstCellEditable(true);
  }

  @RunsInEDT
  final void makeFirstCellNotEditable() {
    makeFirstCellEditable(false);
  }

  @RunsInEDT
  private void makeFirstCellEditable(boolean editable) {
    table.cellEditable(0, 0, editable);
    robot.waitForIdle();
  }

  static class MyWindow extends TestWindow {
    private static final Dimension TABLE_SIZE = new Dimension(400, 100);

    static final int COLUMN_COUNT = 6;
    static final int ROW_COUNT = 10;

    final TestTable table = new TestTable(ROW_COUNT, COLUMN_COUNT);
    final JTableHeader tableHeader;

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addTable(table);
      tableHeader = table.getTableHeader();
    }

    void addTable(JTable t) {
      add(decorate(t));
    }

    private static Component decorate(JTable table) {
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setPreferredSize(TABLE_SIZE);
      return scrollPane;
    }
  }

  static class JTableCellReaderStub extends BasicJTableCellReader {
    private final MethodInvocations methodInvocations = new MethodInvocations();

    JTableCellReaderStub() {
    }

    @Override
    public String valueAt(@Nonnull JTable table, int row, int column) {
      methodInvocations.invoked("valueAt");
      return super.valueAt(table, row, column);
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
