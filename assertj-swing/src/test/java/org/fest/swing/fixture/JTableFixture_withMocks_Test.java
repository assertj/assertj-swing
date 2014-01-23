/*
 * Created on Jul 12, 2007
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
package org.fest.swing.fixture;

import static java.awt.Color.BLUE;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.MouseButton.MIDDLE_BUTTON;
import static org.fest.swing.core.MouseClickInfo.middleButton;
import static org.fest.swing.data.TableCell.row;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Font;
import java.awt.Point;
import java.util.regex.Pattern;

import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.data.TableCell;
import org.fest.swing.data.TableCellFinder;
import org.fest.swing.driver.JTableDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JTableFixture}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableFixture_withMocks_Test {
  private TableCell cell;
  private JTableDriver driver;
  private JTable target;

  private JTableFixture fixture;

  @Before
  public void setUp() {
    cell = row(6).column(8);
    fixture = new JTableFixture(mock(Robot.class), mock(JTable.class));
    fixture.replaceDriverWith(mock(JTableDriver.class));
    driver = fixture.driver();
    target = fixture.target();
  }

  @Test
  public void should_return_font_at_cell_using_driver() {
    Font font = mock(Font.class);
    when(driver.font(target, cell)).thenReturn(font);
    FontFixture fontFixture = fixture.fontAt(cell);
    assertThat(fontFixture.target()).isSameAs(font);
    verify(driver).font(target, cell);
  }

  @Test
  public void should_return_background_at_cell_using_driver() {
    when(driver.background(target, cell)).thenReturn(BLUE);
    ColorFixture colorFixture = fixture.backgroundAt(cell);
    assertThat(colorFixture.target()).isSameAs(BLUE);
    verify(driver).background(target, cell);
  }

  @Test
  public void should_return_foreground_at_cell_using_driver() {
    when(driver.foreground(target, cell)).thenReturn(BLUE);
    ColorFixture colorFixture = fixture.foregroundAt(cell);
    assertThat(colorFixture.target()).isSameAs(BLUE);
    verify(driver).foreground(target, cell);
  }

  @Test
  public void should_return_cell_with_value_using_driver() {
    when(driver.cell(target, "Hello")).thenReturn(cell);
    JTableCellFixture cellFixture = fixture.cell("Hello");
    assertThat(cellFixture.cell()).isSameAs(cell);
    assertThat(cellFixture.tableFixture()).isSameAs(fixture);
    verify(driver).cell(target, "Hello");
  }

  @Test
  public void should_return_cell_with_pattern_using_driver() {
    Pattern pattern = Pattern.compile("Hello");
    when(driver.cell(target, pattern)).thenReturn(cell);
    JTableCellFixture cellFixture = fixture.cell(pattern);
    assertThat(cellFixture.cell()).isSameAs(cell);
    assertThat(cellFixture.tableFixture()).isSameAs(fixture);
    verify(driver).cell(target, pattern);
  }

  @Test
  public void should_return_cell_with_TableCellFinder_using_driver() {
    TableCellFinder cellFinder = mock(TableCellFinder.class);
    when(driver.cell(target, cellFinder)).thenReturn(cell);
    JTableCellFixture cellFixture = fixture.cell(cellFinder);
    assertThat(cellFixture.cell()).isSameAs(cell);
    assertThat(cellFixture.tableFixture()).isSameAs(fixture);
    verify(driver).cell(target, cellFinder);
  }

  @Test
  public void should_return_cell_using_driver() {
    JTableCellFixture cellFixture = fixture.cell(cell);
    assertThat(cellFixture.cell()).isSameAs(cell);
    assertThat(cellFixture.tableFixture()).isSameAs(fixture);
    verify(driver).checkCellIndicesInBounds(target, cell);
  }

  @Test
  public void should_return_JTableHeader_using_driver() {
    JTableHeader header = mock(JTableHeader.class);
    when(driver.tableHeaderOf(target)).thenReturn(header);
    JTableHeaderFixture headerFixture = fixture.tableHeader();
    assertThat(headerFixture.target()).isSameAs(header);
    verify(driver).tableHeaderOf(target);
  }

  @Test
  public void should_return_selection_using_driver() {
    when(driver.selectionValue(target)).thenReturn("Hello");
    assertThat(fixture.selectionValue()).isEqualTo("Hello");
    verify(driver).selectionValue(target);
  }

  @Test
  public void should_convert_cell_to_point_using_driver() {
    Point p = new Point(6, 8);
    when(driver.pointAt(target, cell)).thenReturn(p);
    assertThat(fixture.pointAt(cell)).isSameAs(p);
    verify(driver).pointAt(target, cell);
  }

  @Test
  public void should_return_contents_using_driver() {
    String[][] contents = { { "0", "1", "2" } };
    when(driver.contents(target)).thenReturn(contents);
    assertThat(fixture.contents()).isSameAs(contents);
    verify(driver).contents(target);
  }

  @Test
  public void should_return_row_count_using_driver() {
    when(driver.rowCountOf(target)).thenReturn(6);
    assertThat(fixture.rowCount()).isEqualTo(6);
    verify(driver).rowCountOf(target);
  }

  @Test
  public void should_return_value_at_cell_using_driver() {
    when(driver.value(target, cell)).thenReturn("Hello");
    assertThat(fixture.valueAt(cell)).isEqualTo("Hello");
    verify(driver).value(target, cell);
  }

  @Test
  public void should_call_selectCell_in_driver_and_return_self() {
    assertThat(fixture.selectCell(cell)).isSameAs(fixture);
    verify(driver).selectCell(target, cell);
  }

  @Test
  public void should_call_selectCells_in_driver_and_return_self() {
    TableCell[] cells = { cell };
    assertThat(fixture.selectCells(cells)).isSameAs(fixture);
    verify(driver).selectCells(target, cells);
  }

  @Test
  public void should_call_selectRows_in_driver_and_return_self() {
    int rows[] = { 6, 8 };
    assertThat(fixture.selectRows(rows)).isSameAs(fixture);
    verify(driver).selectRows(target, rows);
  }

  @Test
  public void should_call_drag_in_driver_and_return_self() {
    assertThat(fixture.drag(cell)).isSameAs(fixture);
    verify(driver).drag(target, cell);
  }

  @Test
  public void should_call_drop_in_driver_and_return_self() {
    assertThat(fixture.drop(cell)).isSameAs(fixture);
    verify(driver).drop(target, cell);
  }

  @Test
  public void should_call_click_with_MouseButton_in_driver_and_return_self() {
    assertThat(fixture.click(cell, LEFT_BUTTON)).isSameAs(fixture);
    verify(driver).click(target, cell, LEFT_BUTTON, 1);
  }

  @Test
  public void should_call_click_with_MouseClickInfo_in_driver_and_return_self() {
    MouseClickInfo info = middleButton().times(3);
    assertThat(fixture.click(cell, info)).isSameAs(fixture);
    verify(driver).click(target, cell, MIDDLE_BUTTON, 3);
  }

  @Test
  public void should_call_enterValueInCell_in_driver_and_return_self() {
    assertThat(fixture.enterValue(cell, "Hello")).isSameAs(fixture);
    verify(driver).enterValueInCell(target, cell, "Hello");
  }

  @Test
  public void should_call_replaceCellReader_in_driver() {
    JTableCellReader cellReader = mock(JTableCellReader.class);
    fixture.replaceCellReader(cellReader);
    verify(driver).replaceCellReader(cellReader);
  }

  @Test
  public void should_call_requireRowCount_in_driver_and_return_self() {
    assertThat(fixture.requireRowCount(6)).isSameAs(fixture);
    verify(driver).requireRowCount(target, 6);
  }

  @Test
  public void should_call_requireSelectedRows_in_driver_and_return_self() {
    int[] rows = { 6, 8 };
    assertThat(fixture.requireSelectedRows(rows)).isSameAs(fixture);
    verify(driver).requireSelectedRows(target, rows);
  }

  @Test
  public void should_call_requireColumnCount_in_driver_and_return_self() {
    assertThat(fixture.requireColumnCount(6)).isSameAs(fixture);
    verify(driver).requireColumnCount(target, 6);
  }

  @Test
  public void should_call_requireEditable_in_driver_and_return_self() {
    assertThat(fixture.requireEditable(cell)).isSameAs(fixture);
    verify(driver).requireEditable(target, cell);
  }

  @Test
  public void should_call_requireNotEditable_in_driver_and_return_self() {
    assertThat(fixture.requireNotEditable(cell)).isSameAs(fixture);
    verify(driver).requireNotEditable(target, cell);
  }

  @Test
  public void should_call_requireNoSelection_in_driver_and_return_self() {
    assertThat(fixture.requireNoSelection()).isSameAs(fixture);
    verify(driver).requireNoSelection(target);
  }

  @Test
  public void should_call_requireCellValue_with_text_in_driver_and_return_self() {
    assertThat(fixture.requireCellValue(cell, "Hello")).isSameAs(fixture);
    verify(driver).requireCellValue(target, cell, "Hello");
  }

  @Test
  public void should_call_requireCellValue_with_pattern_in_driver_and_return_self() {
    Pattern pattern = Pattern.compile("Hello");
    assertThat(fixture.requireCellValue(cell, pattern)).isSameAs(fixture);
    verify(driver).requireCellValue(target, cell, pattern);
  }

  @Test
  public void should_call_requireContents_in_driver_and_return_self() {
    String[][] contents = { { "0", "1", "2" } };
    assertThat(fixture.requireContents(contents)).isSameAs(fixture);
    verify(driver).requireContents(target, contents);
  }

  @Test
  public void should_call_replaceCellWriter_in_driver() {
    JTableCellWriter cellWriter = mock(JTableCellWriter.class);
    fixture.replaceCellWriter(cellWriter);
    verify(driver).replaceCellWriter(cellWriter);
  }

  @Test
  public void should_return_column_index_using_driver() {
    when(driver.columnIndex(target, "Name")).thenReturn(2);
    assertThat(fixture.columnIndexFor("Name")).isEqualTo(2);
    verify(driver).columnIndex(target, "Name");
  }

  @Test
  public void should_return_JPopupMenu_with_cell_using_driver() {
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(driver.showPopupMenuAt(target, cell)).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(cell);
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).showPopupMenuAt(target, cell);
  }
}
