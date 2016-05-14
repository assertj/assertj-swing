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
import static org.assertj.swing.driver.ModelValueToString.asText;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.cell.JComboBoxCellReader;
import org.assertj.swing.edt.GuiQuery;

/**
 * Default implementation of {@link JComboBoxCellReader}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJComboBoxCellReader implements JComboBoxCellReader {
  private static final JList<?> REFERENCE_JLIST = newJList();

  private static @Nonnull <T> JList<T> newJList() {
    JList<T> result = execute(new GuiQuery<JList<T>>() {
      @Override
      protected JList<T> executeInEDT() {
        return new JList<T>();
      };
    });
    ;
    return checkNotNull(result);
  }

  private final CellRendererReader rendererReader;

  /**
   * Creates a new {@link BasicJComboBoxCellReader} that uses a {@link BasicCellRendererReader} to read the value from
   * the cell renderer {@code Component} in a {@code JComboBox}.
   */
  public BasicJComboBoxCellReader() {
    this(new BasicCellRendererReader());
  }

  /**
   * Creates a new {@link BasicJComboBoxCellReader}.
   *
   * @param rendererReader knows how to read values from the cell renderer {@code Component} in a {@code JComboBox}.
   * @throws NullPointerException if the given {@link CellRendererReader} is {@code null}.
   */
  public BasicJComboBoxCellReader(@Nonnull CellRendererReader rendererReader) {
    this.rendererReader = checkNotNull(rendererReader);
  }

  /**
   * <p>
   * Returns the internal value of a cell in a {@code JComboBox} as expected in a test.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT). Client code must call this method from the EDT.
   * </p>
   *
   * @param comboBox the given {@code JComboBox}.
   * @param index the index of the cell.
   * @return the internal value of a cell in a {@code JComboBox} as expected in a test.
   * @see CellRendererReader#valueFrom(Component)
   */
  @Override
  @RunsInCurrentThread
  public @Nullable String valueAt(@Nonnull JComboBox<?> comboBox, int index) {
    Component c = cellRendererComponent(comboBox, index);
    String value = (c != null) ? rendererReader.valueFrom(c) : null;
    if (value != null) {
      return value;
    }
    return asText(comboBox.getItemAt(index));
  }

  @RunsInCurrentThread
  private @Nullable Component cellRendererComponent(@Nonnull JComboBox comboBox, int index) {
    Object item = comboBox.getItemAt(index);
    ListCellRenderer renderer = comboBox.getRenderer();
    return renderer.getListCellRendererComponent(REFERENCE_JLIST, item, index, true, true);
  }
}
