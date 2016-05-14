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

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JTable;

import org.assertj.swing.driver.BasicJTableCellReader;

import com.jidesoft.combobox.ListComboBox;

/**
 * TODO
 *
 * @author Peter Murray
 */
public class JideTableCellReader extends BasicJTableCellReader {

  private AbstractComboBoxCellReader _listComboReader = new AbstractComboBoxCellReader();

  @Override
  public String valueAt(JTable table, int row, int column) {
    Component c = getCellRendererIn(table, row, column);

    // TODO may need to support more types of components here
    if (c instanceof ListComboBox) {
      return valueAt((ListComboBox) c);
    }

    return super.valueAt(table, row, column);
  }

  private String valueAt(ListComboBox comboBox) {
    int selectedIndex = comboBox.getSelectedIndex();
    if (selectedIndex == -1) {
      return null;
    }
    return _listComboReader.valueAt(comboBox, selectedIndex);
  }

  /** Should be able to do this without resorting to reflection - PM */
  @Deprecated
  private Component getCellRendererIn(JTable table, int row, int column) {
    try {
      Method m = getClass().getSuperclass().getDeclaredMethod("cellRendererIn",
                                                              JTable.class,
                                                              int.class,
                                                              int.class);
      m.setAccessible(true);
      return (Component) m.invoke(this, table, row, column);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }
    throw new IllegalStateException("Failed to call superclass method to resolve contents");
  }
}
