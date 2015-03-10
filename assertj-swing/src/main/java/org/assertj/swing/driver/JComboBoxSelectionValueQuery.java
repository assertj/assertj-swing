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
import javax.annotation.Nullable;
import javax.swing.JComboBox;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.cell.JComboBoxCellReader;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.util.Pair;

/**
 * Returns the selected value of a {@code JComboBox} as plain text. This query is executed in the event dispatch thread
 * (EDT.)
 * 
 * @author Alex Ruiz
 */
final class JComboBoxSelectionValueQuery {
  private static final Pair<Boolean, String> NO_SELECTION = Pair.of(false, null);

  @RunsInEDT
  static @Nonnull Pair<Boolean, String> selection(final @Nonnull JComboBox comboBox,
      final @Nonnull JComboBoxCellReader cellReader) {
    Pair<Boolean, String> result = execute(new GuiQuery<Pair<Boolean, String>>() {
      @Override
      protected @Nullable Pair<Boolean, String> executeInEDT() {
        int selectedIndex = comboBox.getSelectedIndex();
        if (selectedIndex == -1) {
          return valueForNoSelection(comboBox);
        }
        return selection(cellReader.valueAt(comboBox, selectedIndex));
      }
    });
    return checkNotNull(result);
  }

  private static @Nonnull Pair<Boolean, String> valueForNoSelection(@Nonnull JComboBox comboBox) {
    if (!comboBox.isEditable()) {
      return NO_SELECTION;
    }
    Object selectedItem = comboBox.getSelectedItem();
    if (selectedItem instanceof String) {
      return selection((String) selectedItem);
    }
    if (selectedItem != null) {
      return selection(selectedItem.toString());
    }
    return NO_SELECTION;
  }

  private static @Nonnull Pair<Boolean, String> selection(@Nullable String selection) {
    return Pair.of(true, selection);
  }

  private JComboBoxSelectionValueQuery() {
  }
}
