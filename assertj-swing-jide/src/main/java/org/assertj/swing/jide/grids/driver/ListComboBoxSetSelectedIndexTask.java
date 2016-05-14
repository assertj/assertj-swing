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
package org.assertj.swing.jide.grids.driver;

import static org.assertj.swing.edt.GuiActionRunner.execute;

import org.assertj.swing.annotation.RunsInEDT;

import com.jidesoft.combobox.ListComboBox;

/**
 * Understands a task that selects the element in the given index in the given <code>{@link ListComboBox}</code>. This
 * task is executed in the event dispatch thread.
 *
 * @see ListComboBox#setSelectedIndex(int)
 */
final class ListComboBoxSetSelectedIndexTask {

  @RunsInEDT
  static void setSelectedIndex(final ListComboBox comboBox, final int index) {
    execute(() -> comboBox.setSelectedIndex(index));
  }

  private ListComboBoxSetSelectedIndexTask() {
  }
}