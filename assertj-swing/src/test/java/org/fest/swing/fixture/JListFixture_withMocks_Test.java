/*
 * Created on Jun 12, 2007
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.MouseButton.MIDDLE_BUTTON;
import static org.fest.swing.util.Range.from;
import static org.fest.swing.util.Range.to;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.regex.Pattern;

import javax.swing.JList;
import javax.swing.JPopupMenu;

import org.fest.swing.cell.JListCellReader;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JListDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JListFixture}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListFixture_withMocks_Test {
  private JListDriver driver;
  private JList target;

  private JListFixture fixture;

  @Before
  public void setUp() {
    fixture = new JListFixture(mock(Robot.class), mock(JList.class));
    fixture.replaceDriverWith(mock(JListDriver.class));
    driver = fixture.driver();
    target = fixture.target();
  }

  @Test
  public void should_return_value_at_index_using_driver() {
    when(driver.value(target, 6)).thenReturn("Six");
    assertThat(fixture.valueAt(6)).isEqualTo("Six");
    verify(driver).value(target, 6);
  }

  @Test
  public void should_return_contents_using_driver() {
    String[] contents = { "One", "Two" };
    when(driver.contentsOf(target)).thenReturn(contents);
    assertThat(fixture.contents()).isSameAs(contents);
    verify(driver).contentsOf(target);
  }

  @Test
  public void should_return_selection_using_driver() {
    String[] selection = { "One" };
    when(driver.selectionOf(target)).thenReturn(selection);
    assertThat(fixture.selection()).isSameAs(selection);
    verify(driver).selectionOf(target);
  }

  @Test
  public void should_create_JListItemFixture_with_index() {
    JListItemFixture itemFixture = fixture.item(6);
    assertThat(itemFixture.listFixture()).isSameAs(fixture);
    assertThat(itemFixture.index()).isEqualTo(6);
  }

  @Test
  public void should_create_JListItemFixture_with_text() {
    when(driver.indexOf(target, "Six")).thenReturn(6);
    JListItemFixture itemFixture = fixture.item("Six");
    assertThat(itemFixture.listFixture()).isSameAs(fixture);
    assertThat(itemFixture.index()).isEqualTo(6);
    verify(driver).indexOf(target, "Six");
  }

  @Test
  public void should_create_JListItemFixture_with_pattern() {
    Pattern pattern = Pattern.compile("Six");
    when(driver.indexOf(target, pattern)).thenReturn(6);
    JListItemFixture itemFixture = fixture.item(pattern);
    assertThat(itemFixture.listFixture()).isSameAs(fixture);
    assertThat(itemFixture.index()).isEqualTo(6);
    verify(driver).indexOf(target, pattern);
  }

  @Test
  public void should_call_clearSelection_in_driver_and_return_self() {
    assertThat(fixture.clearSelection()).isSameAs(fixture);
    verify(driver).clearSelection(target);
  }

  @Test
  public void should_call_selectItem_with_index_in_driver_and_return_self() {
    assertThat(fixture.selectItem(6)).isSameAs(fixture);
    verify(driver).selectItem(target, 6);
  }

  @Test
  public void should_call_selectItem_with_text_in_driver_and_return_self() {
    assertThat(fixture.selectItem("Six")).isSameAs(fixture);
    verify(driver).selectItem(target, "Six");
  }

  @Test
  public void should_call_selectItem_with_pattern_in_driver_and_return_self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.selectItem(pattern)).isSameAs(fixture);
    verify(driver).selectItem(target, pattern);
  }

  @Test
  public void should_call_requireSelection_in_driver_and_return_self() {
    assertThat(fixture.requireSelection(6)).isSameAs(fixture);
    verify(driver).requireSelection(target, 6);
  }

  @Test
  public void should_call_requireNoSelection_in_driver_and_return_self() {
    assertThat(fixture.requireNoSelection()).isSameAs(fixture);
    verify(driver).requireNoSelection(target);
  }

  @Test
  public void should_call_requireItemCount_in_driver_and_return_self() {
    assertThat(fixture.requireItemCount(8)).isSameAs(fixture);
    verify(driver).requireItemCount(target, 8);
  }

  @Test
  public void should_call_selectItems_with_Range_in_driver_and_return_self() {
    assertThat(fixture.selectItems(from(6), to(8))).isSameAs(fixture);
    verify(driver).selectItems(target, from(6), to(8));
  }

  @Test
  public void should_call_selectItems_with_indices_in_driver_and_return_self() {
    int[] indices = { 6, 7, 8 };
    assertThat(fixture.selectItems(indices)).isSameAs(fixture);
    verify(driver).selectItems(target, indices);
  }

  @Test
  public void should_call_selectItems_with_text_in_driver_and_return_self() {
    String[] items = { "Six", "Seven", "Eight" };
    assertThat(fixture.selectItems(items)).isSameAs(fixture);
    verify(driver).selectItems(target, items);
  }

  @Test
  public void should_call_selectItems_with_Patterns_in_driver_and_return_self() {
    Pattern[] patterns = { Pattern.compile("six")  };
    assertThat(fixture.selectItems(patterns)).isSameAs(fixture);
    verify(driver).selectItems(target, patterns);
  }

  @Test
  public void should_call_clickItem_with_index_in_driver_and_return_self() {
    assertThat(fixture.clickItem(6)).isSameAs(fixture);
    verify(driver).clickItem(target, 6, LEFT_BUTTON, 1);
  }

  @Test
  public void should_call_clickItem_with_text_in_driver_and_return_self() {
    assertThat(fixture.clickItem("Six")).isSameAs(fixture);
    verify(driver).clickItem(target, "Six", LEFT_BUTTON, 1);
  }

  @Test
  public void should_call_clickItem_with_pattern_in_driver_and_return_self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.clickItem(pattern)).isSameAs(fixture);
    verify(driver).clickItem(target, pattern, LEFT_BUTTON, 1);
  }

  @Test
  public void should_call_clickItem_with_index_MouseButton_and_times_in_driver() {
    fixture.clickItem(6, MIDDLE_BUTTON, 2);
    verify(driver).clickItem(target, 6, MIDDLE_BUTTON, 2);
  }

  @Test
  public void should_call_requireSelectedItems_with_indices_in_driver_and_return_self() {
    int[] indices = { 6, 7, 8 };
    assertThat(fixture.requireSelectedItems(indices)).isSameAs(fixture);
    verify(driver).requireSelectedItems(target, indices);
  }

  @Test
  public void should_call_requireSelectedItems_with_text_in_driver_and_return_self() {
    String[] items = { "Six", "Seven", "Eight" };
    assertThat(fixture.requireSelectedItems(items)).isSameAs(fixture);
    verify(driver).requireSelectedItems(target, items);
  }

  @Test
  public void should_call_requireSelectedItems_with_Patterns_in_driver_and_return_self() {
    Pattern[] patterns = { Pattern.compile("six")  };
    assertThat(fixture.requireSelectedItems(patterns)).isSameAs(fixture);
    verify(driver).requireSelectedItems(target, patterns);
  }

  @Test
  public void should_call_drag_with_index_in_driver_and_return_self() {
    assertThat(fixture.drag(6)).isSameAs(fixture);
    verify(driver).drag(target, 6);
  }

  @Test
  public void should_call_drag_with_text_in_driver_and_return_self() {
    assertThat(fixture.drag("Six")).isSameAs(fixture);
    verify(driver).drag(target, "Six");
  }

  @Test
  public void should_call_drag_with_pattern_in_driver_and_return_self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.drag(pattern)).isSameAs(fixture);
    verify(driver).drag(target, pattern);
  }

  @Test
  public void should_call_drop_with_index_in_driver_and_return_self() {
    assertThat(fixture.drop(6)).isSameAs(fixture);
    verify(driver).drop(target, 6);
  }

  @Test
  public void should_call_drop_with_text_in_driver_and_return_self() {
    assertThat(fixture.drop("Six")).isSameAs(fixture);
    verify(driver).drop(target, "Six");
  }

  @Test
  public void should_call_drop_with_pattern_in_driver_and_return_self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.drop(pattern)).isSameAs(fixture);
    verify(driver).drop(target, pattern);
  }

  @Test
  public void should_show_JPopupMenu_with_index_using_driver() {
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(driver.showPopupMenu(target, 6)).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(6);
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).showPopupMenu(target, 6);
  }

  @Test
  public void should_show_JPopupMenu_with_text_using_driver() {
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(driver.showPopupMenu(target, "Six")).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt("Six");
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).showPopupMenu(target, "Six");
  }

  @Test
  public void should_show_JPopupMenu_with_pattern_using_driver() {
    Pattern pattern = Pattern.compile("Six");
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(driver.showPopupMenu(target, pattern)).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(pattern);
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).showPopupMenu(target, pattern);
  }

  @Test
  public void should_call_replaceCellReader_in_driver() {
    JListCellReader cellReader = mock(JListCellReader.class);
    fixture.replaceCellReader(cellReader);
    verify(fixture.driver()).replaceCellReader(cellReader);
  }
}
