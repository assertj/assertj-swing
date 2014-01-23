/*
 * Created on Dec 17, 2007
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JInternalFrame;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JInternalFrameDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JInternalFrameFixture}.
 *
 * @author Alex Ruiz
 */
public class JInternalFrameFixture_withMocks_Test {
  private JInternalFrameFixture fixture;

  @Before
  public void setUp() {
    fixture = new JInternalFrameFixture(mock(Robot.class), mock(JInternalFrame.class));
    fixture.replaceDriverWith(mock(JInternalFrameDriver.class));
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
  public void should_call_deiconify_in_driver_and_return_self() {
    assertThat(fixture.deiconify()).isSameAs(fixture);
    verify(fixture.driver()).deiconify(fixture.target());
  }

  @Test
  public void should_call_iconify_in_driver_and_return_self() {
    assertThat(fixture.iconify()).isSameAs(fixture);
    verify(fixture.driver()).iconify(fixture.target());
  }

  @Test
  public void should_call_maximize_in_driver_and_return_self() {
    assertThat(fixture.maximize()).isSameAs(fixture);
    verify(fixture.driver()).maximize(fixture.target());
  }

  @Test
  public void should_call_normalize_in_driver_and_return_self() {
    assertThat(fixture.normalize()).isSameAs(fixture);
    verify(fixture.driver()).normalize(fixture.target());
  }

  @Test
  public void should_call_close_in_driver() {
    fixture.close();
    verify(fixture.driver()).close(fixture.target());
  }

  @Test
  public void should_call_requireSize_in_driver_and_return_self() {
    Dimension size = new Dimension(6,  8);
    assertThat(fixture.requireSize(size)).isSameAs(fixture);
    verify(fixture.driver()).requireSize(fixture.target(), size);
  }

  @Test
  public void should_call_resizeWidthTo_in_driver_and_return_self() {
    assertThat(fixture.resizeWidthTo(6)).isSameAs(fixture);
    verify(fixture.driver()).resizeWidth(fixture.target(), 6);
  }

  @Test
  public void should_call_resizeHeightTo_in_driver_and_return_self() {
    assertThat(fixture.resizeHeightTo(6)).isSameAs(fixture);
    verify(fixture.driver()).resizeHeight(fixture.target(), 6);
  }

  @Test
  public void should_call_moveTo_in_driver_and_return_self() {
    Point p = new Point(6, 8);
    assertThat(fixture.moveTo(p)).isSameAs(fixture);
    verify(fixture.driver()).move(fixture.target(), p);
  }
}
