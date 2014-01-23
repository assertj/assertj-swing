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

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.swing.CustomCellRenderer;
import org.junit.Test;

/**
 * Tests for {@link BasicJTableCellReader#valueAt(JTable, int, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJTableCellReader_valueAt_Test extends BasicJTableCellReader_TestCase {
  @Test
  public void should_return_toString_from_model_if_cellRenderer_is_not_recognized() {
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
  public void should_return_text_from_cellRenderer_if_it_is_JLabel() {
    setJLabelAsCellRenderer();
    String value = valueAt(reader, table, 0, 0);
    assertThat(value).isEqualTo("Hello");
  }

  @Test
  public void should_return_selection_from_cellRenderer_if_it_is_JComboBox() {
    setJComboBoxAsCellRendererWithSelection(1);
    robot.waitForIdle();
    String value = valueAt(reader, table, 0, 0);
    assertThat(value).isEqualTo("Two");
  }

  @Test
  public void should_return_null_if_cellRenderer_is_JComboBox_without_selection() {
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
  public void should_return_selection_from_cellRenderer_if_it_is_JCheckBox() {
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
