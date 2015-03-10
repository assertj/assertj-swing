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

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.test.swing.CustomCellRenderer;
import org.junit.Test;

/**
 * Tests for {@link BasicJTableCellReader#valueAt(JTable, int, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJTableCellReader_valueAt_Test extends BasicJTableCellReader_TestCase {
  @Test
  public void should_Return_ToString_From_Model_If_CellRenderer_Is_Not_Recognized() {
    setModelData(table, new Object[][] { array(new Jedi("Yoda")) }, array("Names"));
    setNotRecognizedCellRendererTo(table);
    robot.waitForIdle();
    String value = valueAt(reader, table, 0, 0);
    assertThat(value).isEqualTo("Yoda");
  }

  @RunsInEDT
  private static void setModelData(final JTable table, final Object[][] data, final Object[] columnNames) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
      }
    });
  }

  @RunsInEDT
  private static void setNotRecognizedCellRendererTo(final JTable table) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        setCellRendererComponent(table, new JToolBar());
      }
    });
  }

  @Test
  public void should_Return_Text_From_CellRenderer_If_It_Is_JLabel() {
    setJLabelAsCellRenderer();
    String value = valueAt(reader, table, 0, 0);
    assertThat(value).isEqualTo("Hello");
  }

  @Test
  public void should_Return_Selection_From_CellRenderer_If_It_Is_JComboBox() {
    setJComboBoxAsCellRendererWithSelection(1);
    robot.waitForIdle();
    String value = valueAt(reader, table, 0, 0);
    assertThat(value).isEqualTo("Two");
  }

  @Test
  public void should_Return_Null_If_CellRenderer_Is_JComboBox_Without_Selection() {
    setJComboBoxAsCellRendererWithSelection(-1);
    robot.waitForIdle();
    String value = valueAt(reader, table, 0, 0);
    assertThat(value).isNull();
  }

  @RunsInEDT
  void setJComboBoxAsCellRendererWithSelection(int itemIndex) {
    setJComboBoxAsCellRenderer(table, itemIndex);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setJComboBoxAsCellRenderer(final JTable table, final int itemIndex) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        JComboBox comboBox = new JComboBox(array("One", "Two"));
        comboBox.setSelectedIndex(itemIndex);
        setCellRendererComponent(table, comboBox);
      }
    });
  }

  @Test
  public void should_Return_Selection_From_CellRenderer_If_It_Is_JCheckBox() {
    setJCheckBoxAsCellRenderer(table, "Hello", true);
    robot.waitForIdle();
    String value = valueAt(reader, table, 0, 0);
    assertThat(value).isEqualTo("true");
  }

  @RunsInEDT
  private static void setJCheckBoxAsCellRenderer(final JTable table, final String text, final boolean selected) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        JCheckBox checkBox = new JCheckBox(text, selected);
        setCellRendererComponent(table, checkBox);
      }
    });
  }

  @RunsInCurrentThread
  private static void setCellRendererComponent(JTable table, Component renderer) {
    CustomCellRenderer cellRenderer = new CustomCellRenderer(renderer);
    table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
  }

  @RunsInEDT
  private static String valueAt(final BasicJTableCellReader reader, final JTable table, final int row, final int column) {
    return execute(new GuiQuery<String>() {
      @Override
      protected String executeInEDT() {
        return reader.valueAt(table, row, column);
      }
    });
  }
}
