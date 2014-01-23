/*
 * Created on Dec 27, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.MIDDLE_BUTTON;
import static org.fest.swing.core.MouseClickInfo.middleButton;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.fest.swing.core.MouseClickInfo;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JTreePathFixture}.
 *
 * @author Alex Ruiz
 */
public class JTreePathFixture_withMocks_Test {
  private JTreeFixture treeFixture;
  private String path;

  private JTreePathFixture fixture;

  @Before
  public void setUp() {
    fixture = new JTreePathFixture(mock(JTreeFixture.class), "root/child");
    treeFixture = fixture.treeFixture();
    path = fixture.path();
  }

  @Test
  public void should_call_expandPath_in_JTreeFixture_and_return_self() {
    assertThat(fixture.expand()).isSameAs(fixture);
    verify(treeFixture).expandPath(path);
  }

  @Test
  public void should_call_collapsePath_in_JTreeFixture_and_return_self() {
    assertThat(fixture.collapse()).isSameAs(fixture);
    verify(treeFixture).collapsePath(path);
  }

  @Test
  public void should_call_selectPath_in_JTreeFixture_and_return_self() {
    assertThat(fixture.select()).isSameAs(fixture);
    verify(treeFixture).selectPath(path);
  }

  @Test
  public void should_call_clickPath_in_JTreeFixture_and_return_self() {
    assertThat(fixture.click()).isSameAs(fixture);
    verify(treeFixture).clickPath(path);
  }

  @Test
  public void should_call_clickPath_with_MouseButtion_in_JTreeFixture_and_return_self() {
    assertThat(fixture.click(MIDDLE_BUTTON)).isSameAs(fixture);
    verify(treeFixture).clickPath(path, MIDDLE_BUTTON);
  }

  @Test
  public void should_call_clickPath_with_MouseClickInfo_in_JTreeFixture_and_return_self() {
    MouseClickInfo info = middleButton().times(3);
    assertThat(fixture.click(info)).isSameAs(fixture);
    verify(treeFixture).clickPath(path, info);
  }

  @Test
  public void should_call_doubleClickPath_in_JTreeFixture_and_return_self() {
    assertThat(fixture.doubleClick()).isSameAs(fixture);
    verify(treeFixture).doubleClickPath(path);
  }

  @Test
  public void should_call_rightClickPath_in_JTreeFixture_and_return_self() {
    assertThat(fixture.rightClick()).isSameAs(fixture);
    verify(treeFixture).rightClickPath(path);
  }

  @Test
  public void should_call_drag_in_JTreeFixture_and_return_self() {
    assertThat(fixture.drag()).isSameAs(fixture);
    verify(treeFixture).drag(path);
  }

  @Test
  public void should_call_drop_in_JTreeFixture_and_return_self() {
    assertThat(fixture.drop()).isSameAs(fixture);
    verify(treeFixture).drop(path);
  }

  @Test
  public void should_return_JPopupMenu_using_JTreeFixture() {
    JPopupMenuFixture popupMenuFixture = mock(JPopupMenuFixture.class);
    when(treeFixture.showPopupMenuAt(path)).thenReturn(popupMenuFixture);
    assertThat(fixture.showPopupMenu()).isSameAs(popupMenuFixture);
    verify(treeFixture).showPopupMenuAt(path);
  }

  @Test
  public void should_return_value_using_JTreeFixture() {
    when(treeFixture.valueAt(path)).thenReturn("Hello");
    assertThat(fixture.value()).isEqualTo("Hello");
    verify(treeFixture).valueAt(path);
  }
}