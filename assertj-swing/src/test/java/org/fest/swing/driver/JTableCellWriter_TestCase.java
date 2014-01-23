/*
 * Created on Jun 10, 2008
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

import javax.swing.JTable;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.test.core.RobotBasedTestCase;

/**
 * Test case for implementations of {@link JTableCellWriter}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class JTableCellWriter_TestCase extends RobotBasedTestCase {
  TableDialogEditDemoWindow window;
  JTable table;
  JTableCellWriter writer;

  @Override
  protected final void onSetUp() {
    writer = createWriter();
    window = TableDialogEditDemoWindow.createNew(getClass());
    table = window.table;
    robot.showWindow(window, new Dimension(500, 100));
  }

  abstract JTableCellWriter createWriter();

  final void assertMessageIndicatesWriterWasUnableToActivateEditor(ActionFailedException e) {
    assertThat(e.getMessage()).contains("Unable to find or activate editor");
  }

  @RunsInEDT
  final Object valueAt(int row, int column) {
    return cellValueOf(window.table, row, column);
  }
}
