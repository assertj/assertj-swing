/*
 * Created on Jul 10, 2009
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

import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JTable;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-150" target="_blank">FEST-150</a>.
 * 
 * @author Alex Ruiz
 */
public class FEST150_cannotTypeDashInTableCell_Test extends RobotBasedTestCase {
  private JTableFixture table;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    robot.showWindow(window);
    table = new JTableFixture(robot, window.table);
  }

  @Test
  public void should_enter_dash_in_cell() {
    table.cell(row(0).column(0)).enterValue("-");
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

    final JTable table = new JTable(6, 8);

    private MyWindow() {
      super(FEST150_cannotTypeDashInTableCell_Test.class);
      addComponents(table);
    }
  }
}
