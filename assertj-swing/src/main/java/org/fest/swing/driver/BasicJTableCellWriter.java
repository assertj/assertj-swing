/*
 * Created on Jun 8, 2008
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

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;

import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;

/**
 * Default implementation of {@link JTableCellWriter}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicJTableCellWriter extends AbstractJTableCellWriter {
  private final JTableCheckBoxEditorCellWriter checkBoxWriter;
  private final JTableComboBoxEditorCellWriter comboBoxWriter;
  private final JTableTextComponentEditorCellWriter textComponentWriter;

  public BasicJTableCellWriter(@Nonnull Robot robot) {
    super(robot);
    checkBoxWriter = new JTableCheckBoxEditorCellWriter(robot);
    comboBoxWriter = new JTableComboBoxEditorCellWriter(robot);
    textComponentWriter = new JTableTextComponentEditorCellWriter(robot);
  }

  /**
   * Enters the given value at the given cell of the {@code JTable}. This method only supports the following GUI
   * components as cell editors:
   * <ul>
   * <li>{@code JCheckBox}: valid values for the property "selected" (a boolean) are "true" and "yes", other values are
   * considered {@code false}.</li>
   * <li>{@code JComboBox}: this writer will select the element which {@code String} representation matches the given
   * value.</li>
   * <li>{@code JTextComponent}: any value will be entered in the cell.</li>
   * </ul>
   * 
   * @param table the target {@code JTable}.
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @param value the value to enter.
   * @throws ActionFailedException if this writer is unable to handle the underlying cell editor.
   */
  @Override
  public void enterValue(@Nonnull JTable table, int row, int column, @Nonnull String value) {
    cellWriterFor(table, row, column).enterValue(table, row, column, value);
  }

  /**
   * Starts editing the given cell of the {@code JTable}. This method only supports the following Swing components as
   * cell editors:
   * <ul>
   * <li>{@code JCheckBox}</li>
   * <li>{@code JComboBox}</li>
   * <li>{@code JTextComponent}</li>
   * </ul>
   * 
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @throws ActionFailedException if this writer is unable to handle the underlying cell editor.
   * @see JTableCellWriter#startCellEditing(JTable, int, int)
   */
  @Override
  public void startCellEditing(@Nonnull JTable table, int row, int column) {
    cellWriterFor(table, row, column).startCellEditing(table, row, column);
  }

  /**
   * Stops editing the given cell of the {@code JTable}. This method only supports the following Swing components as
   * cell editors:
   * <ul>
   * <li>{@code JCheckBox}</li>
   * <li>{@code JComboBox}</li>
   * <li>{@code JTextComponent}</li>
   * </ul>
   * 
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @throws ActionFailedException if this writer is unable to handle the underlying cell editor.
   * @see JTableCellWriter#stopCellEditing(JTable, int, int)
   */
  @Override
  public void stopCellEditing(@Nonnull JTable table, int row, int column) {
    cellWriterFor(table, row, column).stopCellEditing(table, row, column);
  }

  /**
   * Cancels editing the given cell of the {@code JTable}. This method only supports the following Swing components as
   * cell editors:
   * <ul>
   * <li>{@code JCheckBox}</li>
   * <li>{@code JComboBox}</li>
   * <li>{@code JTextComponent}</li>
   * </ul>
   * 
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @throws ActionFailedException if this writer is unable to handle the underlying cell editor.
   * @see JTableCellWriter#cancelCellEditing(JTable, int, int)
   */
  @Override
  public void cancelCellEditing(@Nonnull JTable table, int row, int column) {
    cellWriterFor(table, row, column).cancelCellEditing(table, row, column);
  }

  private @Nonnull JTableCellWriter cellWriterFor(@Nonnull JTable table, int row, int column) {
    Component editor = editorForCell(table, row, column);
    if (editor instanceof JCheckBox) {
      return checkBoxWriter;
    }
    if (editor instanceof JComboBox) {
      return comboBoxWriter;
    }
    if (editor instanceof JTextComponent) {
      return textComponentWriter;
    }
    throw cannotFindOrActivateEditor(row, column);
  }
}
