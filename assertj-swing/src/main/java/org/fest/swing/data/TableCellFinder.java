/*
 * Created on Dec 23, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.data;

import javax.annotation.Nonnull;
import javax.swing.JTable;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.exception.ActionFailedException;

/**
 * Finds a cell in a {@code JTable}.
 * 
 * @author Alex Ruiz
 */
public interface TableCellFinder {
  /**
   * Finds a cell in the given {@code JTable} based on some search criteria specified by this finder. Implementations of
   * this method <strong>should</strong> access the given {@code JTable} in the event dispatch thread (EDT.)
   * 
   * @param table the target {@code JTable}.
   * @param cellReader knows how to read the contents of a cell in a {@code JTable}.
   * @return the cell found, if any.
   * @throws ActionFailedException if a matching cell could not be found.
   */
  @RunsInEDT
  @Nonnull TableCell findCell(@Nonnull JTable table, @Nonnull JTableCellReader cellReader);
}
