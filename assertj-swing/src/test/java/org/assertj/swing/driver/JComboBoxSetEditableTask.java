package org.assertj.swing.driver;

import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.swing.JComboBox;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;

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

  private JComboBoxSetEditableTask() {
  }
}