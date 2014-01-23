/*
 * Created on Jun 10, 2008
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

import static org.fest.swing.driver.JTableCellPreconditions.validateCellIsEditable;
import static org.fest.swing.driver.JTableCellPreconditions.checkCellIndicesInBounds;
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;

/**
 * Cancels editing of a cell in a {@code JTable}. This task is executed in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JTableCancelCellEditingTask {
  @RunsInEDT
  static void cancelEditing(final @Nonnull JTable table, final int row, final int column) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        checkCellIndicesInBounds(table, row, column);
        validateCellIsEditable(table, row, column);
        TableCellEditor cellEditor = table.getCellEditor(row, column);
        doCancelEditing(cellEditor);
      }
    });
  }

  @RunsInEDT
  static void cancelEditing(final @Nonnull TableCellEditor cellEditor) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        doCancelEditing(cellEditor);
      }
    });
  }

  private static void doCancelEditing(@Nullable TableCellEditor cellEditor) {
    if (cellEditor == null) {
      return;
    }
    cellEditor.cancelCellEditing();
  }

  private JTableCancelCellEditingTask() {}
}