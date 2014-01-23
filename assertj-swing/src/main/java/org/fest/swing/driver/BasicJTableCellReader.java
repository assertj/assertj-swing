/*
 * Created on Apr 14, 2008
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

import static java.lang.String.valueOf;
import static org.fest.swing.driver.ModelValueToString.asText;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.cell.JTableCellReader;

/**
 * Default implementation of {@link JTableCellReader}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicJTableCellReader implements JTableCellReader {
  private final CellRendererReader rendererReader;
  private final BasicJComboBoxCellReader comboBoxCellReader = new BasicJComboBoxCellReader();

  /**
   * Creates a new {@link BasicJTableCellReader} that uses a {@link BasicCellRendererReader} to read the value from the
   * cell renderer component in a {@code JTable}.
   */
  public BasicJTableCellReader() {
    this(new BasicCellRendererReader());
  }

  /**
   * Creates a new {@link BasicJTableCellReader}.
   * 
   * @param reader knows how to read values from the cell renderer component in a {@code JTable}.
   * @throws NullPointerException if {@code reader} is {@code null}.
   */
  public BasicJTableCellReader(@Nonnull CellRendererReader reader) {
    this.rendererReader = checkNotNull(reader);
  }

  /**
   * <p>
   * Returns the internal value of a cell in a {@code JTable} as expected in a test. This method first tries to return
   * the value displayed in the {@code JTable}'s cell renderer.
   * <ul>
   * <li>if the renderer is a {@code JLabel}, this method returns its text</li>
   * <li>if the renderer is a {@code JComboBox}, this method returns the value of its selection as a {@code String}</li>
   * <li>if the renderer is a {@code JCheckBox}, this method returns whether it is selected or not</li>
   * </ul>
   * If it fails reading the cell renderer, this method will get the value from the {@code toString} implementation of
   * the object stored in the {@code JTable}'s model at the specified indices.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param table the given {@code JTable}.
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @return the internal value of a cell in a {@code JTable} as expected in a test.
   */
  @Override
  @RunsInCurrentThread
  public @Nullable String valueAt(@Nonnull JTable table, int row, int column) {
    Component c = cellRendererIn(table, row, column);
    String value = (c != null) ? rendererReader.valueFrom(c) : null;
    if (value != null) {
      return value;
    }
    if (c instanceof JLabel) {
      return ((JLabel) c).getText();
    }
    if (c instanceof JCheckBox) {
      return valueOf(((JCheckBox) c).isSelected());
    }
    if (c instanceof JComboBox) {
      return valueAsText((JComboBox) c);
    }
    return asText(table.getValueAt(row, column));
  }

  private @Nullable String valueAsText(@Nonnull JComboBox comboBox) {
    int selectedIndex = comboBox.getSelectedIndex();
    if (selectedIndex == -1) {
      return null;
    }
    return comboBoxCellReader.valueAt(comboBox, selectedIndex);
  }

  /**
   * <p>
   * Returns the font of the cell renderer for the given {@code JTable} cell.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param table the given {@code JTable}.
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @return the font of the cell renderer for the given {@code JTable} cell.
   */
  @Override
  @RunsInCurrentThread
  public @Nullable Font fontAt(@Nonnull JTable table, int row, int column) {
    Component c = cellRendererIn(table, row, column);
    return c != null ? c.getFont() : null;
  }

  /**
   * <p>
   * Returns the background color of the cell renderer for the given {@code JTable} cell.
   * </p>
   * 
   * @param table the given {@code JTable}.
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @return the background color of the cell renderer for the given {@code JTable} cell.
   */
  @Override
  @RunsInCurrentThread
  public @Nullable Color backgroundAt(@Nonnull JTable table, int row, int column) {
    Component c = cellRendererIn(table, row, column);
    return c != null ? c.getBackground() : null;
  }

  /**
   * <p>
   * Returns the foreground color of the cell renderer for the given {@code JTable} cell.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param table the given {@code JTable}.
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @return the foreground color of the cell renderer for the given {@code JTable} cell.
   */
  @Override
  @RunsInCurrentThread
  public @Nullable Color foregroundAt(@Nonnull JTable table, int row, int column) {
    Component c = cellRendererIn(table, row, column);
    return c != null ? c.getForeground() : null;
  }

  @RunsInCurrentThread
  private @Nullable Component cellRendererIn(final @Nonnull JTable table, final int row, final int column) {
    return table.prepareRenderer(table.getCellRenderer(row, column), row, column);
  }
}
