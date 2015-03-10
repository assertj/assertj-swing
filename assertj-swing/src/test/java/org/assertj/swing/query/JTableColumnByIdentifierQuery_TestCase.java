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
package org.assertj.swing.query;

import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.core.MethodInvocations.Args.args;

import javax.swing.table.TableColumn;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.MethodInvocations.Args;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestTable;
import org.assertj.swing.test.swing.TestWindow;

/**
 * Base test case for {@link JTableColumnByIdentifierQuery}.
 * 
 * @author Alex Ruiz
 */
public abstract class JTableColumnByIdentifierQuery_TestCase extends RobotBasedTestCase {
  MyTable table;

  @Override
  protected final void onSetUp() {
    MyWindow window = MyWindow.createNew(getClass());
    table = window.table;
  }

  @RunsInEDT
  final int columnIndexByIdentifier(String identifier) {
    return columnIndexByIdentifier(table, identifier);
  }

  @RunsInEDT
  private static int columnIndexByIdentifier(final MyTable table, final String identifier) {
    return execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return JTableColumnByIdentifierQuery.columnIndexByIdentifier(table, identifier);
      }
    });
  }

  static class MyWindow extends TestWindow {
    final MyTable table = new MyTable();

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(table);
    }
  }

  static class MyTable extends TestTable {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyTable() {
      super(2, 6);
    }

    @Override
    public TableColumn getColumn(Object identifier) {
      if (recording) {
        methodInvocations.invoked("getColumn", args(identifier));
      }
      return super.getColumn(identifier);
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName, Args args) {
      return methodInvocations.requireInvoked(methodName, args);
    }
  }
}
