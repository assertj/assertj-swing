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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Color;
import java.awt.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JLabel;
import javax.swing.JTable;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.junit.Test;

/**
 * Tests for {@link BasicJTableCellReader#backgroundAt(JTable, int, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJTableCellReader_backgroundAt_Test extends BasicJTableCellReader_TestCase {
  @Test
  public void should_Return_Background_From_CellRenderer() {
    JLabel label = setJLabelAsCellRenderer();
    Color background = backgroundAt(reader, table, 0, 0);
    assertThat(background).isEqualTo(backgroundOf(label));
  }

  @RunsInEDT
  private static Color backgroundAt(final BasicJTableCellReader reader, final JTable table, final int row,
      final int column) {
    return execute(new GuiQuery<Color>() {
      @Override
      protected Color executeInEDT() {
        return reader.backgroundAt(table, row, column);
      }
    });
  }

  @RunsInEDT
  private static @Nonnull Color backgroundOf(final @Nonnull Component component) {
    Color result = execute(new GuiQuery<Color>() {
      @Override
      protected @Nullable Color executeInEDT() {
        return component.getBackground();
      }
    });
    return checkNotNull(result);
  }
}
