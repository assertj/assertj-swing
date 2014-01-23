/*
 * Created on Aug 10, 2008
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
import static org.fest.swing.driver.JTableClearSelectionTask.clearSelectionOf;
import static org.fest.swing.edt.GuiActionRunner.execute;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestTable;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JTableHasSelectionQuery#hasSelection(javax.swing.JTable)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableHasSelectionQuery_hasSelection_Test extends RobotBasedTestCase {
  private TestTable table;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    table = window.table;
  }

  @Test
  public void should_return_false_if_JTable_does_not_have_selection() {
    clearSelectionOf(table);
    robot.waitForIdle();
    assertThat(hasSelection(table)).isFalse();
  }

  @Test
  public void should_return_true_if_JTable_has_selection() {
    selectAllIn(table);
    robot.waitForIdle();
    assertThat(hasSelection(table)).isTrue();
  }

  @RunsInEDT
  private static void selectAllIn(final TestTable table) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        table.selectAll();
      }
    });
  }

  @RunsInEDT
  private static boolean hasSelection(final TestTable table) {
    return execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return JTableHasSelectionQuery.hasSelection(table);
      }
    });
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

    final TestTable table = new TestTable(2, 4);

    private MyWindow() {
      super(JTableHasSelectionQuery_hasSelection_Test.class);
      addComponents(table);
    }
  }
}
