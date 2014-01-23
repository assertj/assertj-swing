/*
 * Created on Apr 12, 2008
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
import static org.fest.util.Arrays.array;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.CustomCellRenderer;
import org.fest.swing.test.swing.TestWindow;
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
  public void should_return_text_from_JLabel_as_cellRenderer() {
    String value = firstItemValue(reader, comboBox);
    assertThat(value).isEqualTo("First");
  }

  @Test
  public void should_return_model_value_toString_if_cellRender_not_recognized() {
    setModelValues(comboBox, array(new Jedi("Yoda")));
    setNotRecognizedRendererComponent(comboBox);
    robot.waitForIdle();
    String value = firstItemValue(reader, comboBox);
    assertThat(value).isEqualTo("Yoda");
  }

  @Test
  public void should_return_null_if_cellRenderer_not_recognized_and_model_value_is_null() {
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
