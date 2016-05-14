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
import javax.swing.JComboBox;

import org.assertj.core.util.Preconditions;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.cell.JComboBoxCellReader;
import org.assertj.swing.util.TextMatcher;

/**
 * Looks up the first item in a {@code JComboBox} whose value matches a given one.
 *
 * @author Alex Ruiz
 */
final class JComboBoxMatchingItemQuery {
  @RunsInEDT
  static int matchingItemIndex(final @Nonnull JComboBox<?> comboBox, final @Nonnull TextMatcher matcher,
                               final @Nonnull JComboBoxCellReader cellReader) {
    Integer result = execute(() -> {
      int itemCount = comboBox.getItemCount();
      for (int i = 0; i < itemCount; i++) {
        String value = cellReader.valueAt(comboBox, i);
        if (value != null && matcher.isMatching(value)) {
          return i;
        }
      }
      return -1;
    });
    return Preconditions.checkNotNull(result);
  }

  private JComboBoxMatchingItemQuery() {
  }
}
