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
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.JComboBox;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.junit.Test;

/**
 * Tests for {@link JComboBoxDriver#showDropDownList(javax.swing.JComboBox)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxDriver_showDropDownList_Test extends JComboBoxDriver_TestCase {
  @Test
  public void should_Show_Drop_Down_List_In_Not_Editable_JComboBox() {
    showWindow();
    driver.showDropDownList(comboBox);
    assertThatDropDownIsVisible();
  }

  @Test
  public void should_Show_Drop_Down_List_In_Editable_JComboBox() {
    showWindow();
    makeEditable();
    driver.showDropDownList(comboBox);
    assertThatDropDownIsVisible();
  }

  @RunsInEDT
  private void assertThatDropDownIsVisible() {
    assertThat(isDropDownVisible(comboBox)).isTrue();
  }

  @RunsInEDT
  private static boolean isDropDownVisible(final JComboBox comboBox) {
    Boolean result = execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return comboBox.getUI().isPopupVisible(comboBox);
      }
    });
    return checkNotNull(result);
  }
}
