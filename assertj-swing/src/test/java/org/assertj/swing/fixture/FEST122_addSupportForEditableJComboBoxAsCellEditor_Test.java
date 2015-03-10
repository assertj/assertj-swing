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

import static org.assertj.swing.data.TableCell.row;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-122" target="_blank">FEST-122</a>.
 * 
 * @author Alex Ruiz
 */
public class FEST122_addSupportForEditableJComboBoxAsCellEditor_Test extends RobotBasedTestCase {
  private FrameFixture frame;

  @Override
  protected void onSetUp() {
    frame = new FrameFixture(robot, MyWindow.createNew());
    frame.show();
  }

  @Test
  public void should_Enter_Value_In_JTable_Cell_With_Editable_JComboBox_As_Editor() {
    JTableCellFixture cell = frame.table("data").cell(row(0).column(0));
    cell.enterValue("Pink");
    cell.requireValue("Pink");
    cell.enterValue("Blue");
    cell.requireValue("Blue");
  }

  private static class MyWindow extends TestWindow {
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JTable table = data();

    private MyWindow() {
      super(FEST122_addSupportForEditableJComboBoxAsCellEditor_Test.class);
      table.setName("data");
      add(new JScrollPane(table));
    }

    private static JTable data() {
      JTable table = new JTable(6, 3);
      table.setPreferredScrollableViewportSize(new Dimension(200, 60));
      setUpColorColumn(table.getColumnModel().getColumn(0));
      return table;
    }

    private static void setUpColorColumn(TableColumn column) {
      JComboBox comboBox = new JComboBox();
      comboBox.setEditable(true);
      comboBox.addItem("Blue");
      comboBox.addItem("Red");
      comboBox.addItem("Yellow");
      column.setCellEditor(new DefaultCellEditor(comboBox));
    }
  }
}
