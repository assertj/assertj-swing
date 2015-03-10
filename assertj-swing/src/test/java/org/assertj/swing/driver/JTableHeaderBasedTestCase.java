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

import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestTable;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;

/**
 * Base test case for classes that handle {@code JTableHeader}s.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class JTableHeaderBasedTestCase extends RobotBasedTestCase {
  MyWindow window;
  JTableHeader tableHeader;

  @Rule
  public ExpectedException thrown = none();

  @Override
  protected final void onSetUp() {
    setUpTestTarget();
    window = MyWindow.createNew(getClass());
    tableHeader = window.table.getTableHeader();
    extraSetUp();
  }

  abstract void setUpTestTarget();

  void extraSetUp() {
  }

  static Object[][] columnIndices() {
    return new Object[][] { { 0 }, { 1 } };
  }

  final void showWindow() {
    robot.showWindow(window);
  }

  final String columnNameFromIndex(int index) {
    return String.valueOf(index);
  }

  static class MyWindow extends TestWindow {
    private static final Dimension TABLE_SIZE = new Dimension(400, 200);

    final TestTable table;

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
      table = new TestTable(6, 2);
      add(decorate(table));
    }

    private static Component decorate(JTable table) {
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setPreferredSize(TABLE_SIZE);
      return scrollPane;
    }
  }
}
