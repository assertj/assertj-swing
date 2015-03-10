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

import org.assertj.swing.core.MouseClickInfo;
import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JTreeRowFixture}.
 * 
 * @author Alex Ruiz
 */
public class JTreeRowFixture_withMocks_Test extends EDTSafeTestCase {
  private JTreeFixture treeFixture;
  private int index;

  private JTreeRowFixture fixture;

  @Before
  public void setUp() {
    fixture = new JTreeRowFixture(mock(JTreeFixture.class), 6);
    treeFixture = fixture.treeFixture();
    index = fixture.index();
  }

  @Test
  public void should_Call_ExpandRow_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.expand()).isSameAs(fixture);
    verify(treeFixture).expandRow(index);
  }

  @Test
  public void should_Call_CollapseRow_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.collapse()).isSameAs(fixture);
    verify(treeFixture).collapseRow(index);
  }

  @Test
  public void should_Call_SelectRow_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.select()).isSameAs(fixture);
    verify(treeFixture).selectRow(index);
  }

  @Test
  public void should_Call_ClickRow_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.click()).isSameAs(fixture);
    verify(treeFixture).clickRow(index);
  }

  @Test
  public void should_Call_ClickRow_With_MouseButtion_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.click(MIDDLE_BUTTON)).isSameAs(fixture);
    verify(treeFixture).clickRow(index, MIDDLE_BUTTON);
  }

  @Test
  public void should_Call_ClickRow_With_MouseClickInfo_In_JTreeFixture_And_Return_Self() {
    MouseClickInfo info = middleButton().times(3);
    assertThat(fixture.click(info)).isSameAs(fixture);
    verify(treeFixture).clickRow(index, info);
  }

  @Test
  public void should_Call_DoubleClickRow_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.doubleClick()).isSameAs(fixture);
    verify(treeFixture).doubleClickRow(index);
  }

  @Test
  public void should_Call_RightClickRow_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.rightClick()).isSameAs(fixture);
    verify(treeFixture).rightClickRow(index);
  }

  @Test
  public void should_Call_Drag_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.drag()).isSameAs(fixture);
    verify(treeFixture).drag(index);
  }

  @Test
  public void should_Call_Drop_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.drop()).isSameAs(fixture);
    verify(treeFixture).drop(index);
  }

  @Test
  public void should_Return_JPopupMenu_Using_JTreeFixture() {
    JPopupMenuFixture popupMenuFixture = mock(JPopupMenuFixture.class);
    when(treeFixture.showPopupMenuAt(index)).thenReturn(popupMenuFixture);
    assertThat(fixture.showPopupMenu()).isSameAs(popupMenuFixture);
    verify(treeFixture).showPopupMenuAt(index);
  }

  @Test
  public void should_Return_Value_Using_JTreeFixture() {
    when(treeFixture.valueAt(index)).thenReturn("Hello");
    assertThat(fixture.value()).isEqualTo("Hello");
    verify(treeFixture).valueAt(index);
  }
}