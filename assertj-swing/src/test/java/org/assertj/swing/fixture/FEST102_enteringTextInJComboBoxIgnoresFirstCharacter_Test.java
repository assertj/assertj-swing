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
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-102" target="_blank">FEST-102</a>
 *
 * @author Alex Ruiz
 */
public class FEST102_enteringTextInJComboBoxIgnoresFirstCharacter_Test extends RobotBasedTestCase {
  @Test
  public void should_Enter_Text_In_JComboBox_With__Integer_Values() {
    JComboBoxFixture comboBox = fixture(newArrayList(1999, 2000, 2001, 2002));
    comboBox.enterText("20");
    assertThatEditorHasValue(comboBox, "20");
  }

  @Test
  public void should_Enter_Text_In_JComboBox_With_String_Values() {
    JComboBoxFixture comboBox = fixture(newArrayList("1999", "2000", "2001", "2002"));
    comboBox.enterText("78");
    assertThatEditorHasValue(comboBox, "78");
  }

  private <T> JComboBoxFixture fixture(List<T> comboBoxItems) {
    MyWindow window = MyWindow.createNew(new Vector<T>(comboBoxItems));
    robot.showWindow(window);
    JComboBoxFixture comboBox = new JComboBoxFixture(robot, window.comboBox);
    return comboBox;
  }

  private void assertThatEditorHasValue(JComboBoxFixture comboBox, String expected) {
    assertThat(textOf(comboBox)).isEqualTo(expected);
  }

  @RunsInEDT
  private static String textOf(JComboBoxFixture comboBox) {
    final JComboBox c = comboBox.target();
    return execute(() -> ((JTextField) c.getEditor().getEditorComponent()).getText());
  }

  private static class MyWindow extends TestWindow {
    final JComboBox comboBox;

    @RunsInEDT
    static MyWindow createNew(final Vector<?> comboBoxItems) {
      return execute(() -> new MyWindow(comboBoxItems));
    }

    private MyWindow(Vector<?> comboBoxItems) {
      super(FEST102_enteringTextInJComboBoxIgnoresFirstCharacter_Test.class);
      comboBox = new JComboBox(comboBoxItems);
      comboBox.setEditable(true);
      comboBox.setSelectedIndex(-1);
      addComponents(comboBox);
    }
  }
}
