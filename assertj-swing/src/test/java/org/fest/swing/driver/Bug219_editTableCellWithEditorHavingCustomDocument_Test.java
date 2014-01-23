/*
 * Created on Nov 29, 2008
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

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JTableCellValueQuery.cellValueOf;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Container;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestTable;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=219" target="_blank">Bug 219</a>.
 *
 * @author matthiaso
 * @author Alex Ruiz
 */
public class Bug219_editTableCellWithEditorHavingCustomDocument_Test extends RobotBasedTestCase {
  private static final int COLUMN = 1;
  private static final int ROW = 0;
  private JTableCellWriter cellWriter;
  private TestTable table;

  @Override
  protected void onSetUp() {
    cellWriter = new BasicJTableCellWriter(robot);
    MyWindow window = MyWindow.createNew();
    table = window.table;
    robot.showWindow(window);
  }

  @Test
  public void should_edit_cell_without_activating_error_message() {
    cellWriter.enterValue(table, ROW, COLUMN, "Hello");
    assertThat(valueAt(ROW, COLUMN)).isEqualTo("Hello");
  }

  @RunsInEDT
  private Object valueAt(int row, int column) {
    return cellValueOf(table, row, column);
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final TestTable table = new TestTable(3, 3);

    private MyWindow() {
      super(Bug219_editTableCellWithEditorHavingCustomDocument_Test.class);
      addComponents(table);
      DefaultCellEditor cellEditor = new DefaultCellEditor(new JTextField(new ExampleDocument(this), "", 10));
      table.getColumnModel().getColumn(COLUMN).setCellEditor(cellEditor);
      table.cellEditable(ROW, COLUMN, true);
    }
  }

  private static class ExampleDocument extends PlainDocument {
    private final Container errorMessageOwner;

    ExampleDocument(Container errorMessageOwner) {
      this.errorMessageOwner = errorMessageOwner;
    }

    /*
     * Inserts a text with a maximum length of 10 characters.
     */
    @Override
    public void insertString(int offs, String s, AttributeSet a) throws BadLocationException {
      // If length is less than 10 characters, insert it
      if (s != null && getLength() + s.length() <= 10) {
        super.insertString(offs, s, a);
        return;
      }
      showMessageDialog(errorMessageOwner, "Maximum length: 10 characters", "Maximum length exceeded", ERROR_MESSAGE);
    }
  }
}
