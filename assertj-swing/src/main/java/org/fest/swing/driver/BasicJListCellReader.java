/*
 * Created on Apr 12, 2008
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

import static org.fest.swing.driver.ModelValueToString.asText;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JList;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.cell.JListCellReader;

/**
 * Default implementation of {@link JListCellReader}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJListCellReader implements JListCellReader {
  private final CellRendererReader rendererReader;

  /**
   * Creates a new {@link BasicJListCellReader} that uses a {@link BasicCellRendererReader} to read the value from the
   * cell renderer component in a {@code JList}.
   */
  public BasicJListCellReader() {
    this(new BasicCellRendererReader());
  }

  /**
   * Creates a new {@link BasicJListCellReader}.
   * 
   * @param rendererReader knows how to read values from the cell renderer component in a {@code JList}.
   * @throws NullPointerException if {@code rendererReader} is {@code null}.
   */
  public BasicJListCellReader(@Nonnull CellRendererReader rendererReader) {
    this.rendererReader = checkNotNull(rendererReader);
  }

  /**
   * <p>
   * Returns the internal value of a cell in a {@code JList} as expected in a test.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param list the given {@code JList}.
   * @param index the index of the cell.
   * @return the internal value of a cell in a {@code JList} as expected in a test.
   * @see CellRendererReader#valueFrom(Component)
   */
  @Override
  @RunsInCurrentThread
  public @Nullable String valueAt(@Nonnull JList list, int index) {
    Object element = list.getModel().getElementAt(index);
    Component c = list.getCellRenderer().getListCellRendererComponent(list, element, index, true, true);
    String value = (c != null) ? rendererReader.valueFrom(c) : null;
    if (value != null) {
      return value;
    }
    return asText(element);
  }
}
