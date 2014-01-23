/*
 * Created on Aug 28, 2008
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
package org.fest.swing.test.builder;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Arrays.isNullOrEmpty;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Factory of {@code JComboBox}s.
 *
 * @author Alex Ruiz
 */
public final class JComboBoxes {
  private JComboBoxes() {}

  public static JComboBoxFactory comboBox() {
    return new JComboBoxFactory();
  }

  public static class JComboBoxFactory {
    boolean editable;
    Object items[];
    String name;
    int selectedIndex = -1;

    public JComboBoxFactory editable(boolean isEditable) {
      editable = isEditable;
      return this;
    }

    public JComboBoxFactory withItems(Object... newItems) {
      items = newItems;
      return this;
    }

    public JComboBoxFactory withName(String newName) {
      name = newName;
      return this;
    }

    public JComboBoxFactory withSelectedIndex(int newSelectedIndex) {
      selectedIndex = newSelectedIndex;
      return this;
    }

    @RunsInEDT
    public JComboBox createNew() {
      return execute(new GuiQuery<JComboBox>() {
        @Override
        protected JComboBox executeInEDT() {
          JComboBox comboBox = new JComboBox();
          comboBox.setEditable(editable);
          if (!isNullOrEmpty(items)) {
            comboBox.setModel(new DefaultComboBoxModel(items));
          }
          comboBox.setName(name);
          comboBox.setSelectedIndex(selectedIndex);
          return comboBox;
        }
      });
    }
  }
}