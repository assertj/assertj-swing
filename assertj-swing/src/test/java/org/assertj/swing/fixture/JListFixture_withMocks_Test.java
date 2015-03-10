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
package org.assertj.swing.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.core.MouseButton.LEFT_BUTTON;
import static org.assertj.swing.core.MouseButton.MIDDLE_BUTTON;
import static org.assertj.swing.util.Range.from;
import static org.assertj.swing.util.Range.to;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.regex.Pattern;

import javax.swing.JList;
import javax.swing.JPopupMenu;

import org.assertj.swing.cell.JListCellReader;
import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JListDriver;
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
  public void should_Return_Value_At_Index_Using_Driver() {
    when(driver.value(target, 6)).thenReturn("Six");
    assertThat(fixture.valueAt(6)).isEqualTo("Six");
    verify(driver).value(target, 6);
  }

  @Test
  public void should_Return_Contents_Using_Driver() {
    String[] contents = { "One", "Two" };
    when(driver.contentsOf(target)).thenReturn(contents);
    assertThat(fixture.contents()).isSameAs(contents);
    verify(driver).contentsOf(target);
  }

  @Test
  public void should_Return_Selection_Using_Driver() {
    String[] selection = { "One" };
    when(driver.selectionOf(target)).thenReturn(selection);
    assertThat(fixture.selection()).isSameAs(selection);
    verify(driver).selectionOf(target);
  }

  @Test
  public void should_Create_JListItemFixture_With_Index() {
    JListItemFixture itemFixture = fixture.item(6);
    assertThat(itemFixture.listFixture()).isSameAs(fixture);
    assertThat(itemFixture.index()).isEqualTo(6);
  }

  @Test
  public void should_Create_JListItemFixture_With_Text() {
    when(driver.indexOf(target, "Six")).thenReturn(6);
    JListItemFixture itemFixture = fixture.item("Six");
    assertThat(itemFixture.listFixture()).isSameAs(fixture);
    assertThat(itemFixture.index()).isEqualTo(6);
    verify(driver).indexOf(target, "Six");
  }

  @Test
  public void should_Create_JListItemFixture_With_Pattern() {
    Pattern pattern = Pattern.compile("Six");
    when(driver.indexOf(target, pattern)).thenReturn(6);
    JListItemFixture itemFixture = fixture.item(pattern);
    assertThat(itemFixture.listFixture()).isSameAs(fixture);
    assertThat(itemFixture.index()).isEqualTo(6);
    verify(driver).indexOf(target, pattern);
  }

  @Test
  public void should_Call_ClearSelection_In_Driver_And_Return_Self() {
    assertThat(fixture.clearSelection()).isSameAs(fixture);
    verify(driver).clearSelection(target);
  }

  @Test
  public void should_Call_SelectItem_With_Index_In_Driver_And_Return_Self() {
    assertThat(fixture.selectItem(6)).isSameAs(fixture);
    verify(driver).selectItem(target, 6);
  }

  @Test
  public void should_Call_SelectItem_With_Text_In_Driver_And_Return_Self() {
    assertThat(fixture.selectItem("Six")).isSameAs(fixture);
    verify(driver).selectItem(target, "Six");
  }

  @Test
  public void should_Call_SelectItem_With_Pattern_In_Driver_And_Return_Self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.selectItem(pattern)).isSameAs(fixture);
    verify(driver).selectItem(target, pattern);
  }

  @Test
  public void should_Call_RequireSelection_In_Driver_And_Return_Self() {
    assertThat(fixture.requireSelection(6)).isSameAs(fixture);
    verify(driver).requireSelection(target, 6);
  }

  @Test
  public void should_Call_RequireNoSelection_In_Driver_And_Return_Self() {
    assertThat(fixture.requireNoSelection()).isSameAs(fixture);
    verify(driver).requireNoSelection(target);
  }

  @Test
  public void should_Call_RequireItemCount_In_Driver_And_Return_Self() {
    assertThat(fixture.requireItemCount(8)).isSameAs(fixture);
    verify(driver).requireItemCount(target, 8);
  }

  @Test
  public void should_Call_SelectItems_With_Range_In_Driver_And_Return_Self() {
    assertThat(fixture.selectItems(from(6), to(8))).isSameAs(fixture);
    verify(driver).selectItems(target, from(6), to(8));
  }

  @Test
  public void should_Call_SelectItems_With_Indices_In_Driver_And_Return_Self() {
    int[] indices = { 6, 7, 8 };
    assertThat(fixture.selectItems(indices)).isSameAs(fixture);
    verify(driver).selectItems(target, indices);
  }

  @Test
  public void should_Call_SelectItems_With_Text_In_Driver_And_Return_Self() {
    String[] items = { "Six", "Seven", "Eight" };
    assertThat(fixture.selectItems(items)).isSameAs(fixture);
    verify(driver).selectItems(target, items);
  }

  @Test
  public void should_Call_SelectItems_With_Patterns_In_Driver_And_Return_Self() {
    Pattern[] patterns = { Pattern.compile("six") };
    assertThat(fixture.selectItems(patterns)).isSameAs(fixture);
    verify(driver).selectItems(target, patterns);
  }

  @Test
  public void should_Call_ClickItem_With_Index_In_Driver_And_Return_Self() {
    assertThat(fixture.clickItem(6)).isSameAs(fixture);
    verify(driver).clickItem(target, 6, LEFT_BUTTON, 1);
  }

  @Test
  public void should_Call_ClickItem_With_Text_In_Driver_And_Return_Self() {
    assertThat(fixture.clickItem("Six")).isSameAs(fixture);
    verify(driver).clickItem(target, "Six", LEFT_BUTTON, 1);
  }

  @Test
  public void should_Call_ClickItem_With_Pattern_In_Driver_And_Return_Self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.clickItem(pattern)).isSameAs(fixture);
    verify(driver).clickItem(target, pattern, LEFT_BUTTON, 1);
  }

  @Test
  public void should_Call_ClickItem_With_Index_MouseButton_And_Times_In_Driver() {
    fixture.clickItem(6, MIDDLE_BUTTON, 2);
    verify(driver).clickItem(target, 6, MIDDLE_BUTTON, 2);
  }

  @Test
  public void should_Call_RequireSelectedItems_With_Indices_In_Driver_And_Return_Self() {
    int[] indices = { 6, 7, 8 };
    assertThat(fixture.requireSelectedItems(indices)).isSameAs(fixture);
    verify(driver).requireSelectedItems(target, indices);
  }

  @Test
  public void should_Call_RequireSelectedItems_With_Text_In_Driver_And_Return_Self() {
    String[] items = { "Six", "Seven", "Eight" };
    assertThat(fixture.requireSelectedItems(items)).isSameAs(fixture);
    verify(driver).requireSelectedItems(target, items);
  }

  @Test
  public void should_Call_RequireSelectedItems_With_Patterns_In_Driver_And_Return_Self() {
    Pattern[] patterns = { Pattern.compile("six") };
    assertThat(fixture.requireSelectedItems(patterns)).isSameAs(fixture);
    verify(driver).requireSelectedItems(target, patterns);
  }

  @Test
  public void should_Call_Drag_With_Index_In_Driver_And_Return_Self() {
    assertThat(fixture.drag(6)).isSameAs(fixture);
    verify(driver).drag(target, 6);
  }

  @Test
  public void should_Call_Drag_With_Text_In_Driver_And_Return_Self() {
    assertThat(fixture.drag("Six")).isSameAs(fixture);
    verify(driver).drag(target, "Six");
  }

  @Test
  public void should_Call_Drag_With_Pattern_In_Driver_And_Return_Self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.drag(pattern)).isSameAs(fixture);
    verify(driver).drag(target, pattern);
  }

  @Test
  public void should_Call_Drop_With_Index_In_Driver_And_Return_Self() {
    assertThat(fixture.drop(6)).isSameAs(fixture);
    verify(driver).drop(target, 6);
  }

  @Test
  public void should_Call_Drop_With_Text_In_Driver_And_Return_Self() {
    assertThat(fixture.drop("Six")).isSameAs(fixture);
    verify(driver).drop(target, "Six");
  }

  @Test
  public void should_Call_Drop_With_Pattern_In_Driver_And_Return_Self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.drop(pattern)).isSameAs(fixture);
    verify(driver).drop(target, pattern);
  }

  @Test
  public void should_Show_JPopupMenu_With_Index_Using_Driver() {
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(driver.showPopupMenu(target, 6)).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(6);
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).showPopupMenu(target, 6);
  }

  @Test
  public void should_Show_JPopupMenu_With_Text_Using_Driver() {
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(driver.showPopupMenu(target, "Six")).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt("Six");
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).showPopupMenu(target, "Six");
  }

  @Test
  public void should_Show_JPopupMenu_With_Pattern_Using_Driver() {
    Pattern pattern = Pattern.compile("Six");
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(driver.showPopupMenu(target, pattern)).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(pattern);
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).showPopupMenu(target, pattern);
  }

  @Test
  public void should_Call_ReplaceCellReader_In_Driver() {
    JListCellReader cellReader = mock(JListCellReader.class);
    fixture.replaceCellReader(cellReader);
    verify(fixture.driver()).replaceCellReader(cellReader);
  }
}
