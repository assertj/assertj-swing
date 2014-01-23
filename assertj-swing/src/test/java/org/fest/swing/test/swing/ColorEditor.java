/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met: - Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution. - Neither the name of Sun Microsystems nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.fest.swing.test.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * Adapted from the <a href="http://java.sun.com/docs/books/tutorial/uiswing/" target="_blank">Swing Tutorial</a>.
 */
class ColorEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
  Color currentColor;
  JButton button;
  JColorChooser colorChooser;
  JDialog dialog;
  protected static final String EDIT = "edit";

  ColorEditor() {
    // Set up the editor (from the table's point of view),
    // which is a button.
    // This button brings up the color chooser dialog,
    // which is the editor from the user's point of view.
    button = new JButton();
    button.setActionCommand(EDIT);
    button.addActionListener(this);
    button.setBorderPainted(false);

    // Set up the dialog that the button brings up.
    colorChooser = new JColorChooser();
    dialog = JColorChooser.createDialog(button, "Pick a Color", true, colorChooser, this, null);
  }

  /**
   * Handles events from the editor button and from the dialog's OK button.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (EDIT.equals(e.getActionCommand())) {
      // The user has clicked the cell, so
      // bring up the dialog.
      button.setBackground(currentColor);
      colorChooser.setColor(currentColor);
      dialog.setVisible(true);

      // Make the renderer reappear.
      fireEditingStopped();

    } else { // User pressed dialog's "OK" button.
      currentColor = colorChooser.getColor();
    }
  }

  // Implement the one CellEditor method that AbstractCellEditor doesn't.
  @Override
  public Object getCellEditorValue() {
    return currentColor;
  }

  // Implement the one method defined by TableCellEditor.
  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    currentColor = (Color) value;
    return button;
  }
}