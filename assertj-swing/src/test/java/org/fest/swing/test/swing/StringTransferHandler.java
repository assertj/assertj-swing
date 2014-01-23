/*
 * Created on Aug 22, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.test.swing;

import static java.awt.datatransfer.DataFlavor.stringFlavor;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * Imports and exports {@code String}s. Adapted from the tutorial <a
 * href="http://java.sun.com/docs/books/tutorial/uiswing/dnd/intro.html" target="_blank">Introduction to Drag and Drop
 * and Data Transfer</a>.
 *
 * @param <T> specifies the type of component that this class can handle
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
abstract class StringTransferHandler<T extends JComponent> extends TransferHandler {
  private static final int[] DEFAULT_ROWS = null;
  private static final int DEFAULT_ADD_INDEX = -1;
  private static final int DEFAULT_ADD_COUNT = 0;

  protected int[] rows;
  protected int addIndex; // Location where items were added
  protected int addCount; // Number of items added.

  private final Class<T> type;

  StringTransferHandler(Class<T> type) {
    this.type = type;
    reset();
  }

  @Override
  protected Transferable createTransferable(JComponent c) {
    return new StringSelection(exportString(type.cast(c)));
  }

  @Override
  public int getSourceActions(JComponent c) {
    return COPY_OR_MOVE;
  }

  @Override
  public boolean importData(JComponent c, Transferable t) {
    if (!canImport(c, t.getTransferDataFlavors())) {
      return false;
    }
    try {
      String str = (String) t.getTransferData(stringFlavor);
      importString(type.cast(c), str);
      return true;
    } catch (Exception ignored) {
    }
    return false;
  }

  @Override
  protected void exportDone(JComponent c, Transferable data, int action) {
    cleanup(type.cast(c), action == MOVE);
    reset();
  }

  private void reset() {
    rows = DEFAULT_ROWS;
    addIndex = DEFAULT_ADD_INDEX;
    addCount = DEFAULT_ADD_COUNT;
  }

  @Override
  public boolean canImport(JComponent c, DataFlavor[] flavors) {
    for (DataFlavor flavor : flavors) {
      if (stringFlavor.equals(flavor)) {
        return true;
      }
    }
    return false;
  }

  protected abstract void importString(T c, String s);

  protected abstract String exportString(T c);

  protected abstract void cleanup(T c, boolean remove);
}
