/*
 * Created on Feb 17, 2013
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
 * Copyright @2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;

import javax.annotation.Nonnull;
import javax.swing.JPopupMenu;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.WindowDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link AbstractWindowFixture}.
 *
 * @author Alex Ruiz
 */
public class AbstractWindowFixture_withMocks_Test {
  private WindowFixture fixture;

  @Before
  public void setUp() {
    fixture = new WindowFixture();
  }

  @Test
  public void should_call_moveTo_in_driver_and_return_self() {
    Point p = new Point(6, 8);
    assertThat(fixture.moveTo(p)).isSameAs(fixture);
    verify(fixture.driver()).moveTo(fixture.target(), p);
  }

  @Test
  public void should_call_moveToFront_in_driver_and_return_self() {
    assertThat(fixture.moveToFront()).isSameAs(fixture);
    verify(fixture.driver()).moveToFront(fixture.target());
  }

  @Test
  public void should_call_moveToBack_in_driver_and_return_self() {
    assertThat(fixture.moveToBack()).isSameAs(fixture);
    verify(fixture.driver()).moveToBack(fixture.target());
  }

  @Test
  public void should_call_requireSize_in_driver_and_return_self() {
    Dimension size = new Dimension(6, 8);
    assertThat(fixture.requireSize(size)).isSameAs(fixture);
    verify(fixture.driver()).requireSize(fixture.target(), size);
  }

  @Test
  public void should_call_resizeHeightTo_in_driver_and_return_self() {
    assertThat(fixture.resizeHeightTo(6)).isSameAs(fixture);
    verify(fixture.driver()).resizeHeightTo(fixture.target(), 6);
  }

  @Test
  public void should_call_resizeWidthTo_in_driver_and_return_self() {
    assertThat(fixture.resizeWidthTo(6)).isSameAs(fixture);
    verify(fixture.driver()).resizeWidthTo(fixture.target(), 6);
  }

  @Test
  public void should_call_resizeTo_in_driver_and_return_self() {
    Dimension size = new Dimension(6, 8);
    assertThat(fixture.resizeTo(size)).isSameAs(fixture);
    verify(fixture.driver()).resizeTo(fixture.target(), size);
  }

  @Test
  public void should_call_show_in_driver_and_return_self() {
    assertThat(fixture.show()).isSameAs(fixture);
    verify(fixture.driver()).show(fixture.target());
  }

  @Test
  public void should_call_show_with_size_in_driver_and_return_self() {
    Dimension size = new Dimension(6, 8);
    assertThat(fixture.show(size)).isSameAs(fixture);
    verify(fixture.driver()).show(fixture.target(), size);
  }

  @Test
  public void should_call_invokePopupMenu_in_driver() {
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(fixture.driver().invokePopupMenu(fixture.target())).thenReturn(popupMenu);
    JPopupMenuFixture result = fixture.showPopupMenu();
    assertThat(result.target()).isSameAs(popupMenu);
  }

  @Test
  public void should_call_invokePopupMenu_with_location_in_driver() {
    Point p = new Point(6, 8);
    JPopupMenu popupMenu = mock(JPopupMenu.class);
    when(fixture.driver().invokePopupMenu(fixture.target(), p)).thenReturn(popupMenu);
    JPopupMenuFixture result = fixture.showPopupMenuAt(p);
    assertThat(result.target()).isSameAs(popupMenu);
  }

  @Test
  public void should_call_close_in_driver() {
    fixture.close();
    verify(fixture.driver()).close(fixture.target());
  }

  @Test
  public void should_call_cleanUp_in_robot() {
    fixture.cleanUp();
    verify(fixture.robot()).cleanUp();
  }

  private static class WindowFixture extends AbstractWindowFixture<WindowFixture, Window, WindowDriver> {
    WindowFixture() {
      super(WindowFixture.class, mock(Robot.class), mock(Window.class));
    }

    @Override
    protected @Nonnull WindowDriver createDriver(@Nonnull Robot robot) {
      return mock(WindowDriver.class);
    }
  }
}
