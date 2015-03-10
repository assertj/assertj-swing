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

import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.util.regex.Pattern;

import javax.swing.JComboBox;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link JComboBoxDriver#requireSelection(javax.swing.JComboBox, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxDriver_requireSelectionByText_Test extends JComboBoxDriver_TestCase {
  @Test
  public void should_Pass_If_JComboBox_Has_Expected_Selection() {
    selectFirstItem();
    driver.requireSelection(comboBox, "first");
    assertThatCellReaderWasCalled();
  }

  @Test
  public void should_Pass_If_JComboBox_Has_Matching_Selection() {
    selectFirstItem();
    driver.requireSelection(comboBox, "firs.*");
    assertThatCellReaderWasCalled();
  }

  @Test
  public void should_Fail_If_JComboBox_Does_Not_Have_Expected_Selection() {
    selectFirstItem();
    thrown.expectAssertionError("selectedIndex", "first", Pattern.compile("second"));
    driver.requireSelection(comboBox, "second");
  }

  @Test
  public void should_Fail_If_JComboBox_Does_Not_Have_Any_Selection() {
    clearSelection();
    thrown.expect(AssertionError.class);
    thrown.expectMessageToContain("property:'selectedIndex'", "No selection");
    driver.requireSelection(comboBox, "second");
  }

  @Test
  public void should_Pass_If_Editable_JComboBox_Has_Expected_Selection() {
    makeEditableAndSelect("Hello World");
    driver.requireSelection(comboBox, "Hello World");
  }

  @Test
  public void should_Fail_If_Editable_JComboBox_Does_Not_Have_Expected_Selection() {
    makeEditableAndSelect("Hello World");
    thrown.expectAssertionError("selectedIndex", "Hello World", Pattern.compile("second"));
    driver.requireSelection(comboBox, "second");
  }

  @Test
  public void should_Fail_If_Editable_JComboBox_Does_Not_Have_Any_Selection() {
    makeEditableAndClearSelection(comboBox);
    robot.waitForIdle();
    thrown.expect(AssertionError.class);
    thrown.expectMessageToContain("property:'selectedIndex'", "No selection");
    driver.requireSelection(comboBox, "second");
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
