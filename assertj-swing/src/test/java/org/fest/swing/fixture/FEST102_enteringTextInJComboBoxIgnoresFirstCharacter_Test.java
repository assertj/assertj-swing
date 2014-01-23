/*
 * Created on Mar 27, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Lists.newArrayList;

import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-102" target="_blank">FEST-102</a>
 *
 * @author Alex Ruiz
 */
public class FEST102_enteringTextInJComboBoxIgnoresFirstCharacter_Test extends RobotBasedTestCase {
  @Test
  public void should_enter_text_in_JComboBox_with__integer_values() {
    JComboBoxFixture comboBox = fixture(newArrayList(1999, 2000, 2001, 2002));
    comboBox.enterText("20");
    assertThatEditorHasValue(comboBox, "20");
  }

  @Test
  public void should_enter_text_in_JComboBox_with_String_values() {
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
    return execute(new GuiQuery<String>() {
      @Override
      protected String executeInEDT() {
        JTextField editor = (JTextField) c.getEditor().getEditorComponent();
        return editor.getText();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    final JComboBox comboBox;

    @RunsInEDT
    static MyWindow createNew(final Vector<?> comboBoxItems) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(comboBoxItems);
        }
      });
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
