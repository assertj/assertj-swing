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

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.driver.JTableCellPreconditions.checkCellIndicesInBounds;
import static org.assertj.swing.driver.JTableCellPreconditions.validateCellIsEditable;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;

/**
 * Stops editing of a cell in a {@code JTable}. This task is executed in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JTableStopCellEditingTask {
  @RunsInEDT
  static void stopEditing(final @Nonnull TableCellEditor cellEditor) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        doStopCellEditing(cellEditor);
      }
    });
  }

  @RunsInEDT
  static void stopEditing(final @Nonnull JTable table, final int row, final int column) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        doStopCellEditing(table, row, column);
      }
    });
  }

  @RunsInEDT
  static void checkStateAndStopEditing(final @Nonnull JTable table, final int row, final int column) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        checkCellIndicesInBounds(table, row, column);
        validateCellIsEditable(table, row, column);
        doStopCellEditing(table, row, column);
      }
    });
  }

  @RunsInCurrentThread
  private static void doStopCellEditing(@Nonnull JTable table, int row, int column) {
    TableCellEditor editor = checkNotNull(table.getCellEditor(row, column));
    doStopCellEditing(editor);
  }

  @RunsInCurrentThread
  private static void doStopCellEditing(@Nonnull TableCellEditor cellEditor) {
    checkNotNull(cellEditor);
    cellEditor.stopCellEditing();
  }

  private JTableStopCellEditingTask() {
  }
}