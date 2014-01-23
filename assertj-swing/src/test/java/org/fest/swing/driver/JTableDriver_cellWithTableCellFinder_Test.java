/*
 * Created on Feb 25, 2008
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
import static org.fest.swing.data.TableCell.row;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fest.swing.data.TableCell;
import org.fest.swing.data.TableCellFinder;
import org.junit.Test;

/**
 * Tests for {@link JTableDriver#cell(javax.swing.JTable, org.fest.swing.data.TableCellFinder)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableDriver_cellWithTableCellFinder_Test extends JTableDriver_TestCase {
  private TableCellFinder cellFinder;

  @Override
  void extraSetUp() {
    cellFinder = mock(TableCellFinder.class);
  }

  @Test
  public void should_use_TableCellFinder_to_find_a_cell() {
    TableCell cell = row(0).column(0);
    when(cellFinder.findCell(table, driver.cellReader())).thenReturn(cell);
    TableCell found = driver.cell(table, cellFinder);
    assertThat(found).isSameAs(cell);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void should_throw_error_if_indices_in_found_cell_are_out_of_bounds() {
    TableCell cell = row(-1).column(0);
    when(cellFinder.findCell(table, driver.cellReader())).thenReturn(cell);
    driver.cell(table, cellFinder);
  }
}
