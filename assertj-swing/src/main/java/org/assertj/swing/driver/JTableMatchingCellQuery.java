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
import static org.assertj.swing.data.TableCell.row;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.exception.ActionFailedException.actionFailure;

import javax.annotation.Nonnull;
import javax.swing.JTable;

import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.cell.JTableCellReader;
import org.assertj.swing.data.TableCell;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.util.TextMatcher;

/**
 * Returns the first cell in a {@code JTable} whose value matches the given one. This query is executed in the event
 * dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 */
final class JTableMatchingCellQuery {
  @RunsInEDT
  static @Nonnull TableCell cellWithValue(final @Nonnull JTable table, final @Nonnull TextMatcher matcher,
      final @Nonnull JTableCellReader cellReader) {
    TableCell result = execute(new GuiQuery<TableCell>() {
      @Override
      protected TableCell executeInEDT() {
        return findMatchingCell(table, matcher, cellReader);
      }
    });
    return checkNotNull(result);
  }

  @RunsInCurrentThread
  private static @Nonnull TableCell findMatchingCell(@Nonnull JTable table, @Nonnull TextMatcher matcher,
      @Nonnull JTableCellReader cellReader) {
    int rCount = table.getRowCount();
    int cCount = table.getColumnCount();
    for (int r = 0; r < rCount; r++) {
      for (int c = 0; c < cCount; c++) {
        if (cellHasValue(table, r, c, matcher, cellReader)) {
          return row(r).column(c);
        }
      }
    }
    String msg = String.format("Unable to find cell matching %s %s", matcher.description(), matcher.formattedValues());
    throw actionFailure(msg);
  }

  @RunsInCurrentThread
  private static boolean cellHasValue(@Nonnull JTable table, int row, int column, @Nonnull TextMatcher matcher,
      @Nonnull JTableCellReader cellReader) {
    return matcher.isMatching(cellReader.valueAt(table, row, column));
  }

  private JTableMatchingCellQuery() {
  }
}
