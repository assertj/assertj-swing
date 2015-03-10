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

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JInternalFrame;

import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JInternalFrameDriver;
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
  public void should_Call_MoveToFront_In_Driver_And_Return_Self() {
    assertThat(fixture.moveToFront()).isSameAs(fixture);
    verify(fixture.driver()).moveToFront(fixture.target());
  }

  @Test
  public void should_Call_MoveToBack_In_Driver_And_Return_Self() {
    assertThat(fixture.moveToBack()).isSameAs(fixture);
    verify(fixture.driver()).moveToBack(fixture.target());
  }

  @Test
  public void should_Call_Deiconify_In_Driver_And_Return_Self() {
    assertThat(fixture.deiconify()).isSameAs(fixture);
    verify(fixture.driver()).deiconify(fixture.target());
  }

  @Test
  public void should_Call_Iconify_In_Driver_And_Return_Self() {
    assertThat(fixture.iconify()).isSameAs(fixture);
    verify(fixture.driver()).iconify(fixture.target());
  }

  @Test
  public void should_Call_Maximize_In_Driver_And_Return_Self() {
    assertThat(fixture.maximize()).isSameAs(fixture);
    verify(fixture.driver()).maximize(fixture.target());
  }

  @Test
  public void should_Call_Normalize_In_Driver_And_Return_Self() {
    assertThat(fixture.normalize()).isSameAs(fixture);
    verify(fixture.driver()).normalize(fixture.target());
  }

  @Test
  public void should_Call_Close_In_Driver() {
    fixture.close();
    verify(fixture.driver()).close(fixture.target());
  }

  @Test
  public void should_Call_RequireSize_In_Driver_And_Return_Self() {
    Dimension size = new Dimension(6, 8);
    assertThat(fixture.requireSize(size)).isSameAs(fixture);
    verify(fixture.driver()).requireSize(fixture.target(), size);
  }

  @Test
  public void should_Call_ResizeWidthTo_In_Driver_And_Return_Self() {
    assertThat(fixture.resizeWidthTo(6)).isSameAs(fixture);
    verify(fixture.driver()).resizeWidth(fixture.target(), 6);
  }

  @Test
  public void should_Call_ResizeHeightTo_In_Driver_And_Return_Self() {
    assertThat(fixture.resizeHeightTo(6)).isSameAs(fixture);
    verify(fixture.driver()).resizeHeight(fixture.target(), 6);
  }

  @Test
  public void should_Call_MoveTo_In_Driver_And_Return_Self() {
    Point p = new Point(6, 8);
    assertThat(fixture.moveTo(p)).isSameAs(fixture);
    verify(fixture.driver()).move(fixture.target(), p);
  }
}
