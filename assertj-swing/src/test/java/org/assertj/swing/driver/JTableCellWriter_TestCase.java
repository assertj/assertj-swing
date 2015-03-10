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

import static org.assertj.swing.driver.JTableCellValueQuery.cellValueOf;
import static org.assertj.swing.test.ExpectedException.none;

import java.awt.Dimension;

import javax.swing.JTable;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.cell.JTableCellWriter;
import org.assertj.swing.exception.ActionFailedException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.junit.Rule;

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

  @Rule
  public ExpectedException thrown = none();

  @Override
  protected final void onSetUp() {
    writer = createWriter();
    window = TableDialogEditDemoWindow.createNew(getClass());
    table = window.table;
    robot.showWindow(window, new Dimension(500, 100));
  }

  abstract JTableCellWriter createWriter();

  final void assertActionFailedExceptionWithMessageIndicatingWriterWasUnableToActivateEditor() {
    thrown.expect(ActionFailedException.class, "Unable to find or activate editor");
  }

  @RunsInEDT
  final Object valueAt(int row, int column) {
    return cellValueOf(window.table, row, column);
  }
}
