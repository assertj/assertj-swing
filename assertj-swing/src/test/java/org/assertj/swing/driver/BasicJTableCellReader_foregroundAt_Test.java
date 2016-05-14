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
import javax.swing.JLabel;
import javax.swing.JTable;

import org.assertj.swing.annotation.RunsInEDT;
import org.junit.Test;

/**
 * Tests for {@link BasicJTableCellReader#foregroundAt(JTable, int, int)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJTableCellReader_foregroundAt_Test extends BasicJTableCellReader_TestCase {
  @Test
  public void should_Return_Foreground_From_CellRenderer() {
    JLabel label = setJLabelAsCellRenderer();
    robot.waitForIdle();
    Color foreground = foregroundAt(reader, table, 0, 0);
    assertThat(foreground).isEqualTo(foregroundOf(label));
  }

  @RunsInEDT
  private static Color foregroundAt(final BasicJTableCellReader reader, final JTable table, final int row,
                                    final int column) {
    return execute(() -> reader.foregroundAt(table, row, column));
  }

  @RunsInEDT
  private static @Nonnull Color foregroundOf(final @Nonnull Component component) {
    Color result = execute(() -> component.getForeground());
    return checkNotNull(result);
  }
}
