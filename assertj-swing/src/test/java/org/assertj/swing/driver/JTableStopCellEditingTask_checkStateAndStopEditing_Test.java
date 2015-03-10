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

import static org.assertj.swing.test.ExpectedException.none;

import org.assertj.swing.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link JTableStopCellEditingTask#checkStateAndStopEditing(javax.swing.JTable, int, int)}.
 * 
 * @author Alex Ruiz
 */
public class JTableStopCellEditingTask_checkStateAndStopEditing_Test extends JTableCellEditingTask_TestCase {
  @Rule
  public ExpectedException thrown = none();

  @Test
  public void should_Throw_Error_If_Row_Index_Is_Out_Of_Bounds() {
    thrown.expect(IndexOutOfBoundsException.class, "row <8> should be between <0> and <4>");
    JTableStopCellEditingTask.checkStateAndStopEditing(window.table, 8, 2);
  }

  @Test
  public void should_Throw_Error_If_Column_Index_Is_Out_Of_Bounds() {
    thrown.expect(IndexOutOfBoundsException.class, "column <8> should be between <0> and <1>");
    JTableStopCellEditingTask.checkStateAndStopEditing(window.table, 0, 8);
  }

  @Test
  public void should_Throw_Error_If_Cell_Is_Not_Editable() {
    thrown.expect(IllegalStateException.class, "Expecting cell [0, 0] to be editable");
    JTableStopCellEditingTask.checkStateAndStopEditing(window.table, 0, 0);
  }

  @Test
  public void should_Stop_Cell_Editing() {
    int row = 0;
    int col = 1;
    editTableCellAt(row, col);
    JTableStopCellEditingTask.checkStateAndStopEditing(window.table, row, col);
    robot.waitForIdle();
    assertCellEditingStopped();
  }

  @Test
  public void shouldStopCellEditing() {
    int row = 0;
    int col = 1;
    editTableCellAt(row, col);
    JTableStopCellEditingTask.stopEditing(window.table, row, col);
    robot.waitForIdle();
    assertCellEditingStopped();
  }

  @Test
  public void should_Throw_Error_If_CellEditor_Is_Null() {
    thrown.expect(NullPointerException.class);
    JTableStopCellEditingTask.stopEditing(null);
  }
}
