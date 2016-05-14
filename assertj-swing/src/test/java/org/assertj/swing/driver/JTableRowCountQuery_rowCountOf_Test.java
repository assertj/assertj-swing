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
import static org.assertj.swing.edt.GuiActionRunner.execute;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestTable;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JTableRowCountQuery#rowCountOf(javax.swing.JTable)}.
 * 
 * @author Alex Ruiz
 */
public class JTableRowCountQuery_rowCountOf_Test extends RobotBasedTestCase {
  private static final int ROW_COUNT = 2;

  private MyTable table;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    table = window.table;
  }

  @Test
  public void should_Return_JTable_Column_Count() {
    table.startRecording();
    assertThat(JTableRowCountQuery.rowCountOf(table)).isEqualTo(ROW_COUNT);
    table.requireInvoked("getRowCount");
  }

  private static class MyWindow extends TestWindow {
    final MyTable table = new MyTable();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    private MyWindow() {
      super(JTableRowCountQuery_rowCountOf_Test.class);
      addComponents(table);
    }
  }

  private static class MyTable extends TestTable {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyTable() {
      super(ROW_COUNT, 6);
    }

    @Override
    public int getRowCount() {
      if (recording) {
        methodInvocations.invoked("getRowCount");
      }
      return super.getRowCount();
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
