/*
 * Created on Aug 6, 2008
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
import static org.fest.util.Lists.newArrayList;

import java.awt.Component;
import java.util.Collection;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TableRenderDemo;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JTableCellEditorQuery#cellEditorIn(JTable, int, int)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class JTableCellEditorQuery_cellEditorIn_Test extends RobotBasedTestCase {
  private JTable table;

  private final int column;
  private final Class<?> editorType;

  @Parameters
  public static Collection<Object[]> editorTypes() {
    return newArrayList(new Object[][] {
        { 2, JComboBox.class },
        { 3, JTextComponent.class },
        { 4, JCheckBox.class }
      });
  }

  public JTableCellEditorQuery_cellEditorIn_Test(int column, Class<?> editorType) {
    this.column = column;
    this.editorType = editorType;
  }

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    table = window.table;
  }

  @Test
  public void should_return_editor_component_from_JTable_cell() {
    int row = 0;
    Component editor = cellEditorIn(table, row, column);
    assertThat(editor).isInstanceOf(editorType);
  }

  @RunsInEDT
  private static Component cellEditorIn(final JTable table, final int row, final int column) {
    return execute(new GuiQuery<Component>() {
      @Override
      protected Component executeInEDT() {
        return JTableCellEditorQuery.cellEditorIn(table, row, column);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    final JTable table;

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
      super(JTableCellEditorQuery_cellEditorIn_Test.class);
      TableRenderDemo newContentPane = new TableRenderDemo();
      newContentPane.setOpaque(true); // content panes must be opaque
      setContentPane(newContentPane);
      table = newContentPane.table;
    }
  }
}
