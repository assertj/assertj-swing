/*
 * Created on Mar 2, 2008
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.MouseButton.MIDDLE_BUTTON;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.core.MouseClickInfo.middleButton;
import static org.fest.swing.data.TableCell.row;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.util.regex.Pattern;

import javax.swing.JTable;

import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.data.TableCell;
import org.fest.swing.driver.JTableDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JTableCellFixture}.
 *
 * @author Alex Ruiz
 */
public class JTableCellFixture_withMocks_Test {
  private JTableFixture table;
  private TableCell cell;
  private JTableDriver driver;
  private JTable target;

  private JTableCellFixture fixture;

  @Before
  public void setUp() {
    table = mock(JTableFixture.class);
    cell = row(6).column(8);
    driver = mock(JTableDriver.class);
    target = mock(JTable.class);
    fixture = new JTableCellFixture(table, cell, target, driver);
  }

  @Test
  public void should_call_select_in_JTableFixture_and_return_self() {
    assertThat(fixture.select()).isSameAs(fixture);
    verify(table).selectCell(cell);
  }

  @Test
  public void should_call_click_in_JTableFixture_and_return_self() {
    assertThat(fixture.click()).isSameAs(fixture);
    verify(table).click(cell, LEFT_BUTTON);
  }

  @Test
  public void should_call_click_with_MouseClickInfo_in_JTableFixture_and_return_self() {
    MouseClickInfo info = middleButton().times(3);
    assertThat(fixture.click(info)).isSameAs(fixture);
    verify(table).click(cell, info);
  }

  @Test
  public void should_call_click_with_MouseButton_in_JTableFixture_and_return_self() {
    assertThat(fixture.click(MIDDLE_BUTTON)).isSameAs(fixture);
    verify(table).click(cell, MIDDLE_BUTTON);
  }

  @Test
  public void should_call_click_with_left_button_two_times_in_JTableFixture_and_return_self() {
    assertThat(fixture.doubleClick()).isSameAs(fixture);
    verify(table).click(cell, LEFT_BUTTON, 2);
  }

  @Test
  public void should_call_click_with_right_button_one_time_in_JTableFixture_and_return_self() {
    assertThat(fixture.rightClick()).isSameAs(fixture);
    verify(table).click(cell, RIGHT_BUTTON);
  }

  @Test
  public void should_call_startCellEditing_in_driver_and_return_self() {
    assertThat(fixture.startEditing()).isSameAs(fixture);
    verify(driver).startCellEditing(target, cell);
  }

  @Test
  public void should_call_stopCellEditing_in_driver_and_return_self() {
    assertThat(fixture.stopEditing()).isSameAs(fixture);
    verify(driver).stopCellEditing(target, cell);
  }

  @Test
  public void should_call_cancelCellEditing_in_driver_and_return_self() {
    assertThat(fixture.cancelEditing()).isSameAs(fixture);
    verify(driver).cancelCellEditing(target, cell);
  }

  @Test
  public void should_return_cell_editor_using_driver() {
    Component editor = mock(Component.class);
    when(driver.cellEditor(target, cell)).thenReturn(editor);
    assertThat(fixture.editor()).isSameAs(editor);
    verify(driver).cellEditor(target, cell);
  }

  @Test
  public void should_call_enterValue_in_driver_and_return_self() {
    assertThat(fixture.enterValue("Hello")).isSameAs(fixture);
    verify(driver).enterValueInCell(target, cell, "Hello");
  }

  @Test
  public void should_call_requireValue_with_text_in_JTableFixture_and_return_self() {
    assertThat(fixture.requireValue("Six")).isSameAs(fixture);
    verify(table).requireCellValue(cell, "Six");
  }

  @Test
  public void should_call_requireValue_with_pattern_in_JTableFixture_and_return_self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.requireValue(pattern)).isSameAs(fixture);
    verify(table).requireCellValue(cell, pattern);
  }

  @Test
  public void should_return_font_using_JTableFixture() {
    FontFixture font = mock(FontFixture.class);
    when(table.fontAt(cell)).thenReturn(font);
    assertThat(fixture.font()).isSameAs(font);
    verify(table).fontAt(cell);
  }

  @Test
  public void should_return_background_using_JTableFixture() {
    ColorFixture color = mock(ColorFixture.class);
    when(table.backgroundAt(cell)).thenReturn(color);
    assertThat(fixture.background()).isSameAs(color);
    verify(table).backgroundAt(cell);
  }

  @Test
  public void should_return_foreground_using_JTableFixture() {
    ColorFixture color = mock(ColorFixture.class);
    when(table.foregroundAt(cell)).thenReturn(color);
    assertThat(fixture.foreground()).isSameAs(color);
    verify(table).foregroundAt(cell);
  }

  @Test
  public void should_return_value_using_JTableFixture() {
    when(table.valueAt(cell)).thenReturn("Hello");
    assertThat(fixture.value()).isEqualTo("Hello");
    verify(table).valueAt(cell);
  }

  @Test
  public void should_call_drag_in_JTableFixture_and_return_self() {
    assertThat(fixture.drag()).isSameAs(fixture);
    verify(table).drag(cell);
  }

  @Test
  public void should_call_drop_in_JTableFixture_and_return_self() {
    assertThat(fixture.drop()).isSameAs(fixture);
    verify(table).drop(cell);
  }

  @Test
  public void should_call_requireEditable_in_JTableFixture_and_return_self() {
    assertThat(fixture.requireEditable()).isSameAs(fixture);
    verify(table).requireEditable(cell);
  }

  @Test
  public void should_call_requireNotEditable_in_JTableFixture_and_return_self() {
    assertThat(fixture.requireNotEditable()).isSameAs(fixture);
    verify(table).requireNotEditable(cell);
  }

  @Test
  public void should_return_JPopupMenu_using_JTableFixture() {
    JPopupMenuFixture popupMenu = mock(JPopupMenuFixture.class);
    when(table.showPopupMenuAt(cell)).thenReturn(popupMenu);
    assertThat(fixture.showPopupMenu()).isSameAs(popupMenu);
    verify(table).showPopupMenuAt(cell);
  }

  @Test
  public void should_return_row() {
    assertThat(fixture.row()).isEqualTo(cell.row);
  }

  @Test
  public void should_return_column() {
    assertThat(fixture.column()).isEqualTo(cell.column);
  }
}
