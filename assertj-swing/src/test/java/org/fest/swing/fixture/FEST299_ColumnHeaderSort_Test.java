/*
 * Created on Feb 6, 2010
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
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.swing.data.TableCell.row;
import static org.fest.util.Arrays.array;

import javax.swing.*;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-299" target="_blank">FEST-299</a>.
 *
 * @author Rahel Luethy
 * @author Alex Ruiz
 */
public class FEST299_ColumnHeaderSort_Test extends RobotBasedTestCase {
  private FrameFixture frame;

  @Override protected void onSetUp() {
    frame = new FrameFixture(robot, MyWindow.createNew());
    frame.show();
  }

  @Test
  public void should_sort_table_when_clicking_header_several_times() {
    JTableFixture table = frame.table();
    JTableHeaderFixture header = table.tableHeader();
    header.clickColumn(0); // sort-up
    header.clickColumn(0); // sort-down
    table.requireCellValue(row(0).column(0), "c");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return GuiActionRunner.execute(new GuiQuery<MyWindow>() {
        @Override protected MyWindow executeInEDT() throws Throwable {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(FEST299_ColumnHeaderSort_Test.class);
      add(new JScrollPane(table()));
      setSize(200, 200);
    }

    private static JTable table() {
      String[][] data = { { "a" }, { "c" }, { "b" } };
      JTable table = new JTable(data, array("column"));
      table.setAutoCreateRowSorter(true);
      return table;
    }
  }
}
