/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.test.swing;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 * Adapted from the <a href="http://java.sun.com/docs/books/tutorial/uiswing/" target="_blank">Swing Tutorial</a>.
 */
class ColorRenderer extends JLabel implements TableCellRenderer {
  Border unselectedBorder = null;
  Border selectedBorder = null;
  boolean isBordered = true;

  ColorRenderer(boolean isBordered) {
    this.isBordered = isBordered;
    setOpaque(true); // MUST do this for background to show up.
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus,
      int row, int column) {
    Color newColor = (Color) color;
    setBackground(newColor);
    if (isBordered) {
      if (isSelected) {
        if (selectedBorder == null) {
          selectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground());
        }
        setBorder(selectedBorder);
      } else {
        if (unselectedBorder == null) {
          unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground());
        }
        setBorder(unselectedBorder);
      }
    }

    setToolTipText("RGB value: " + newColor.getRed() + ", " + newColor.getGreen() + ", " + newColor.getBlue());
    return this;
  }
}