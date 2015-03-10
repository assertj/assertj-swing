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

import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;

/**
 * Returns the {@code JTableHeader} in a {@code JTable}. This query is executed in the event dispatch thread (EDT.)
 * 
 * @see JTable#getTableHeader()
 * 
 * @author Alex Ruiz
 */
final class JTableHeaderQuery {
  @RunsInEDT
  static @Nullable JTableHeader tableHeader(final @Nonnull JTable table) {
    return execute(new GuiQuery<JTableHeader>() {
      @Override
      protected @Nullable JTableHeader executeInEDT() {
        return table.getTableHeader();
      }
    });
  }

  private JTableHeaderQuery() {
  }
}