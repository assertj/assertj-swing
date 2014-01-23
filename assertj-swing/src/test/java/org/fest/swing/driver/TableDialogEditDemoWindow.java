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

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JTable;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TableDialogEditDemo;
import org.fest.swing.test.swing.TestWindow;

/**
 * A frame hosting {@link TableDialogEditDemo}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class TableDialogEditDemoWindow extends TestWindow {
  final JTable table;

  @RunsInEDT
  public static TableDialogEditDemoWindow createNew(final Class<?> testClass) {
    return execute(new GuiQuery<TableDialogEditDemoWindow>() {
      @Override
      protected TableDialogEditDemoWindow executeInEDT() {
        return new TableDialogEditDemoWindow(testClass);
      }
    });
  }

  private TableDialogEditDemoWindow(Class<?> testClass) {
    super(testClass);
    TableDialogEditDemo newContentPane = new TableDialogEditDemo();
    table = newContentPane.table;
    newContentPane.setOpaque(true); // content panes must be opaque
    setContentPane(newContentPane);
  }
}