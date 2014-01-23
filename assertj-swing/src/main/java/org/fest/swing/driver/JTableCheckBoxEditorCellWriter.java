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

import static java.lang.Boolean.parseBoolean;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Point;

import javax.annotation.Nonnull;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.util.Pair;

/**
 * {@link JTableCellWriter} that knows how to use {@code JTextComponent}s as cell editors.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableCheckBoxEditorCellWriter extends AbstractJTableCellWriter {
  public JTableCheckBoxEditorCellWriter(@Nonnull Robot robot) {
    super(robot);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public void enterValue(@Nonnull JTable table, int row, int column, @Nonnull String value) {
    boolean realValue = parseBoolean(value);
    Pair<Boolean, Point> editingInfo = doStartCellEditing(table, row, column, location());
    if (editingInfo.first == realValue) {
      return; // JCheckBox already has value to set.
    }
    robot.click(table, checkNotNull(editingInfo.second));
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public void startCellEditing(@Nonnull JTable table, int row, int column) {
    doStartCellEditing(table, row, column, location());
  }

  @RunsInEDT
  private static @Nonnull Pair<Boolean, Point> doStartCellEditing(final @Nonnull JTable table, final int row, final int column,
      final @Nonnull JTableLocation location) {
    Pair<Boolean, Point> result = execute(new GuiQuery<Pair<Boolean, Point>>() {
      @Override
      protected Pair<Boolean, Point> executeInEDT() {
        JCheckBox editor = editor(table, row, column, JCheckBox.class);
        scrollToCell(table, row, column, location);
        return Pair.of(editor.isSelected(), location.pointAt(table, row, column));
      }
    });
    return checkNotNull(result);
  }
}
