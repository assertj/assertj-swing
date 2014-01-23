package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.swing.JComboBox;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;

/**
 * Sets a {@code JComboBox} editable. This task is executed in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JComboBoxSetEditableTask {
  @RunsInEDT
  static void setEditable(final @Nonnull JComboBox comboBox, final boolean editable) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        comboBox.setEditable(editable);
      }
    });
  }

  private JComboBoxSetEditableTask() {}
}