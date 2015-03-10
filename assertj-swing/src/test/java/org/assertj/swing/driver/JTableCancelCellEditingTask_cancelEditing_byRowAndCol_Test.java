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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.test.ExpectedException.none;

import org.assertj.swing.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link JTableCancelCellEditingTask#cancelEditing(javax.swing.JTable, int, int)}.
 * 
 * @author Alex Ruiz
 */
public class JTableCancelCellEditingTask_cancelEditing_byRowAndCol_Test extends JTableCellEditingTask_TestCase {
  @Rule
  public ExpectedException thrown = none();

  @Test
  public void should_Throw_Error_If_Row_Index_Is_Out_Of_Bounds() {
    thrown.expect(IndexOutOfBoundsException.class, "row <8> should be between <0> and <4>");
    JTableCancelCellEditingTask.cancelEditing(window.table, 8, 2);
  }

  @Test
  public void should_Throw_Error_If_Column_Index_Is_Out_Of_Bounds() {
    thrown.expect(IndexOutOfBoundsException.class, "column <8> should be between <0> and <1>");
    JTableCancelCellEditingTask.cancelEditing(window.table, 0, 8);
  }

  @Test
  public void should_Throw_Error_If_Cell_Is_Not_Editable() {
    thrown.expect(IllegalStateException.class, "Expecting cell [0, 0] to be editable");
    JTableCancelCellEditingTask.cancelEditing(window.table, 0, 0);
  }

  @Test
  public void should_Cancel_Cell_Editing() {
    editTableCellAt(0, 1);
    JTableCancelCellEditingTask.cancelEditing(window.table, 0, 1);
    robot.waitForIdle();
    assertThat(isTableEditing()).isFalse();
    MyCellEditor cellEditor = window.table.cellEditor();
    assertThat(cellEditor.cellEditingCanceled()).isTrue();
    assertThat(cellEditor.cellEditingStopped()).isFalse();
  }
}
