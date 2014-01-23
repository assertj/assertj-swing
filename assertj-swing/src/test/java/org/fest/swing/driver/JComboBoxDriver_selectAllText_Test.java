/*
 * Created on Feb 24, 2008
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.junit.Test;

/**
 * Tests for {@link JComboBoxDriver#selectAllText(javax.swing.JComboBox)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxDriver_selectAllText_Test extends JComboBoxDriver_TestCase {
  @Test
  public void should_throw_error_if_JComboBox_is_disabled() {
    disableComboBox();
    try {
      driver.selectAllText(comboBox);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JComboBox_is_not_showing_on_the_screen() {
    try {
      driver.selectAllText(comboBox);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JComboBox_is_not_editable() {
    showWindow();
    try {
      driver.selectAllText(comboBox);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotEditableComboBox(e);
    }
  }

  @Test
  public void should_select_all_text() {
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
