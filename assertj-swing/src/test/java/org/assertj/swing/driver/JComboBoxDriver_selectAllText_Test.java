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
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.junit.Test;

/**
 * Tests for {@link JComboBoxDriver#selectAllText(javax.swing.JComboBox)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxDriver_selectAllText_Test extends JComboBoxDriver_TestCase {
  @Test
  public void should_Throw_Error_If_JComboBox_Is_Disabled() {
    disableComboBox();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.selectAllText(comboBox);
  }

  @Test
  public void should_Throw_Error_If_JComboBox_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.selectAllText(comboBox);
  }

  @Test
  public void should_Throw_Error_If_JComboBox_Is_Not_Editable() {
    showWindow();
    assertThatIllegalStateExceptionCauseIsNotEditableComboBox();
    driver.selectAllText(comboBox);
  }

  @Test
  public void should_Select_All_Text() {
    showWindow();
    makeEditableAndSelectFirstItem();
    robot.waitForIdle();
    driver.selectAllText(comboBox);
    assertSelectedTextIs("first");
  }

  @RunsInEDT
  private void assertSelectedTextIs(String expected) {
    assertThat(selectedTextOf(comboBox)).isEqualTo(expected);
  }

  @RunsInEDT
  private static String selectedTextOf(final JComboBox comboBox) {
    return execute(new GuiQuery<String>() {
      @Override
      protected String executeInEDT() {
        Component editor = comboBox.getEditor().getEditorComponent();
        assertThat(editor).isInstanceOf(JTextComponent.class);
        return ((JTextComponent) editor).getSelectedText();
      }
    });
  }
}
