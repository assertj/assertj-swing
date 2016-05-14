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

import static org.assertj.swing.data.TableCell.row;
import static org.mockito.Mockito.verify;

import org.junit.Test;

/**
 * Tests for {@link JTableDriver#enterValueInCell(JTable, TableCell, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableDriver_enterValueInCell_Test extends JTableDriver_withMockCellWriter_TestCase {
  private String value;

  @Override
  void onSetUp() {
    value = "Hello";
  }

  @Test
  public void should_Enter_Value_In_Cell() {
    driver.enterValueInCell(table, row(0).column(0), value);
    verify(cellWriter).enterValue(table, 0, 0, value);
  }
}
