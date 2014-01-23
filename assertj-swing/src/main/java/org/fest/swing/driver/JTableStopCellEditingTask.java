/*
 * Created on Nov 22, 2008
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

import static org.fest.swing.driver.JTableCellPreconditions.checkCellIndicesInBounds;
import static org.fest.swing.driver.JTableCellPreconditions.validateCellIsEditable;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;

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

  private JTableStopCellEditingTask() {}
}