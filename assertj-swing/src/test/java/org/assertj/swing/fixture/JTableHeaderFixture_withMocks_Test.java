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

import java.util.regex.Pattern;

import javax.swing.JPopupMenu;
import javax.swing.table.JTableHeader;

import org.assertj.swing.core.MouseClickInfo;
import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JTableHeaderDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JTableHeaderFixture}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JTableHeaderFixture_withMocks_Test {
  private JTableHeaderDriver driver;
  private JTableHeader target;

  private JTableHeaderFixture fixture;

  @Before
  public void setUp() {
    fixture = new JTableHeaderFixture(mock(Robot.class), mock(JTableHeader.class));
    fixture.replaceDriverWith(mock(JTableHeaderDriver.class));
    driver = fixture.driver();
    target = fixture.target();
  }

  @Test
  public void should_Call_ClickColumn_With_Index_In_Driver_And_Return_Self() {
    assertThat(fixture.clickColumn(6)).isSameAs(fixture);
    verify(driver).clickColumn(target, 6);
  }

  @Test
  public void should_Call_ClickColumn_With_Name_In_Driver_And_Return_Self() {
    assertThat(fixture.clickColumn("Hello")).isSameAs(fixture);
    verify(driver).clickColumn(target, "Hello");
  }

  @Test
  public void should_Call_ClickColumn_With_Pattern_In_Driver_And_Return_Self() {
    Pattern pattern = Pattern.compile("Hello");
    assertThat(fixture.clickColumn(pattern)).isSameAs(fixture);
    verify(driver).clickColumn(target, pattern);
  }

  @Test
  public void should_Call_ClickColumn_With_Index_And_MouseClickInfo_In_Driver_And_Return_Self() {
    MouseClickInfo clickInfo = middleButton().times(2);
    assertThat(fixture.clickColumn(6, clickInfo)).isSameAs(fixture);
    verify(driver).clickColumn(target, 6, MIDDLE_BUTTON, 2);
  }

  @Test
  public void should_Call_ClickColumn_With_Name_And_MouseClickInfo_In_Driver_And_Return_Self() {
    MouseClickInfo clickInfo = middleButton().times(2);
    assertThat(fixture.clickColumn("Hello", clickInfo)).isSameAs(fixture);
    verify(driver).clickColumn(target, "Hello", MIDDLE_BUTTON, 2);
  }

  @Test
  public void should_Call_ClickColumn_With_Pattern_And_MouseClickInfo_In_Driver_And_Return_Self() {
    MouseClickInfo clickInfo = middleButton().times(2);
    Pattern pattern = Pattern.compile("Hello");
    assertThat(fixture.clickColumn(pattern, clickInfo)).isSameAs(fixture);
    verify(driver).clickColumn(target, pattern, MIDDLE_BUTTON, 2);
  }

  @Test
  public void should_Return_JPopupMenu_With_Index_In_Driver() {
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(driver.showPopupMenu(target, 6)).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(6);
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).showPopupMenu(target, 6);
  }

  @Test
  public void should_Return_JPopupMenu_With_Name_In_Driver() {
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(driver.showPopupMenu(target, "Hello")).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt("Hello");
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).showPopupMenu(target, "Hello");
  }

  @Test
  public void should_Return_JPopupMenu_With_Pattern_In_Driver() {
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    Pattern pattern = Pattern.compile("Hello");
    when(driver.showPopupMenu(target, pattern)).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(pattern);
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).showPopupMenu(target, pattern);
  }
}
