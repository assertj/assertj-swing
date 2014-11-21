/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2008-2010 the original author or authors.
 */

package org.fest.swing.jide.grids.driver;

import com.jidesoft.combobox.AbstractComboBox;
import com.jidesoft.combobox.ListComboBox;
import com.jidesoft.converter.ObjectConverterManager;

/**
 * A CellReader for extracting a String representation of a value in an
 * {@link AbstractComboBox}.
 * @author Peter Murray
 */
public class AbstractComboBoxCellReader {

  /**
   * Returns the internal value of a cell in a <code>{@link ListComboBox}</code> as expected
   * in a test. This method first tries to get the value from the <code>toString</code>
   * implementation of the object stored in the <code>JComboBox</code>'s model at the
   * specified index. If it fails, it returns the value displayed in the
   * <code>ListComboBox</code>'s cell renderer.
   * @param comboBox the given <code>ListComboBox</code>.
   * @param index the index of the cell.
   * @return the internal value of a cell in a <code>ListComboBox</code> as expected in a
   *         test.
   */
  public String valueAt(AbstractComboBox comboBox, int index) {
    final Object item = itemAt(comboBox, index);
    return valueAsText(comboBox, item);
  }

  public String valueAsText(AbstractComboBox comboBox, Object value) {
    if (value == null) {
      return null;
    }

    if (comboBox.getConverter() != null) {
      return comboBox.getConverter().toString(value, comboBox.getConverterContext());
    } else {
      return ObjectConverterManager.toString(value,
                                             comboBox.getType(),
                                             comboBox.getConverterContext());
    }
  }

  private Object itemAt(AbstractComboBox comboBox, int index) {
    return comboBox.getModel().getElementAt(index);
  }
}
