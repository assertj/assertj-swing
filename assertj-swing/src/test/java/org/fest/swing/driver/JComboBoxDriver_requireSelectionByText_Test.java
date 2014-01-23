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
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import javax.swing.JComboBox;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link JComboBoxDriver#requireSelection(javax.swing.JComboBox, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxDriver_requireSelectionByText_Test extends JComboBoxDriver_TestCase {
  @Test
  public void should_pass_if_JComboBox_has_expected_selection() {
    selectFirstItem();
    driver.requireSelection(comboBox, "first");
    assertThatCellReaderWasCalled();
  }

  @Test
  public void should_pass_if_JComboBox_has_matching_selection() {
    selectFirstItem();
    driver.requireSelection(comboBox, "firs.*");
    assertThatCellReaderWasCalled();
  }

  @Test
  public void should_fail_if_JComboBox_does_not_have_expected_selection() {
    selectFirstItem();
    try {
      driver.requireSelection(comboBox, "second");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndex'").contains(
          "actual value:<'first'> is not equal to or does not match pattern:<'second'>");
    }
  }

  @Test
  public void should_fail_if_JComboBox_does_not_have_any_selection() {
    clearSelection();
    try {
      driver.requireSelection(comboBox, "second");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndex'").contains("No selection");
    }
  }

  @Test
  public void should_pass_if_editable_JComboBox_has_expected_selection() {
    makeEditableAndSelect("Hello World");
    driver.requireSelection(comboBox, "Hello World");
  }

  @Test
  public void should_fail_if_editable_JComboBox_does_not_have_expected_selection() {
    makeEditableAndSelect("Hello World");
    try {
      driver.requireSelection(comboBox, "second");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndex'").contains(
          "actual value:<'Hello World'> is not equal to or does not match pattern:<'second'>");
    }
  }

  @Test
  public void should_fail_if_editable_JComboBox_does_not_have_any_selection() {
    makeEditableAndClearSelection(comboBox);
    robot.waitForIdle();
    try {
      driver.requireSelection(comboBox, "second");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selectedIndex'").contains("No selection");
    }
  }

  @RunsInEDT
  private static void makeEditableAndClearSelection(final JComboBox comboBox) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        comboBox.setSelectedIndex(-1);
        comboBox.setEditable(true);
      }
    });
  }
}
