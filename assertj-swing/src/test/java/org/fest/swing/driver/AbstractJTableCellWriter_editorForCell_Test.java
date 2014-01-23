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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Lists.newArrayList;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.fest.swing.test.core.RobotBasedTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link AbstractJTableCellWriter#editorForCell(javax.swing.JTable, int, int)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class AbstractJTableCellWriter_editorForCell_Test extends RobotBasedTestCase {
  private TableDialogEditDemoWindow frame;
  private AbstractJTableCellWriter writer;

  private final int row;
  private final int column;
  private final Class<Component> editorType;

  @Parameters
  public static Collection<Object[]> cellEditors() {
    return newArrayList(new Object[][] {
        { 0, 2, JComboBox.class },
        { 0, 3, JTextField.class },
        { 0, 4, JCheckBox.class }
      });
  }

  public AbstractJTableCellWriter_editorForCell_Test(int row, int column, Class<Component> editorType) {
    this.row = row;
    this.column = column;
    this.editorType = editorType;
  }

  @Override
  protected void onSetUp() {
    writer = new AbstractJTableCellWriterStub(robot);
    frame = TableDialogEditDemoWindow.createNew(AbstractJTableCellWriter_editorForCell_Test.class);
    robot.showWindow(frame, new Dimension(500, 100));
  }

  @Test
  public void shouldReturnEditorForCell() {
    Component editor = writer.editorForCell(frame.table, row, column);
    assertThat(editor).isNotNull().isInstanceOf(editorType);
  }
}
