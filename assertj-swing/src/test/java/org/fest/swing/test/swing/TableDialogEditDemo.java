/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met: - Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution. - Neither the name of Sun Microsystems nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.fest.swing.test.swing;

import static java.awt.Color.PINK;
import static java.awt.Color.RED;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.SwingUtilities.invokeLater;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * This is like TableDemo, except that it substitutes a Favorite Color column for the Last Name column and specifies a
 * custom cell renderer and editor for the color data.
 */
public class TableDialogEditDemo extends JPanel {
  public final JTable table;

  public TableDialogEditDemo() {
    super(new GridLayout(1, 0));

    table = new JTable(new MyTableModel());
    table.setPreferredScrollableViewportSize(new Dimension(500, 70));

    // Create the scroll pane and add the table to it.
    JScrollPane scrollPane = new JScrollPane(table);

    // Set up renderer and editor for the Favorite Color column.
    table.setDefaultRenderer(Color.class, new ColorRenderer(true));
    table.setDefaultEditor(Color.class, new ColorEditor());

    // Fiddle with the Sport column's cell editors/renderers.
    setUpSportColumn(table.getColumnModel().getColumn(2));

    // Add the scroll pane to this panel.
    add(scrollPane);
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

  private static class MyTableModel extends AbstractTableModel {
    private final String[] columnNames = { "First Name", "Favorite Color", "Sport", "# of Years", "Vegetarian" };
    private final Object[][] data = { { "Mary", new Color(153, 0, 153), "Snowboarding", 5, false },
        { "Alison", new Color(51, 51, 153), "Rowing", 3, true },
        { "Kathy", new Color(51, 102, 51), "Knitting", 2, false }, { "Sharon", RED, "Speed reading", 20, true },
        { "Philip", PINK, "Pool", 10, false } };

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
    public Class<?> getColumnClass(int columnIndex) {
      return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
      // Note that the data/cell address is constant, no matter where the cell appears on screen.
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

  /**
   * Create the GUI and show it. For thread safety, this method should be invoked from the event-dispatching thread.
   */
  static void createAndShowGUI() {
    // Create and set up the window.
    TestWindow frame = new TestWindow(TableDialogEditDemo.class);
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Create and set up the content pane.
    JComponent newContentPane = new TableDialogEditDemo();
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