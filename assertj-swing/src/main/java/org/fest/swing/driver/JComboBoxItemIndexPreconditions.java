/*
 * Created on Nov 10, 2008
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

import static java.lang.String.valueOf;
import static org.fest.util.Strings.concat;

import javax.annotation.Nonnull;
import javax.swing.JComboBox;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * <p>
 * Validates that a given number is a valid index of an item in a {@code JComboBox}.
 * </p>
 *
 * <p>
 * <b>Note:</b> Methods in this class are accessed in the current executing thread. Such thread may or may not be the
 * event dispatch thread (EDT.) Client code must call methods in this class from the EDT.
 * </p>
 *
 * @author Alex Ruiz
 */
final class JComboBoxItemIndexPreconditions {
  @RunsInCurrentThread
  static void checkItemIndexInBounds(@Nonnull JComboBox comboBox, int index) {
    if (index < 0) {
      String msg = String.format("%s should not be less than zero", itemIndex(index));
      throw new IndexOutOfBoundsException(msg);
    }
    int itemCount = comboBox.getItemCount();
    if (itemCount == 0) {
      throw new IndexOutOfBoundsException("JComboBox is empty");
    }
    if (index >= 0 && index < itemCount) {
      return;
    }
    String msg = String.format("%s should be between [0] and [%d] (inclusive)", itemIndex(index), itemCount - 1);
    throw new IndexOutOfBoundsException(msg);
  }

  private static String itemIndex(int index) {
    return concat("Item index (", valueOf(index), ")");
  }

  private JComboBoxItemIndexPreconditions() {}
}
