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

import static org.fest.swing.driver.JTableCellPreconditions.checkCellIndicesInBounds;

import javax.annotation.Nonnull;
import javax.swing.JTable;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.data.TableCell;

/**
 * <p>
 * Indicates whether a cell in a {@code JTable} is editable.
 * </p>
 * 
 * <p>
 * <b>Note:</b> Methods in this class are accessed in the current executing thread. Such thread may or may not be the
 * event dispatch thread (EDT.) Client code must call methods in this class from the EDT.
 * </p>
 * 
 * @see JTable#isCellEditable(int, int)
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JTableCellEditableQuery {
  @RunsInCurrentThread
  static boolean isCellEditable(@Nonnull JTable table, @Nonnull TableCell cell) {
    checkCellIndicesInBounds(table, cell);
    return table.isCellEditable(cell.row, cell.column);
  }

  private JTableCellEditableQuery() {}
}