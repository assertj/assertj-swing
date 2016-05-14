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
package org.assertj.swing.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.query.JComboBoxSelectedItemQuery.selectedItemOf;
import static org.assertj.swing.test.task.JComboBoxSetSelectedItemTask.setSelectedItem;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.junit.Test;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=210" target="_blank">Bug 210</a>.
 * <p>
 * Demonstrate bug when testing {@code JComboBox}es. If value programmatically added to {@code JComboBox} using
 * {@code setSelectedItem} then AssertJ-Swing assertion {@code requireSelection} fails while JUnit {@code assertEqual}
 * passes. (FEST 1.0b1, Java 1.5)
 * </p>
 *
 * @author Ewan McDougall
 * @author Alex Ruiz
 */
public class Bug210_editableComboBox_Test extends RobotBasedTestCase {
  private final static String ADDED_STRING = "rocket";
  private String[] values;
  private DialogFixture dialog;

  @Override
  protected void onSetUp() {
    values = array("hat", "son");
    dialog = new DialogFixture(robot, MyDialog.createNew(values));
    dialog.show();
  }

  @Test
  public void should_Add_String() {
    JComboBoxFixture comboBox = dialog.comboBox("cb");
    comboBox.requireSelection("hat");
    comboBox.enterText(ADDED_STRING);
    setSelectedItem(comboBox.target(), ADDED_STRING);
    assertThat(selectedItemOf(comboBox.target())).isEqualTo(ADDED_STRING);
    comboBox.requireSelection(ADDED_STRING);
  }

  private static class MyDialog extends JDialog {
    @RunsInEDT
    static MyDialog createNew(final String[] items) {
      return execute(() -> new MyDialog(items));
    }

    private MyDialog(String[] items) {
      JComboBox comboBox = new JComboBox(items);
      comboBox.setEditable(true);
      comboBox.setSelectedIndex(0);
      comboBox.setName("cb");
      add(comboBox);
      setTitle(Bug210_editableComboBox_Test.class.getSimpleName());
    }
  }
}
