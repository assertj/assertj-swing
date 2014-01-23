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

import static org.fest.swing.driver.JComboBoxEditableQuery.isEditable;
import static org.fest.swing.driver.JTableStopCellEditingTask.stopEditing;
import static org.fest.util.Lists.newArrayList;

import java.awt.Point;

import javax.annotation.Nonnull;
import javax.swing.JComboBox;
import javax.swing.JTable;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.Robot;

/**
 * {@link JTableCellWriter} that knows how to use {@code JComboBox}es as cell editors.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableComboBoxEditorCellWriter extends AbstractJTableCellWriter {
  private final JComboBoxDriver driver;

  public JTableComboBoxEditorCellWriter(@Nonnull Robot robot) {
    super(robot);
    driver = new JComboBoxDriver(robot);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public void enterValue(@Nonnull JTable table, int row, int column, @Nonnull String value) {
    JComboBox editor = doStartCellEditing(table, row, column);
    selectOrType(editor, value);
    stopEditing(table, row, column);
  }

  private void selectOrType(@Nonnull JComboBox editor, @Nonnull String value) {
    boolean selectValue = !isEditable(editor);
    if (!selectValue) {
      selectValue = newArrayList(driver.contentsOf(editor)).contains(value);
    }
    if (selectValue) {
      driver.selectItem(editor, value);
      return;
    }
    driver.enterText(editor, value);
  }

  /** {@inheritDoc} */
  @Override
  @RunsInEDT
  public void startCellEditing(@Nonnull JTable table, int row, int column) {
    doStartCellEditing(table, row, column);
  }

  @RunsInEDT
  private JComboBox doStartCellEditing(@Nonnull JTable table, int row, int column) {
    Point cellLocation = cellLocation(table, row, column, location());
    robot.click(table, cellLocation); // activate JComboBox editor
    JComboBox comboBox = waitForEditorActivation(table, row, column);
    cellEditor(cellEditor(table, row, column));
    return comboBox;
  }

  @RunsInEDT
  private JComboBox waitForEditorActivation(@Nonnull JTable table, int row, int column) {
    return waitForEditorActivation(table, row, column, JComboBox.class);
  }
}
