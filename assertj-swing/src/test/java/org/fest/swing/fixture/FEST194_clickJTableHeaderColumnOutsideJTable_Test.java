/*
 * Created on Jul 22, 2009
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

import static javax.swing.JTable.AUTO_RESIZE_OFF;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Arrays.array;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-194" target="_blank">FEST-194</a>
 * 
 * @author Andriy Tsykholyas
 * @author Alex Ruiz
 */
public class FEST194_clickJTableHeaderColumnOutsideJTable_Test extends RobotBasedTestCase {
  private HeaderMouseListener headerMouseListener;
  private JTableFixture table;

  @Override
  protected final void onSetUp() {
    headerMouseListener = new HeaderMouseListener();
    MyWindow window = MyWindow.createNew();
    window.table.getTableHeader().addMouseListener(headerMouseListener);
    table = new JTableFixture(robot, window.table);
    robot.showWindow(window);
  }

  @Test
  public void should_scroll_to_header_column_to_click() {
    int columnCount = columnCountOf(table.target());
    for (int column = 0; column < columnCount; column++) {
      table.tableHeader().clickColumn(column);
      assertThat(headerMouseListener.headerClickCounter).isEqualTo(column + 1);
    }
  }

  @RunsInEDT
  static int columnCountOf(final JTable table) {
    return execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return table.getColumnCount();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final int COLUMN_WIDTH = 200;
    private static final Dimension FRAME_SIZE = new Dimension((int) (COLUMN_WIDTH * 1.3), 200);

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JTable table = table();

    private MyWindow() {
      super(FEST194_clickJTableHeaderColumnOutsideJTable_Test.class);
      setLayout(new GridLayout(1, 0));
      setMinimumSize(FRAME_SIZE);
      setMaximumSize(FRAME_SIZE);
      setPreferredSize(FRAME_SIZE);
      addComponents(new JScrollPane(table));
    }

    private static JTable table() {
      JTable table = new JTable(tableData(), array("Visible", "Semi Visible", "Not Visible"));
      table.setAutoResizeMode(AUTO_RESIZE_OFF);
      for (int col = 0; col < table.getColumnCount(); col++) {
        configure(table.getColumnModel().getColumn(col));
      }
      return table;
    }

    private static Object[][] tableData() {
      return new Object[][] { { "data", "data", "data" }, { "data", "data", "data" }, { "data", "data", "data" } };
    }

    private static void configure(TableColumn column) {
      column.setPreferredWidth(COLUMN_WIDTH);
      column.setMaxWidth(COLUMN_WIDTH);
      column.setMinWidth(COLUMN_WIDTH);
    }
  }

  private static class HeaderMouseListener extends MouseAdapter {
    int headerClickCounter;

    HeaderMouseListener() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      headerClickCounter++;
    }
  }
}
