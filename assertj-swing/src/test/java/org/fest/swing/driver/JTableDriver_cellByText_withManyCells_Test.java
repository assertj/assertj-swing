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
import static org.fest.swing.test.swing.TestTable.createCellValueFrom;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import org.fest.swing.data.TableCell;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JTableDriver#cell(javax.swing.JTable, String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class JTableDriver_cellByText_withManyCells_Test extends JTableDriver_TestCase {
  private final int row;
  private final int column;

  @Parameters
  public static Collection<Object[]> cells() {
    return newArrayList(tableCells());
  }

  public JTableDriver_cellByText_withManyCells_Test(int row, int column) {
    this.row = row;
    this.column = column;
  }

  @Test
  public void should_return_cell_with_given_text() {
    String value = createCellValueFrom(row, column);
    TableCell cell = driver.cell(table, value);
    assertThat(cell.row).isEqualTo(row);
    assertThat(cell.column).isEqualTo(column);
    assertThatCellReaderWasCalled();
  }
}
