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

import org.assertj.swing.cell.JTableCellWriter;
import org.junit.Test;

/**
 * Test case for implementations of {@link JTableCellWriter}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class JTableCellWriter_startCellEditing_TestCase extends JTableCellWriter_TestCase {
  @Test
  public void should_Throw_Error_If_Editor_Component_Cannot_Be_Handled() {
    assertActionFailedExceptionWithMessageIndicatingWriterWasUnableToActivateEditor();
    writer.startCellEditing(window.table, 0, 1);
  }
}
