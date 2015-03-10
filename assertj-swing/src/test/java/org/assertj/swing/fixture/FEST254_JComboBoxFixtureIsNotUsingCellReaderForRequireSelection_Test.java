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

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-254" target="_blank">FEST-254</a>
 * 
 * @author Glen Schrader
 * @author Alex Ruiz
 */
public class FEST254_JComboBoxFixtureIsNotUsingCellReaderForRequireSelection_Test extends RobotBasedTestCase {
  private JComboBox comboBox;
  private JComboBoxFixture fixture;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    comboBox = window.comboBox;
    fixture = new JComboBoxFixture(robot, comboBox);
    robot.showWindow(window);
  }

  @Test
  public void should_Return_Text_Of_Selected_Item_In_Editable_JComboBox() {
    selectIndex(comboBox, 0);
    robot.waitForIdle();
    assertThat(fixture.requireSelection("first"));
  }

  @RunsInEDT
  private static void selectIndex(final JComboBox comboBox, final int index) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        comboBox.setSelectedIndex(index);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private final JComboBox comboBox;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(FEST254_JComboBoxFixtureIsNotUsingCellReaderForRequireSelection_Test.class);
      comboBox = new JComboBox(array(TestValue.values()));
      comboBox.setEditable(true);
      comboBox.setEditor(new MyComboBoxEditor());
      comboBox.setRenderer(new MyComboBoxRenderer());
      comboBox.setSelectedIndex(-1);
      addComponents(comboBox);
    }
  }

  private static class MyComboBoxEditor extends BasicComboBoxEditor {
    @Override
    public void setItem(Object anObject) {
      super.setItem(renderIfValue(anObject));
    }

    @Override
    public Object getItem() {
      String text = editor.getText();
      try {
        return TestValue.valueOf(text.toUpperCase());
      } catch (IllegalArgumentException e) {
        return null;
      }
    }
  }

  private static class MyComboBoxRenderer extends BasicComboBoxRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
        boolean cellHasFocus) {
      return super.getListCellRendererComponent(list, renderIfValue(value), index, isSelected, cellHasFocus);
    }
  }

  private static Object renderIfValue(Object o) {
    if (o instanceof TestValue) {
      return renderValue((TestValue) o);
    }
    return o;
  }

  private static String renderValue(TestValue v) {
    return v == null ? null : v.toString().toLowerCase();
  }

  private static enum TestValue {
    FIRST, SECOND, THIRD
  }
}