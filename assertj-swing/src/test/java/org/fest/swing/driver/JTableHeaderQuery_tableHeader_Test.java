/*
 * Created on Sep 2, 2008
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
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.table.JTableHeader;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestTable;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JTableHeaderQuery#tableHeader(javax.swing.JTable)}.
 * 
 * @author Alex Ruiz
 */
public class JTableHeaderQuery_tableHeader_Test extends RobotBasedTestCase {
  private MyTable table;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    table = window.table;
  }

  @Test
  public void should_return_JTableHeader_in_JTable() {
    JTableHeader expected = table.getTableHeader();
    table.startRecording();
    assertThat(JTableHeaderQuery.tableHeader(table)).isSameAs(expected);
    table.requireInvoked("getTableHeader");
  }

  private static class MyWindow extends TestWindow {
    final MyTable table = new MyTable();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JTableHeaderQuery_tableHeader_Test.class);
      addComponents(table);
    }
  }

  private static class MyTable extends TestTable {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyTable() {
      super(2, 6);
    }

    @Override
    public JTableHeader getTableHeader() {
      if (recording) {
        methodInvocations.invoked("getTableHeader");
      }
      return super.getTableHeader();
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
