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

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;

/**
 * Selects the element in the given index in the given {@code JComboBox}. This task is executed in the event dispatch
 * thread (EDT.)
 * 
 * @see JComboBox#setSelectedIndex(int)
 * 
 * @author Alex Ruiz
 */
final class JComboBoxSetSelectedIndexTask {
  @RunsInEDT
  static void setSelectedIndex(final @Nonnull JComboBox comboBox, final int index) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        comboBox.setSelectedIndex(index);
      }
    });
  }

  private JComboBoxSetSelectedIndexTask() {
  }
}