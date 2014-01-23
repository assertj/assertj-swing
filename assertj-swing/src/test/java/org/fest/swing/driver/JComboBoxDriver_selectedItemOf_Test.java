/*
 * Created on Jul 26, 2010
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
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JComboBoxMakeEditableAndSelectItemTask.makeEditableAndSelectIndex;
import static org.fest.swing.driver.JComboBoxMakeEditableAndSelectItemTask.makeEditableAndSelectItem;
import static org.fest.swing.driver.JComboBoxSetSelectedIndexTask.setSelectedIndex;

import org.junit.Test;

/**
 * Tests for {@link JComboBoxDriver#selectedItemOf(javax.swing.JComboBox)}.
 * 
 * @author Alex Ruiz
 */
public class JComboBoxDriver_selectedItemOf_Test extends JComboBoxDriver_TestCase {
  @Test
  public void should_return_null_if_not_editable_JComboBox_does_not_have_selection() {
    clearSelection();
    String selection = driver.selectedItemOf(comboBox);
    assertThat(selection).isNull();
  }

  @Test
  public void should_return_true_and_text_of_selected_item_in_not_editable_JComboBox() {
    setSelectedIndex(comboBox, 0);
    robot.waitForIdle();
    String selection = driver.selectedItemOf(comboBox);
    assertThat(selection).isEqualTo("first");
  }

  @Test
  public void should_return_text_of_selected_item_in_editable_JComboBox() {
    makeEditableAndSelectIndex(comboBox, 0);
    robot.waitForIdle();
    String selection = driver.selectedItemOf(comboBox);
    assertThat(selection).isEqualTo("first");
  }

  @Test
  public void should_return_text_of_entered_item_in_editable_JComboBox() {
    makeEditableAndSelectItem(comboBox, "Hello");
    robot.waitForIdle();
    String selection = driver.selectedItemOf(comboBox);
    assertThat(selection).isEqualTo("Hello");
  }
}
