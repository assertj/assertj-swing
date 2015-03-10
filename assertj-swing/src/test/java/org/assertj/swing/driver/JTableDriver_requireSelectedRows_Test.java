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

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.JTable;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link JTableDriver#requireSelectedRows(javax.swing.JTable, int[])}.
 * 
 * @author Alex Ruiz
 */
public class JTableDriver_requireSelectedRows_Test extends JTableDriver_TestCase {
  @Test
  public void should_Fail_If_JTable_Does_Not_Have_The_Expected_Selected_Rows() {
    selectRows(6, 8);
    thrown.expect(AssertionError.class);
    thrown.expectMessage("property:'selectedRows'");
    thrown.expectMessage("Expecting:\n <[6, 7, 8]>\nto contain:\n <[0, 1]>\nbut could not find:\n <[0, 1]>");
    driver.requireSelectedRows(table, 0, 1);
  }

  @Test
  public void should_Pass_If_JTable_Has_Expected_Selected_Rows() {
    selectRows(6, 8);
    driver.requireSelectedRows(table, 6, 7, 8);
  }

  @Test
  public void should_Pass_If_JTable_Has_Expected_Selected_Rows_In_Different_Order() {
    selectRows(6, 8);
    driver.requireSelectedRows(table, 8, 7, 6);
  }

  @Test
  public void should_Pass_If_Expected_Selected_Rows_Are_Subset_Of_All_Selected_Rows() {
    selectRows(6, 8);
    driver.requireSelectedRows(table, 6, 7);
  }

  @RunsInEDT
  private void selectRows(int from, int to) {
    selectRows(table, from, to);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void selectRows(final JTable table, final int from, final int to) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        if (from != to) {
          table.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
        }
        table.setRowSelectionInterval(from, to);
      }
    });
  }
}
