/*
 * Created on Sep 10, 2007
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
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.core.MouseClickInfo.middleButton;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JListItemFixture}.
 *
 * @author Alex Ruiz
 */
public class JListItemFixture_withMocks_Test {
  private JListFixture list;
  private int index;

  private JListItemFixture fixture;

  @Before
  public void setUp() {
    list = mock(JListFixture.class);
    index = 8;
    fixture = new JListItemFixture(list, index);
  }

  @Test
  public void should_call_selectItem_in_JListFixture_and_return_self() {
    assertThat(fixture.select()).isSameAs(fixture);
    verify(list).selectItem(index);
  }

  @Test
  public void should_call_clickItem_in_JListFixture_and_return_self() {
    assertThat(fixture.click()).isSameAs(fixture);
    verify(list).clickItem(index);
  }

  @Test
  public void should_call_clickItem_with_MouseButton_in_JListFixture_and_return_self() {
    assertThat(fixture.click(MIDDLE_BUTTON)).isSameAs(fixture);
    verify(list).clickItem(index, MIDDLE_BUTTON, 1);
  }

  @Test
  public void should_call_clickItem_with_MouseClickInfo_in_JListFixture_and_return_self() {
    assertThat(fixture.click(middleButton().times(2))).isSameAs(fixture);
    verify(list).clickItem(index, MIDDLE_BUTTON, 2);
  }

  @Test
  public void should_call_clickItem_with_left_button_two_times_in_JListFixture_and_return_self() {
    assertThat(fixture.doubleClick()).isSameAs(fixture);
    verify(list).clickItem(index, LEFT_BUTTON, 2);
  }

  @Test
  public void should_call_clickItem_with_right_button_one_time_in_JListFixture_and_return_self() {
    assertThat(fixture.rightClick()).isSameAs(fixture);
    verify(list).clickItem(index, RIGHT_BUTTON, 1);
  }

  @Test
  public void should_call_showPopupMenuAt_in_JListFixture_and_return_self() {
    JPopupMenuFixture popupMenu = mock(JPopupMenuFixture.class);
    when(list.showPopupMenuAt(index)).thenReturn(popupMenu);
    assertThat(fixture.showPopupMenu()).isSameAs(popupMenu);
    verify(list).showPopupMenuAt(index);
  }

  @Test
  public void should_return_value_using_JListFixture() {
    when(list.valueAt(index)).thenReturn("One");
    assertThat(fixture.value()).isEqualTo("One");
    verify(list).valueAt(index);
  }

  @Test
  public void should_call_drag_in_JListFixture_and_return_self() {
    assertThat(fixture.drag()).isSameAs(fixture);
    verify(list).drag(index);
  }

  @Test
  public void should_call_drop_in_JListFixture_and_return_self() {
    assertThat(fixture.drop()).isSameAs(fixture);
    verify(list).drop(index);
  }
}
