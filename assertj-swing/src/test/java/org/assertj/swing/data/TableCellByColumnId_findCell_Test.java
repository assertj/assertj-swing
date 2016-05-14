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
package org.assertj.swing.data;

import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.data.TableCellByColumnId.row;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link TableCellByColumnId#findCell(javax.swing.JTable, JTableCellReader)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class TableCellByColumnId_findCell_Test extends TableCellFinder_TestCase {
  private final String columnId;

  @Parameters
  public static Collection<Object[]> ids() {
    Object[][] ids = new Object[][] { { "0" }, { "1" }, { "2" }, { "3" } };
    return newArrayList(ids);
  }

  public TableCellByColumnId_findCell_Test(String columnId) {
    this.columnId = columnId;
  }

  @Test
  public void should_Find_Cell_By_Column_Id() {
    TableCellByColumnId finder = row(0).columnId(columnId);
    TableCell cell = finder.findCell(table, null);
    assertThat(cell.row).isEqualTo(0);
    assertThat(cell.column).isEqualTo(parseInt(columnId));
  }
}
