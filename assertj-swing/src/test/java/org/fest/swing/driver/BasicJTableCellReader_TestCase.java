/*
 * Created on Apr 12, 2008
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

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.CustomCellRenderer;
import org.fest.swing.test.swing.TestWindow;

/**
 * Base test case for {@link BasicJTableCellReader}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJTableCellReader_TestCase extends RobotBasedTestCase {
  JTable table;
  BasicJTableCellReader reader;

  @Override
  protected final void onSetUp() {
    MyWindow window = MyWindow.createNew(getClass());
    table = window.table;
    reader = new BasicJTableCellReader();
  }

  @RunsInEDT
  final JLabel setJLabelAsCellRenderer() {
    JLabel label = setJLabelAsCellRendererOf(table);
    robot.waitForIdle();
    return label;
  }

  @RunsInEDT
  private static JLabel setJLabelAsCellRendererOf(final JTable table) {
    return execute(new GuiQuery<JLabel>() {
      @Override
      protected JLabel executeInEDT() {
        JLabel label = new JLabel("Hello");
        label.setBackground(BLUE);
        label.setForeground(WHITE);
        setCellRendererComponent(table, label);
        return label;
      }
    });
  }

  @RunsInCurrentThread
  private static void setCellRendererComponent(JTable table, Component renderer) {
    CustomCellRenderer cellRenderer = new CustomCellRenderer(renderer);
    table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
  }

  static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    final JTable table = new JTable(1, 1);

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(table);
    }
  }
}
