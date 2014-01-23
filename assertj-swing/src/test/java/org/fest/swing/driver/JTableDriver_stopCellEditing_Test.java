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

import static org.fest.swing.data.TableCell.row;
import static org.mockito.Mockito.verify;

import javax.swing.JTable;

import org.fest.swing.data.TableCell;
import org.junit.Test;

/**
 * Tests for {@link JTableDriver#stopCellEditing(JTable, TableCell)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableDriver_stopCellEditing_Test extends JTableDriver_withMockCellWriter_TestCase {
  @Test
  public void should_stop_cell_editing() {
    driver.stopCellEditing(table, row(0).column(0));
    verify(cellWriter).stopCellEditing(table, 0, 0);
  }
}
