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
package org.assertj.swing.test.swing;

import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.SwingUtilities.invokeLater;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Adapted from the <a href="http://java.sun.com/docs/books/tutorial/uiswing/" target="_blank">Swing Tutorial</a>.
 */
public class TableRenderDemo extends JPanel {
  public final JTable table;

  public TableRenderDemo() {
    super(new GridLayout(1, 0));

    table = new JTable(new MyTableModel());
    table.setName("table");
    table.setPreferredScrollableViewportSize(new Dimension(500, 100));
    // JDK 6: table.setFillsViewportHeight(true);

    // Create the scroll pane and add the table to it.
    JScrollPane scrollPane = new JScrollPane(table);

    // Set up column sizes.
    initColumnSizes(table);

    // Fiddle with the Sport column's cell editors/renderers.
    setUpSportColumn(table.getColumnModel().getColumn(2));

    // Add the scroll pane to this panel.
    add(scrollPane);
  }

  /*
   * This method picks good column sizes. If all column heads are wider than the column's cells' contents, then you can
   * just use column.sizeWidthToFit().
   */
  private static void initColumnSizes(JTable table) {
    MyTableModel model = (MyTableModel) table.getModel();
    TableColumn column = null;
    Component comp = null;
    int headerWidth = 0;
    int cellWidth = 0;
    Object[] longValues = model.longValues;
    TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
    for (int i = 0; i < 5; i++) {
      column = table.getColumnModel().getColumn(i);
      comp = headerRenderer.getTableCellRendererComponent(null, column.getHeaderValue(), false, false, 0, 0);
      headerWidth = comp.getPreferredSize().width;
      comp = table.getDefaultRenderer(model.getColumnClass(i)).getTableCellRendererComponent(table, longValues[i],
          false, false, 0, i);
      cellWidth = comp.getPreferredSize().width;
      column.setPreferredWidth(Math.max(headerWidth, cellWidth));
    }
  }

  public void setUpSportColumn(TableColumn sportColumn) {
    // Set up the editor for the sport cells.
    JComboBox comboBox = new JComboBox();
    comboBox.addItem("Snowboarding");
    comboBox.addItem("Rowing");
    comboBox.addItem("Knitting");
    comboBox.addItem("Speed reading");
    comboBox.addItem("Pool");
    comboBox.addItem("None of the above");
    sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
    // Set up tool tips for the sport cells.
    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    renderer.setToolTipText("Click for combo box");
    sportColumn.setCellRenderer(renderer);
  }

  class MyTableModel extends AbstractTableModel {
    private final String[] columnNames = { "First Name", "Last Name", "Sport", "# of Years", "Vegetarian" };
    private final Object[][] data = { { "Mary", "Campione", "Snowboarding", 5, false },
        { "Alison", "Huml", "Rowing", 3, true }, { "Kathy", "Walrath", "Knitting", 2, false },
        { "Sharon", "Zakhour", "Speed reading", 20, true }, { "Philip", "Milne", "Pool", 10, false } };

    public final Object[] longValues = { "Sharon", "Campione", "None of the above", 20, true };

    @Override
    public int getColumnCount() {
      return columnNames.length;
    }

    @Override
    public int getRowCount() {
      return data.length;
    }

    @Override
    public String getColumnName(int col) {
      return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
      return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/ editor for each cell. If we didn't implement this
     * method, then the last column would contain text ("true"/"false"), rather than a check box.
     */
    @Override
    public Class<?> getColumnClass(int c) {
      return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
      // Note that the data/cell address is constant,
      // no matter where the cell appears onscreen.
      if (col < 2) {
        return false;
      }
      return true;
    }

    /*
     * Don't need to implement this method unless your table's data can change.
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
      data[row][col] = value;
      fireTableCellUpdated(row, col);
    }
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be invoked from the event-dispatching thread.
   */
  static void createAndShowGUI() {
    // Create and set up the window.
    TestWindow frame = new TestWindow(TableRenderDemo.class);
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Create and set up the content pane.
    TableRenderDemo newContentPane = new TableRenderDemo();
    newContentPane.setOpaque(true); // content panes must be opaque
    frame.setContentPane(newContentPane);

    // Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    invokeLater(new Runnable() {
      @Override
      public void run() {
        createAndShowGUI();
      }
    });
  }
}