/*
 * Created on Aug 25, 2007
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
package org.fest.swing.test.swing;

import static java.lang.String.valueOf;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Strings.concat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;

/**
 * <p>
 * {@code JTable} that requires a name and supports drag and drop.
 * </p>
 *
 * <p>
 * Adapted from the tutorial <a href="http://java.sun.com/docs/books/tutorial/uiswing/dnd/intro.html"
 * target="_blank">Introduction to Drag and Drop and Data Transfer</a>.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class TestTable extends JTable {
  private final CustomModel model;

  public TestTable(int rowCount, int columnCount) {
    this(null, rowData(rowCount, columnCount), columnNames(columnCount));
  }

  public TestTable(String name, int rowCount, int columnCount) {
    this(name, rowData(rowCount, columnCount), columnNames(columnCount));
  }

  public static Object[] columnNames(int columnCount) {
    Object[] columnNames = new Object[columnCount];
    for (int i = 0; i < columnCount; i++) {
      columnNames[i] = String.valueOf(i);
    }
    return columnNames;
  }

  public static Object[][] rowData(int rowCount, int columnCount) {
    Object[][] data = new Object[rowCount][columnCount];
    for (int i = 0; i < rowCount; i++) {
      for (int j = 0; j < columnCount; j++) {
        data[i][j] = createCellValueFrom(i, j);
      }
    }
    return data;
  }

  public static String createCellValueFrom(int row, int column) {
    return concat(valueOf(row), "-", valueOf(column));
  }

  public TestTable(Object[][] rowData, Object[] columnNames) {
    this(null, rowData, columnNames);
  }

  public TestTable(String name, Object[][] data, Object[] columnNames) {
    setDragEnabled(true);
    model = new CustomModel(data, columnNames);
    setModel(model);
    setName(name);
    setSelectionMode(SINGLE_SELECTION);
    setTransferHandler(new TableTransferHandler());
  }

  @RunsInEDT
  public void cellEditable(final int row, final int column, final boolean editable) {
    cellEditable(model, row, column, editable);
  }

  @RunsInEDT
  private static void cellEditable(final CustomModel model, final int row, final int column, final boolean editable) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        model.cellEditable(row, column, editable);
      }
    });
  }

  private static class CustomModel extends DefaultTableModel {
    private final boolean[][] editableCells;

    CustomModel(Object[][] data, Object[] columnNames) {
      super(data, columnNames);
      editableCells = new boolean[data.length][data[0].length];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      return editableCells[row][column];
    }

    void cellEditable(int row, int column, boolean editable) {
      editableCells[row][column] = editable;
    }
  }

  private static class TableTransferHandler extends StringTransferHandler<JTable> {
    TableTransferHandler() {
      super(JTable.class);
    }

    @Override
    protected String exportString(JTable table) {
      rows = table.getSelectedRows();
      int colCount = table.getColumnCount();
      StringBuilder b = new StringBuilder();
      for (int i = 0; i < rows.length; i++) {
        for (int j = 0; j < colCount; j++) {
          Object val = table.getValueAt(rows[i], j);
          b.append(val == null ? "" : val.toString());
          if (j != colCount - 1) {
            b.append(",");
          }
        }
        if (i != rows.length - 1) {
          b.append("\n");
        }
      }
      return b.toString();
    }

    @Override
    protected void importString(JTable target, String s) {
      DefaultTableModel model = (DefaultTableModel) target.getModel();
      int index = target.getSelectedRow();
      // Prevent the user from dropping data back on itself.
      if (rows != null && index >= rows[0] - 1 && index <= rows[rows.length - 1]) {
        rows = null;
        return;
      }
      int max = model.getRowCount();
      if (index < 0) {
        index = max;
      } else {
        index++;
        if (index > max) {
          index = max;
        }
      }
      addIndex = index;
      String[] values = s.split("\n");
      addCount = values.length;
      int colCount = target.getColumnCount();
      for (int i = 0; i < values.length && i < colCount; i++) {
        model.insertRow(index++, values[i].split(","));
      }
    }

    @Override
    protected void cleanup(JTable source, boolean remove) {
      if (remove && rows != null) {
        DefaultTableModel model = (DefaultTableModel) source.getModel();
        // If we are moving items around in the same table, we need to adjust the rows accordingly, since those after
        // the
        // insertion point have moved.
        if (addCount > 0) {
          for (int i = 0; i < rows.length; i++) {
            if (rows[i] > addIndex) {
              rows[i] += addCount;
            }
          }
        }
        for (int i = rows.length - 1; i >= 0; i--) {
          model.removeRow(rows[i]);
        }
      }
    }
  }
}