/*
 * Created on May 21, 2007
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
import static org.fest.swing.core.MouseButton.MIDDLE_BUTTON;
import static org.fest.swing.core.MouseClickInfo.middleButton;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.swing.JPopupMenu;
import javax.swing.JTree;

import org.fest.swing.cell.JTreeCellReader;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JTreeDriver;
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
  public void should_call_clickRow_in_driver_and_return_self() {
    assertThat(fixture.clickRow(6)).isSameAs(fixture);
    verify(driver).clickRow(target, 6);
  }

  @Test
  public void should_call_clickRow_with_MouseButton_in_driver_and_return_self() {
    assertThat(fixture.clickRow(6, MIDDLE_BUTTON)).isSameAs(fixture);
    verify(driver).clickRow(target, 6, MIDDLE_BUTTON);
  }

  @Test
  public void should_call_clickRow_with_MouseClickInfo_in_driver_and_return_self() {
    MouseClickInfo info = middleButton().times(3);
    assertThat(fixture.clickRow(6, info)).isSameAs(fixture);
    verify(driver).clickRow(target, 6, info);
  }

  @Test
  public void should_call_clickPath_in_driver_and_return_self() {
    assertThat(fixture.clickPath("root/child")).isSameAs(fixture);
    verify(driver).clickPath(target, "root/child");
  }

  @Test
  public void should_call_clickPath_with_MouseButton_in_driver_and_return_self() {
    assertThat(fixture.clickPath("root/child", MIDDLE_BUTTON)).isSameAs(fixture);
    verify(driver).clickPath(target, "root/child", MIDDLE_BUTTON);
  }

  @Test
  public void should_call_clickPath_with_MouseClickInfo_in_driver_and_return_self() {
    MouseClickInfo info = middleButton().times(3);
    assertThat(fixture.clickPath("root/child", info)).isSameAs(fixture);
    verify(driver).clickPath(target, "root/child", info);
  }

  @Test
  public void should_call_doubleClickRow_in_driver_and_return_self() {
    assertThat(fixture.doubleClickRow(6)).isSameAs(fixture);
    verify(driver).doubleClickRow(target, 6);
  }

  @Test
  public void should_call_doubleClickPath_in_driver_and_return_self() {
    assertThat(fixture.doubleClickPath("root/child")).isSameAs(fixture);
    verify(driver).doubleClickPath(target, "root/child");
  }

  @Test
  public void should_call_rightClickRow_in_driver_and_return_self() {
    assertThat(fixture.rightClickRow(6)).isSameAs(fixture);
    verify(driver).rightClickRow(target, 6);
  }

  @Test
  public void should_call_rightClickPath_in_driver_and_return_self() {
    assertThat(fixture.rightClickPath("root/child")).isSameAs(fixture);
    verify(driver).rightClickPath(target, "root/child");
  }

  @Test
  public void should_call_drag_with_row_in_driver_and_return_self() {
    assertThat(fixture.drag(6)).isSameAs(fixture);
    verify(driver).drag(target, 6);
  }

  @Test
  public void should_call_drop_with_row_in_driver_and_return_self() {
    assertThat(fixture.drop(6)).isSameAs(fixture);
    verify(driver).drop(target, 6);
  }

  @Test
  public void should_call_drag_with_path_in_driver_and_return_self() {
    assertThat(fixture.drag("root/child")).isSameAs(fixture);
    verify(driver).drag(target, "root/child");
  }

  @Test
  public void should_call_drop_with_path_in_driver_and_return_self() {
    assertThat(fixture.drop("root/child")).isSameAs(fixture);
    verify(driver).drop(target, "root/child");
  }

  @Test
  public void should_call_selectRow_in_driver_and_return_self() {
    assertThat(fixture.selectRow(6)).isSameAs(fixture);
    verify(driver).selectRow(target, 6);
  }

  @Test
  public void should_call_selectRows_in_driver_and_return_self() {
    int[] rows = { 6, 8 };
    assertThat(fixture.selectRows(rows)).isSameAs(fixture);
    verify(driver).selectRows(target, rows);
  }

  @Test
  public void should_call_selectPath_in_driver_and_return_self() {
    assertThat(fixture.selectPath("root/child")).isSameAs(fixture);
    verify(driver).selectPath(target, "root/child");
  }

  @Test
  public void should_call_selectPaths_in_driver_and_return_self() {
    String[] paths = { "root", "root/child" };
    assertThat(fixture.selectPaths(paths)).isSameAs(fixture);
    verify(driver).selectPaths(target, paths);
  }

  @Test
  public void should_call_toggleRow_in_driver_and_return_self() {
    assertThat(fixture.toggleRow(6)).isSameAs(fixture);
    verify(driver).toggleRow(target, 6);
  }

  @Test
  public void should_call_expandRow_in_driver_and_return_self() {
    assertThat(fixture.expandRow(6)).isSameAs(fixture);
    verify(driver).expandRow(target, 6);
  }

  @Test
  public void should_call_collapseRow_in_driver_and_return_self() {
    assertThat(fixture.collapseRow(6)).isSameAs(fixture);
    verify(driver).collapseRow(target, 6);
  }

  @Test
  public void should_call_expandPath_in_driver_and_return_self() {
    assertThat(fixture.expandPath("root/child")).isSameAs(fixture);
    verify(driver).expandPath(target, "root/child");
  }

  @Test
  public void should_call_collapsePath_in_driver_and_return_self() {
    assertThat(fixture.collapsePath("root/child")).isSameAs(fixture);
    verify(driver).collapsePath(target, "root/child");
  }

  @Test
  public void should_return_JPopupMenu_at_row_using_driver() {
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(driver.showPopupMenu(target, 6)).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(6);
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).showPopupMenu(target, 6);
  }

  @Test
  public void should_return_JPopupMenu_at_path_using_driver() {
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(driver.showPopupMenu(target, "root/child")).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt("root/child");
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).showPopupMenu(target, "root/child");
  }

  @Test
  public void should_call_requireEditable_in_driver_and_return_self() {
    assertThat(fixture.requireEditable()).isSameAs(fixture);
    verify(driver).requireEditable(target);
  }

  @Test
  public void should_call_requireNotEditable_in_driver_and_return_self() {
    assertThat(fixture.requireNotEditable()).isSameAs(fixture);
    verify(driver).requireNotEditable(target);
  }

  @Test
  public void should_call_requireSelection_with_rows_in_driver_and_return_self() {
    int[] rows = { 6, 8 };
    assertThat(fixture.requireSelection(rows)).isSameAs(fixture);
    verify(driver).requireSelection(target, rows);
  }

  @Test
  public void should_call_requireSelection_with_paths_in_driver_and_return_self() {
    String[] paths = { "root", "root/child" };
    assertThat(fixture.requireSelection(paths)).isSameAs(fixture);
    verify(driver).requireSelection(target, paths);
  }

  @Test
  public void should_call_requireNoSelection_in_driver_and_return_self() {
    assertThat(fixture.requireNoSelection()).isSameAs(fixture);
    verify(driver).requireNoSelection(target);
  }

  @Test
  public void should_return_path_separator_using_driver() {
    when(driver.separator()).thenReturn(":");
    assertThat(fixture.separator()).isEqualTo(":");
    verify(driver).separator();
  }

  @Test
  public void should_call_replaceSeparator_in_driver_and_return_self() {
    assertThat(fixture.replaceSeparator(":")).isSameAs(fixture);
    verify(driver).replaceSeparator(":");
  }

  @Test
  public void should_call_replaceCellReader_in_driver_and_return_self() {
    JTreeCellReader reader = mock(JTreeCellReader.class);
    assertThat(fixture.replaceCellReader(reader)).isSameAs(fixture);
    verify(driver).replaceCellReader(reader);
  }

  @Test
  public void should_return_JTreeRowFixture() {
    JTreeRowFixture treeRowFixture = fixture.node(6);
    assertThat(treeRowFixture.treeFixture()).isSameAs(fixture);
    assertThat(treeRowFixture.index()).isEqualTo(6);
    verify(driver).checkRowInBounds(target, 6);
  }

  @Test
  public void should_return_JTreePathFixture() {
    JTreePathFixture treePathFixture = fixture.node("root/child");
    assertThat(treePathFixture.treeFixture()).isSameAs(fixture);
    assertThat(treePathFixture.path()).isEqualTo("root/child");
    verify(driver).checkPathExists(target, "root/child");
  }

  @Test
  public void should_return_value_at_row_using_driver() {
    when(driver.nodeValue(target, 6)).thenReturn("1");
    assertThat(fixture.valueAt(6)).isEqualTo("1");
    verify(driver).nodeValue(target, 6);
  }

  @Test
  public void should_return_value_at_path_using_driver() {
    when(driver.nodeValue(target, "root/child")).thenReturn("1");
    assertThat(fixture.valueAt("root/child")).isEqualTo("1");
    verify(driver).nodeValue(target, "root/child");
  }
}