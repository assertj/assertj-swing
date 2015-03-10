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
import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.CustomCellRenderer;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link BasicJComboBoxCellReader#valueAt(JComboBox, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJComboBoxCellReader_valueAt_Test extends RobotBasedTestCase {
  private JComboBox comboBox;
  private BasicJComboBoxCellReader reader;

  @Override
  protected void onSetUp() {
    reader = new BasicJComboBoxCellReader();
    MyWindow window = MyWindow.createNew();
    comboBox = window.comboBox;
  }

  @Test
  public void should_Return_Text_From_JLabel_As_CellRenderer() {
    String value = firstItemValue(reader, comboBox);
    assertThat(value).isEqualTo("First");
  }

  @Test
  public void should_Return_Model_Value_ToString_If_CellRender_Not_Recognized() {
    setModelValues(comboBox, array(new Jedi("Yoda")));
    setNotRecognizedRendererComponent(comboBox);
    robot.waitForIdle();
    String value = firstItemValue(reader, comboBox);
    assertThat(value).isEqualTo("Yoda");
  }

  @Test
  public void should_Return_Null_If_CellRenderer_Not_Recognized_And_Model_Value_Is_Null() {
    setModelValues(comboBox, new Object[] { null });
    setNotRecognizedRendererComponent(comboBox);
    robot.waitForIdle();
    String value = firstItemValue(reader, comboBox);
    assertThat(value).isNull();
  }

  @RunsInEDT
  private static void setModelValues(final JComboBox comboBox, final Object[] values) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        comboBox.setModel(new DefaultComboBoxModel(values));
      }
    });
  }

  @RunsInEDT
  private static void setNotRecognizedRendererComponent(final JComboBox comboBox) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        comboBox.setRenderer(new CustomCellRenderer(new JToolBar()));
      }
    });
  }

  @RunsInEDT
  private static String firstItemValue(final BasicJComboBoxCellReader reader, final JComboBox comboBox) {
    return execute(new GuiQuery<String>() {
      @Override
      protected String executeInEDT() {
        return reader.valueAt(comboBox, 0);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    final JComboBox comboBox = new JComboBox(array("First"));

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
      super(BasicJComboBoxCellReader_valueAt_Test.class);
      addComponents(comboBox);
    }
  }
}
