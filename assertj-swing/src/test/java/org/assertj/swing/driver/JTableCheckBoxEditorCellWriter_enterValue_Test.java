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

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.swing.cell.JTableCellWriter;
import org.junit.Test;

/**
 * Tests for {@link JTableCheckBoxEditorCellWriter#enterValue(javax.swing.JTable, int, int, String)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JTableCheckBoxEditorCellWriter_enterValue_Test extends JTableCellWriter_enterValue_TestCase {
  @Override
  protected JTableCellWriter createWriter() {
    return new JTableCheckBoxEditorCellWriter(robot);
  }

  @Test
  public void should_Check_In_JCheckBox_Editor() {
    int row = 0;
    int col = 4;
    writer.enterValue(table, row, col, "false");
    assertThat(valueAt(row, col)).isEqualTo(false);
    writer.enterValue(table, row, col, "true");
    assertThat(valueAt(row, col)).isEqualTo(true);
    writer.enterValue(table, row, col, "false");
    assertThat(valueAt(row, col)).isEqualTo(false);
    writer.enterValue(table, row, col, "false");
    assertThat(valueAt(row, col)).isEqualTo(false);
  }
}
