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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;

import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;

/**
 * Base test case for tasks related to editing {@code JTable} cells.
 * 
 * @author Alex Ruiz
 */
public abstract class JTableCellEditingTask_TestCase extends RobotBasedTestCase {
  MyWindow window;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew(getClass());
  }

  @RunsInEDT
  final void editTableCellAt(final int row, final int col) {
    execute(() -> window.table.editCellAt(row, col));
    robot.waitForIdle();
    assertThat(isTableEditing()).isTrue();
  }

  @RunsInEDT
  final boolean isTableEditing() {
    return execute(() -> window.table.isEditing());
  }

  final void assertCellEditingStopped() {
    assertThat(isTableEditing()).isFalse();
    MyCellEditor cellEditor = window.table.cellEditor();
    assertThat(cellEditor.cellEditingCanceled()).isFalse();
    assertThat(cellEditor.cellEditingStopped()).isTrue();
  }

  static class MyWindow extends TestWindow {
    final MyTable table = new MyTable();

    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(new JScrollPane(table));
    }
  }

  static class MyTable extends JTable {
    private final MyCellEditor cellEditor = new MyCellEditor();

    MyTable() {
      super(new MyTableModel());
      setDefaultEditor(String.class, cellEditor);
      setPreferredScrollableViewportSize(new Dimension(200, 70));
    }

    MyCellEditor cellEditor() {
      return cellEditor;
    }
  }

  static class MyCellEditor extends DefaultCellEditor {
    private boolean cellEditingCanceled;
    private boolean cellEditingStopped;

    MyCellEditor() {
      super(new JTextField());
    }

    @Override
    public void cancelCellEditing() {
      cellEditingCanceled = true;
      super.cancelCellEditing();
    }

    @Override
    public boolean stopCellEditing() {
      cellEditingStopped = true;
      return super.stopCellEditing();
    }

    boolean cellEditingCanceled() {
      return cellEditingCanceled;
    }

    boolean cellEditingStopped() {
      return cellEditingStopped;
    }
  }

  static class MyTableModel extends AbstractTableModel {
    private final String[] columnNames = { "First Name", "Sport" };
    private final Object[][] data = { { "Mary", "Snowboarding" }, { "Alison", "Rowing" }, { "Kathy", "Knitting" },
        { "Sharon", "Speed reading" }, { "Philip", "Pool" } };

    @Override
    public int getColumnCount() {
      return columnNames.length;
    }

    @Override
    public int getRowCount() {
      return data.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public String getColumnName(int col) {
      return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
      return data[row][col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
      if (col < 1) {
        return false;
      }
      return true;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
      data[row][col] = value;
      fireTableCellUpdated(row, col);
    }
  }
}