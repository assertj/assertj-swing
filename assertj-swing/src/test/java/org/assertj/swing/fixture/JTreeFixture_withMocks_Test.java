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
import static org.assertj.swing.core.MouseButton.MIDDLE_BUTTON;
import static org.assertj.swing.core.MouseClickInfo.middleButton;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.swing.JPopupMenu;
import javax.swing.JTree;

import org.assertj.swing.cell.JTreeCellReader;
import org.assertj.swing.core.MouseClickInfo;
import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JTreeDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JTreeFixture}.
 * 
 * @author Keith Coughtrey
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTreeFixture_withMocks_Test {
  private JTreeDriver driver;
  private JTree target;

  private JTreeFixture fixture;

  @Before
  public void setUp() {
    fixture = new JTreeFixture(mock(Robot.class), mock(JTree.class));
    fixture.replaceDriverWith(mock(JTreeDriver.class));
    driver = fixture.driver();
    target = fixture.target();
  }

  @Test
  public void should_Call_ClickRow_In_Driver_And_Return_Self() {
    assertThat(fixture.clickRow(6)).isSameAs(fixture);
    verify(driver).clickRow(target, 6);
  }

  @Test
  public void should_Call_ClickRow_With_MouseButton_In_Driver_And_Return_Self() {
    assertThat(fixture.clickRow(6, MIDDLE_BUTTON)).isSameAs(fixture);
    verify(driver).clickRow(target, 6, MIDDLE_BUTTON);
  }

  @Test
  public void should_Call_ClickRow_With_MouseClickInfo_In_Driver_And_Return_Self() {
    MouseClickInfo info = middleButton().times(3);
    assertThat(fixture.clickRow(6, info)).isSameAs(fixture);
    verify(driver).clickRow(target, 6, info);
  }

  @Test
  public void should_Call_ClickPath_In_Driver_And_Return_Self() {
    assertThat(fixture.clickPath("root/child")).isSameAs(fixture);
    verify(driver).clickPath(target, "root/child");
  }

  @Test
  public void should_Call_ClickPath_With_MouseButton_In_Driver_And_Return_Self() {
    assertThat(fixture.clickPath("root/child", MIDDLE_BUTTON)).isSameAs(fixture);
    verify(driver).clickPath(target, "root/child", MIDDLE_BUTTON);
  }

  @Test
  public void should_Call_ClickPath_With_MouseClickInfo_In_Driver_And_Return_Self() {
    MouseClickInfo info = middleButton().times(3);
    assertThat(fixture.clickPath("root/child", info)).isSameAs(fixture);
    verify(driver).clickPath(target, "root/child", info);
  }

  @Test
  public void should_Call_DoubleClickRow_In_Driver_And_Return_Self() {
    assertThat(fixture.doubleClickRow(6)).isSameAs(fixture);
    verify(driver).doubleClickRow(target, 6);
  }

  @Test
  public void should_Call_DoubleClickPath_In_Driver_And_Return_Self() {
    assertThat(fixture.doubleClickPath("root/child")).isSameAs(fixture);
    verify(driver).doubleClickPath(target, "root/child");
  }

  @Test
  public void should_Call_RightClickRow_In_Driver_And_Return_Self() {
    assertThat(fixture.rightClickRow(6)).isSameAs(fixture);
    verify(driver).rightClickRow(target, 6);
  }

  @Test
  public void should_Call_RightClickPath_In_Driver_And_Return_Self() {
    assertThat(fixture.rightClickPath("root/child")).isSameAs(fixture);
    verify(driver).rightClickPath(target, "root/child");
  }

  @Test
  public void should_Call_Drag_With_Row_In_Driver_And_Return_Self() {
    assertThat(fixture.drag(6)).isSameAs(fixture);
    verify(driver).drag(target, 6);
  }

  @Test
  public void should_Call_Drop_With_Row_In_Driver_And_Return_Self() {
    assertThat(fixture.drop(6)).isSameAs(fixture);
    verify(driver).drop(target, 6);
  }

  @Test
  public void should_Call_Drag_With_Path_In_Driver_And_Return_Self() {
    assertThat(fixture.drag("root/child")).isSameAs(fixture);
    verify(driver).drag(target, "root/child");
  }

  @Test
  public void should_Call_Drop_With_Path_In_Driver_And_Return_Self() {
    assertThat(fixture.drop("root/child")).isSameAs(fixture);
    verify(driver).drop(target, "root/child");
  }

  @Test
  public void should_Call_SelectRow_In_Driver_And_Return_Self() {
    assertThat(fixture.selectRow(6)).isSameAs(fixture);
    verify(driver).selectRow(target, 6);
  }

  @Test
  public void should_Call_SelectRows_In_Driver_And_Return_Self() {
    int[] rows = { 6, 8 };
    assertThat(fixture.selectRows(rows)).isSameAs(fixture);
    verify(driver).selectRows(target, rows);
  }

  @Test
  public void should_Call_SelectPath_In_Driver_And_Return_Self() {
    assertThat(fixture.selectPath("root/child")).isSameAs(fixture);
    verify(driver).selectPath(target, "root/child");
  }

  @Test
  public void should_Call_SelectPaths_In_Driver_And_Return_Self() {
    String[] paths = { "root", "root/child" };
    assertThat(fixture.selectPaths(paths)).isSameAs(fixture);
    verify(driver).selectPaths(target, paths);
  }

  @Test
  public void should_Call_ToggleRow_In_Driver_And_Return_Self() {
    assertThat(fixture.toggleRow(6)).isSameAs(fixture);
    verify(driver).toggleRow(target, 6);
  }

  @Test
  public void should_Call_ExpandRow_In_Driver_And_Return_Self() {
    assertThat(fixture.expandRow(6)).isSameAs(fixture);
    verify(driver).expandRow(target, 6);
  }

  @Test
  public void should_Call_CollapseRow_In_Driver_And_Return_Self() {
    assertThat(fixture.collapseRow(6)).isSameAs(fixture);
    verify(driver).collapseRow(target, 6);
  }

  @Test
  public void should_Call_ExpandPath_In_Driver_And_Return_Self() {
    assertThat(fixture.expandPath("root/child")).isSameAs(fixture);
    verify(driver).expandPath(target, "root/child");
  }

  @Test
  public void should_Call_CollapsePath_In_Driver_And_Return_Self() {
    assertThat(fixture.collapsePath("root/child")).isSameAs(fixture);
    verify(driver).collapsePath(target, "root/child");
  }

  @Test
  public void should_Return_JPopupMenu_At_Row_Using_Driver() {
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(driver.showPopupMenu(target, 6)).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(6);
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).showPopupMenu(target, 6);
  }

  @Test
  public void should_Return_JPopupMenu_At_Path_Using_Driver() {
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(driver.showPopupMenu(target, "root/child")).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt("root/child");
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).showPopupMenu(target, "root/child");
  }

  @Test
  public void should_Call_RequireEditable_In_Driver_And_Return_Self() {
    assertThat(fixture.requireEditable()).isSameAs(fixture);
    verify(driver).requireEditable(target);
  }

  @Test
  public void should_Call_RequireNotEditable_In_Driver_And_Return_Self() {
    assertThat(fixture.requireNotEditable()).isSameAs(fixture);
    verify(driver).requireNotEditable(target);
  }

  @Test
  public void should_Call_RequireSelection_With_Rows_In_Driver_And_Return_Self() {
    int[] rows = { 6, 8 };
    assertThat(fixture.requireSelection(rows)).isSameAs(fixture);
    verify(driver).requireSelection(target, rows);
  }

  @Test
  public void should_Call_RequireSelection_With_Paths_In_Driver_And_Return_Self() {
    String[] paths = { "root", "root/child" };
    assertThat(fixture.requireSelection(paths)).isSameAs(fixture);
    verify(driver).requireSelection(target, paths);
  }

  @Test
  public void should_Call_RequireNoSelection_In_Driver_And_Return_Self() {
    assertThat(fixture.requireNoSelection()).isSameAs(fixture);
    verify(driver).requireNoSelection(target);
  }

  @Test
  public void should_Return_Path_Separator_Using_Driver() {
    when(driver.separator()).thenReturn(":");
    assertThat(fixture.separator()).isEqualTo(":");
    verify(driver).separator();
  }

  @Test
  public void should_Call_ReplaceSeparator_In_Driver_And_Return_Self() {
    assertThat(fixture.replaceSeparator(":")).isSameAs(fixture);
    verify(driver).replaceSeparator(":");
  }

  @Test
  public void should_Call_ReplaceCellReader_In_Driver_And_Return_Self() {
    JTreeCellReader reader = mock(JTreeCellReader.class);
    assertThat(fixture.replaceCellReader(reader)).isSameAs(fixture);
    verify(driver).replaceCellReader(reader);
  }

  @Test
  public void should_Return_JTreeRowFixture() {
    JTreeRowFixture treeRowFixture = fixture.node(6);
    assertThat(treeRowFixture.treeFixture()).isSameAs(fixture);
    assertThat(treeRowFixture.index()).isEqualTo(6);
    verify(driver).checkRowInBounds(target, 6);
  }

  @Test
  public void should_Return_JTreePathFixture() {
    JTreePathFixture treePathFixture = fixture.node("root/child");
    assertThat(treePathFixture.treeFixture()).isSameAs(fixture);
    assertThat(treePathFixture.path()).isEqualTo("root/child");
    verify(driver).checkPathExists(target, "root/child");
  }

  @Test
  public void should_Return_Value_At_Row_Using_Driver() {
    when(driver.nodeValue(target, 6)).thenReturn("1");
    assertThat(fixture.valueAt(6)).isEqualTo("1");
    verify(driver).nodeValue(target, 6);
  }

  @Test
  public void should_Return_Value_At_Path_Using_Driver() {
    when(driver.nodeValue(target, "root/child")).thenReturn("1");
    assertThat(fixture.valueAt("root/child")).isEqualTo("1");
    verify(driver).nodeValue(target, "root/child");
  }
}