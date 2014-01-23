/*
 * Created on Aug 10, 2008
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

import javax.annotation.Nonnull;
import javax.swing.JTable;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * <p>
 * Indicates whether a cell in a {@code JTable} is selected.
 * </p>
 * 
 * <p>
 * <b>Note:</b> Methods in this class are accessed in the current executing thread. Such thread may or may not be the
 * event dispatch thread (EDT.) Client code must call methods in this class from the EDT.
 * </p>
 * 
 * @author Alex Ruiz
 */
final class JTableSingleRowCellSelectedQuery {
  @RunsInCurrentThread
  static boolean isCellSelected(final @Nonnull JTable table, final int row, final int column) {
    return table.isRowSelected(row) && table.isColumnSelected(column) && table.getSelectedRowCount() == 1;
  }

  private JTableSingleRowCellSelectedQuery() {}
}
