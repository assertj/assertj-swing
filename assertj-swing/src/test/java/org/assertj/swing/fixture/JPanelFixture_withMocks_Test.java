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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Point;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JComponentDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JPanelFixture}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JPanelFixture_withMocks_Test {
  private JPanel target;
  private JComponentDriver driver;

  private JPanelFixture fixture;

  @Before
  public void setUp() {
    fixture = new JPanelFixture(mock(Robot.class), mock(JPanel.class));
    fixture.replaceDriverWith(mock(JComponentDriver.class));
    driver = fixture.driver();
    target = fixture.target();
  }

  @Test
  public void should_Call_RequireToolTip_With_Text_In_Driver_And_Return_Self() {
    assertThat(fixture.requireToolTip("Hello")).isSameAs(fixture);
    verify(driver).requireToolTip(target, "Hello");
  }

  @Test
  public void should_Call_RequireToolTip_With_Pattern_In_Driver_And_Return_Self() {
    Pattern pattern = Pattern.compile("Hello");
    assertThat(fixture.requireToolTip(pattern)).isSameAs(fixture);
    verify(driver).requireToolTip(target, pattern);
  }

  @Test
  public void should_Return_Client_Property_Using_Driver() {
    when(driver.clientProperty(target, "name")).thenReturn("Yoda");
    assertThat(fixture.clientProperty("name")).isEqualTo("Yoda");
    verify(driver).clientProperty(target, "name");
  }

  @Test
  public void should_Show_JPopupMenu_Using_Driver() {
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(driver.invokePopupMenu(target)).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenu();
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).invokePopupMenu(target);
  }

  @Test
  public void should_Show_JPopupMenu_At_Location_Using_Driver() {
    Point p = new Point(6, 8);
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(driver.invokePopupMenu(target, p)).thenReturn(popupMenu);
    JPopupMenuFixture popupMenuFixture = fixture.showPopupMenuAt(p);
    assertThat(popupMenuFixture.target()).isSameAs(popupMenu);
    verify(driver).invokePopupMenu(target, p);
  }
}
