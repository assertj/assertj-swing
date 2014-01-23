/*
 * Created on May 22, 2009
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.test.ExpectedException.none;

import org.fest.test.ExpectedException;
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
  public void should_throw_error_if_row_index_is_out_of_bounds() {
    thrown.expect(IndexOutOfBoundsException.class, "row <8> should be between <0> and <4>");
    JTableCancelCellEditingTask.cancelEditing(window.table, 8, 2);
  }

  @Test
  public void should_throw_error_if_column_index_is_out_of_bounds() {
    thrown.expect(IndexOutOfBoundsException.class, "column <8> should be between <0> and <1>");
    JTableCancelCellEditingTask.cancelEditing(window.table, 0, 8);
  }

  @Test
  public void should_throw_error_if_cell_is_not_editable() {
    thrown.expect(IllegalStateException.class, "Expecting cell [0, 0] to be editable");
    JTableCancelCellEditingTask.cancelEditing(window.table, 0, 0);
  }

  @Test
  public void should_cancel_cell_editing() {
    editTableCellAt(0, 1);
    JTableCancelCellEditingTask.cancelEditing(window.table, 0, 1);
    robot.waitForIdle();
    assertThat(isTableEditing()).isFalse();
    MyCellEditor cellEditor = window.table.cellEditor();
    assertThat(cellEditor.cellEditingCanceled()).isTrue();
    assertThat(cellEditor.cellEditingStopped()).isFalse();
  }
}
