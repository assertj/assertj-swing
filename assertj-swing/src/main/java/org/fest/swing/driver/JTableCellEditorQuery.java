/*
 * Created on Aug 6, 2008
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
import javax.annotation.Nullable;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * <p>
 * Returns the AWT or Swing {@code Component} of a {@code JTable} cell editor.
 * </p>
 *
 * <p>
 * <b>Note:</b> Methods in this class are accessed in the current executing thread. Such thread may or may not be the
 * event dispatch thread (EDT.) Client code must call methods in this class from the EDT.
 * </p>
 *
 * @see JTable#getCellEditor()
 * @see TableCellEditor#getTableCellEditorComponent(JTable, Object, boolean, int, int)
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JTableCellEditorQuery {
  @RunsInCurrentThread
  static @Nullable Component cellEditorIn(final @Nonnull JTable table, final int row, final int column) {
    TableCellEditor cellEditor = table.getCellEditor(row, column);
    return cellEditor.getTableCellEditorComponent(table, table.getValueAt(row, column), false, row, column);
  }

  private JTableCellEditorQuery() {}
}