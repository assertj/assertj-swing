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

import static org.assertj.swing.data.TableCellByColumnId.row;

import org.assertj.swing.cell.JTableCellReader;
import org.assertj.swing.exception.ActionFailedException;
import org.junit.Test;

/**
 * Tests for {@link TableCellByColumnId#findCell(javax.swing.JTable, JTableCellReader)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class TableCellByColumnId_findCell_withInvalidInput_Test extends TableCellFinder_TestCase {
  @Test(expected = ActionFailedException.class)
  public void should_Throw_Error_If_A_Matching_Column_Was_Not_Found() {
    TableCellByColumnId finder = row(0).columnId("Hello");
    finder.findCell(table, null);
  }
}
