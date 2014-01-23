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

import static java.awt.event.KeyEvent.VK_F2;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Point;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;

/**
 * {@link JTableCellWriter} that knows how to use {@code JTextComponent}s as cell editors.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableTextComponentEditorCellWriter extends AbstractJTableCellWriter {
  protected final JTextComponentDriver driver;

  public JTableTextComponentEditorCellWriter(@Nonnull Robot robot) {
    super(robot);
    driver = new JTextComponentDriver(robot);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public void enterValue(@Nonnull JTable table, int row, int column, @Nonnull String value) {
    JTextComponent editor = doStartCellEditing(table, row, column);
    driver.replaceText(editor, value);
    stopCellEditing(table, row, column);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public void startCellEditing(@Nonnull JTable table, int row, int column) {
    doStartCellEditing(table, row, column);
  }

  @RunsInEDT
  private @Nonnull JTextComponent doStartCellEditing(@Nonnull JTable table, int row, int column) {
    Point cellLocation = cellLocation(table, row, column, location());
    JTextComponent textComponent = null;
    try {
      textComponent = activateEditorWithF2Key(table, row, column, cellLocation);
    } catch (ActionFailedException e) {
      textComponent = activateEditorWithDoubleClick(table, row, column, cellLocation);
    }
    cellEditor(cellEditor(table, row, column));
    return checkNotNull(textComponent);
  }

  @RunsInEDT
  private @Nullable JTextComponent activateEditorWithF2Key(@Nonnull JTable table, int row, int column,
      @Nonnull Point cellLocation) {
    robot.click(table, cellLocation);
    robot.pressAndReleaseKeys(VK_F2);
    return waitForEditorActivation(table, row, column);
  }

  @RunsInEDT
  private @Nullable JTextComponent activateEditorWithDoubleClick(@Nonnull JTable table, int row, int column,
      @Nonnull Point cellLocation) {
    robot.click(table, cellLocation, LEFT_BUTTON, 2);
    return waitForEditorActivation(table, row, column);
  }

  @RunsInEDT
  private @Nullable JTextComponent waitForEditorActivation(@Nonnull JTable table, int row, int column) {
    return waitForEditorActivation(table, row, column, JTextComponent.class);
  }
}
