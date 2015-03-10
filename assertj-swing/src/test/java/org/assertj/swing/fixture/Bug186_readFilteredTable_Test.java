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

import static javax.swing.RowFilter.regexFilter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.data.TableCell.row;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestTable;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=186" target="_blank">Bug 186</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug186_readFilteredTable_Test extends RobotBasedTestCase {
  private FrameFixture frame;

  @Override
  protected void onSetUp() {
    frame = new FrameFixture(robot, MyWindow.createNew());
    frame.show();
  }

  @Test
  public void should_Read_First_Table_Cell_After_Filtering_Data() {
    frame.textBox("textBox").enterText("2");
    assertThat(frame.table("table").valueAt(row(0).column(0))).isEqualTo("2-0");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();

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
      super(Bug186_readFilteredTable_Test.class);
      add(textBox());
      add(table());
      setPreferredSize(new Dimension(200, 200));
    }

    private JTextField textBox() {
      final JTextField textBox = new JTextField(20);
      textBox.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
          filterTable();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
          filterTable();
        }

        private void filterTable() {
          sorter.setRowFilter(regexFilter(textBox.getText(), 0));
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
      });
      textBox.setName("textBox");
      return textBox;
    }

    private TestTable table() {
      TestTable table = new TestTable("table", 5, 2);
      sorter.setModel(table.getModel());
      table.setRowSorter(sorter);
      return table;
    }
  }
}
