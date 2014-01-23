/*
 * Created on Oct 13, 2008
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
package org.fest.swing.query;

import javax.annotation.Nonnull;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * An action that returns the index of a column in a {@code JTable} whose identifier matches the given one.
 * 
 * @see JTable#getColumn(Object)
 * @see TableColumn#getModelIndex()
 * 
 * @author Alex Ruiz
 */
public final class JTableColumnByIdentifierQuery {
  /**
   * <p>
   * Returns the index of a column in a {@code JTable} whose identifier matches the given one.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param table the given {@code JTable}.
   * @param identifier the column identifier.
   * @return the index of a column with a matching identifier. Otherwise it returns -1.
   */
  @RunsInCurrentThread
  public static int columnIndexByIdentifier(final @Nonnull JTable table, final @Nonnull Object identifier) {
    try {
      TableColumn column = table.getColumn(identifier);
      return table.convertColumnIndexToView(column.getModelIndex());
    } catch (IllegalArgumentException e) {
      return -1;
    }
  }

  private JTableColumnByIdentifierQuery() {}
}
