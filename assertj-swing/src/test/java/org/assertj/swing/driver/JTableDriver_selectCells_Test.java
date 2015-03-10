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

import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.data.TableCell.row;

import org.junit.Test;

/**
 * Tests for {@link JTableDriver#selectCells(javax.swing.JTable, org.assertj.swing.data.TableCell[])}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableDriver_selectCells_Test extends JTableDriver_TestCase {
  @Test
  public void should_Select_Cells() {
    enableMultipleSelection();
    showWindow();
    driver.selectCells(table, array(row(0).column(0), row(2).column(0)));
    requireCellSelected(0, 0);
    requireCellSelected(2, 0);
  }

  @Test
  public void should_Select_Cell() {
    enableMultipleSelection();
    showWindow();
    driver.selectCells(table, array(row(0).column(0)));
    requireCellSelected(0, 0);
  }

  @Test
  public void should_Throw_Error_If_JTable_Is_Disabled() {
    disableTable();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.selectCells(table, array(row(0).column(0), row(2).column(0)));
  }

  @Test
  public void should_Throw_Error_If_JTable_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.selectCells(table, array(row(0).column(0), row(2).column(0)));
  }
}
