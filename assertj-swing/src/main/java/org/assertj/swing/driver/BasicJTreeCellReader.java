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
package org.assertj.swing.driver;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.util.Strings.isDefaultToString;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.cell.JTreeCellReader;

/**
 * Default implementation of {@link JTreeCellReader}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicJTreeCellReader implements JTreeCellReader {
  private final CellRendererReader rendererReader;

  /**
   * Creates a new {@link BasicJTreeCellReader} that uses a {@link BasicCellRendererReader} to read the value from the
   * cell renderer component in a {@code JTree}.
   */
  public BasicJTreeCellReader() {
    this(new BasicCellRendererReader());
  }

  /**
   * Creates a new {@link BasicJTreeCellReader}.
   * 
   * @param reader knows how to read values from the cell renderer component in a {@code JTree}.
   * @throws NullPointerException if {@code reader} is {@code null}.
   */
  public BasicJTreeCellReader(@Nonnull CellRendererReader reader) {
    this.rendererReader = checkNotNull(reader);
  }

  /**
   * <p>
   * Returns the internal value of a cell in a {@code JTree} as expected in a test.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param tree the given {@code JTree}.
   * @param modelValue the value of a cell, retrieved from the model.
   * @return the internal value of a cell in a {@code JTree} as expected in a test.
   */
  @Override
  @RunsInCurrentThread
  public @Nullable String valueAt(@Nonnull JTree tree, @Nullable Object modelValue) {
    TreeCellRenderer r = tree.getCellRenderer();
    Component c = r.getTreeCellRendererComponent(tree, modelValue, false, false, false, 0, false);
    String value = (c != null) ? rendererReader.valueFrom(c) : null;
    if (value != null) {
      return value;
    }
    value = tree.convertValueToText(modelValue, false, false, false, 0, false);
    if (isDefaultToString(value)) {
      return null;
    }
    return value;
  }
}
