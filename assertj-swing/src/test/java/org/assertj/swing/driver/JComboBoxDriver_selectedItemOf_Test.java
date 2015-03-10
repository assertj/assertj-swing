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
import static org.assertj.swing.driver.JComboBoxMakeEditableAndSelectItemTask.makeEditableAndSelectIndex;
import static org.assertj.swing.driver.JComboBoxMakeEditableAndSelectItemTask.makeEditableAndSelectItem;
import static org.assertj.swing.driver.JComboBoxSetSelectedIndexTask.setSelectedIndex;

import org.junit.Test;

/**
 * Tests for {@link JComboBoxDriver#selectedItemOf(javax.swing.JComboBox)}.
 * 
 * @author Alex Ruiz
 */
public class JComboBoxDriver_selectedItemOf_Test extends JComboBoxDriver_TestCase {
  @Test
  public void should_Return_Null_If_Not_Editable_JComboBox_Does_Not_Have_Selection() {
    clearSelection();
    String selection = driver.selectedItemOf(comboBox);
    assertThat(selection).isNull();
  }

  @Test
  public void should_Return_True_And_Text_Of_Selected_Item_In_Not_Editable_JComboBox() {
    setSelectedIndex(comboBox, 0);
    robot.waitForIdle();
    String selection = driver.selectedItemOf(comboBox);
    assertThat(selection).isEqualTo("first");
  }

  @Test
  public void should_Return_Text_Of_Selected_Item_In_Editable_JComboBox() {
    makeEditableAndSelectIndex(comboBox, 0);
    robot.waitForIdle();
    String selection = driver.selectedItemOf(comboBox);
    assertThat(selection).isEqualTo("first");
  }

  @Test
  public void should_Return_Text_Of_Entered_Item_In_Editable_JComboBox() {
    makeEditableAndSelectItem(comboBox, "Hello");
    robot.waitForIdle();
    String selection = driver.selectedItemOf(comboBox);
    assertThat(selection).isEqualTo("Hello");
  }
}
