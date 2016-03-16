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

import org.assertj.swing.data.TableCell;
import org.junit.Test;

/**
 * Tests for {@link JTableDriver#unselectCells(JTable, TableCell[])}.
 *
 * @author Christian Rösch
 */
public class JTableDriver_unselectCells_withInvalidInput_Test extends JTableDriver_withMocks_TestCase {
  @Test(expected = NullPointerException.class)
  public void shouldThrowErrorIfArrayOfCellsToSelectIsNull() {
    TableCell[] cells = null;
    driver.unselectCells(table, cells);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowErrorIfArrayOfCellsToSelectIsEmpty() {
    TableCell[] cells = new TableCell[0];
    driver.unselectCells(table, cells);
  }
}
