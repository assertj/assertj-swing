/*
 * Created on Feb 25, 2008
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

import static java.awt.Color.BLUE;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.data.TableCell.row;
import static org.mockito.Mockito.when;

import java.awt.Color;

import javax.swing.JTable;

import org.fest.swing.data.TableCell;
import org.junit.Test;

/**
 * Tests for {@link JTableDriver#background(JTable, TableCell)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableDriver_background_Test extends JTableDriver_withMockCellReader_TestCase {
  private Color background;

  @Override
  void onSetUp() {
    background = BLUE;
  }

  @Test
  public void should_return_cell_background_color() {
    when(cellReader.backgroundAt(table, 0, 0)).thenReturn(background);
    Color result = driver.background(table, row(0).column(0));
    assertThat(result).isSameAs(background);
  }
}
