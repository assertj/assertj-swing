/*
 * Created on Nov 1, 2008
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

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Preconditions.checkNotNull;

import java.util.List;

import javax.annotation.Nonnull;
import javax.swing.JList;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JListCellReader;
import org.fest.swing.edt.GuiQuery;

/**
 * Returns an array of {@code String}s that represents the selection of a given {@code JList}. This query is executed in
 * the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 */
final class JListSelectionValuesQuery {
  @RunsInEDT
  static @Nonnull List<String> selectionValues(final @Nonnull JList list, final @Nonnull JListCellReader cellReader) {
    List<String> result = execute(new GuiQuery<List<String>>() {
      @Override
      protected List<String> executeInEDT() {
        List<String> values = newArrayList();
        int[] selectedIndices = list.getSelectedIndices();
        for (int index : selectedIndices) {
          values.add(cellReader.valueAt(list, index));
        }
        return values;
      }
    });
    return checkNotNull(result);
  }

  private JListSelectionValuesQuery() {}
}
