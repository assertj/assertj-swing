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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.JList;
import javax.swing.ListModel;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.junit.Test;

/**
 * Tests for {@link JComboBoxDriver#dropDownList()}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxDriver_dropDownList_Test extends JComboBoxDriver_TestCase {
  @Test
  public void should_Return_Drop_Down_List() {
    showWindow();
    driver.click(comboBox);
    JList dropDownList = driver.dropDownList();
    assertThat(contentsOf(dropDownList)).isEqualTo(array("first", "second", "third"));
  }

  @RunsInEDT
  private Object[] contentsOf(final JList list) {
    return execute(new GuiQuery<Object[]>() {
      @Override
      protected Object[] executeInEDT() {
        ListModel model = list.getModel();
        int elementCount = model.getSize();
        Object[] elements = new Object[elementCount];
        for (int i = 0; i < elementCount; i++) {
          elements[i] = model.getElementAt(i);
        }
        return elements;
      }
    });
  }
}
