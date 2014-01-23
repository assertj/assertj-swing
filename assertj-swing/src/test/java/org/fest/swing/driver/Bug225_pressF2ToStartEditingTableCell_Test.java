/*
 * Created on Nov 29, 2008
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
import static org.fest.swing.driver.JTableCellValueQuery.cellValueOf;

import java.awt.Dimension;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.junit.Test;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=225" target="_blank">Bug 225</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug225_pressF2ToStartEditingTableCell_Test extends RobotBasedTestCase {
  private JTableTextComponentEditorCellWriter writer;
  private TableDialogEditDemoWindow window;

  @Override
  protected void onSetUp() {
    writer = new JTableTextComponentEditorCellWriter(robot);
    window = TableDialogEditDemoWindow.createNew(getClass());
    robot.showWindow(window, new Dimension(500, 100));
  }

  @Test
  public void should_edit_cell() {
    int row = 4;
    int col = 3;
    writer.enterValue(window.table, row, col, "8");
    assertThat(valueAt(row, col)).isEqualTo(8);
  }

  @RunsInEDT
  private Object valueAt(int row, int column) {
    return cellValueOf(window.table, row, column);
  }
}
