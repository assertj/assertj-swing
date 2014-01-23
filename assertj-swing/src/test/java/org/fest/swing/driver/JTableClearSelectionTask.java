package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JTable;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;

/**
 * Clears the selection in a {@code JTable}. This task is executed in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 */
final class JTableClearSelectionTask {
  @RunsInEDT
  static void clearSelectionOf(final JTable table) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        table.clearSelection();
      }
    });
  }

  private JTableClearSelectionTask() {}
}