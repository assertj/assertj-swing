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
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.swing.JTable;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.cell.JTableCellReader;
import org.assertj.swing.edt.GuiQuery;

/**
 * Returns the contents of a {@code JTable} as a two-dimensional {@code String} array. This query is executed in the
 * event dispatch thread (EDT).
 * 
 * @author Alex Ruiz
 */
final class JTableContentsQuery {
  @RunsInEDT
  static @Nonnull String[][] tableContents(final @Nonnull JTable table, final @Nonnull JTableCellReader cellReader) {
    String[][] result = execute(new GuiQuery<String[][]>() {
      @Override
      protected @Nonnull String[][] executeInEDT() {
        int rCount = table.getRowCount();
        int cCount = table.getColumnCount();
        String[][] contents = new String[rCount][cCount];
        for (int r = 0; r < rCount; r++) {
          for (int c = 0; c < cCount; c++) {
            contents[r][c] = cellReader.valueAt(table, r, c);
          }
        }
        return contents;
      }
    });
    return checkNotNull(result);
  }

  private JTableContentsQuery() {
  }
}
