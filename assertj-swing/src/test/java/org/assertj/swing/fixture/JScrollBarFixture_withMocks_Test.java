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

import javax.swing.JScrollBar;

import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JScrollBarDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JScrollBarFixture}.
 * 
 * @author Alex Ruiz
 */
public class JScrollBarFixture_withMocks_Test {
  private JScrollBarFixture fixture;

  @Before
  public void setUp() {
    fixture = new JScrollBarFixture(mock(Robot.class), mock(JScrollBar.class));
    fixture.replaceDriverWith(mock(JScrollBarDriver.class));
  }

  @Test
  public void should_Call_ScrollBlockDown_In_Driver_And_Return_Self() {
    assertThat(fixture.scrollBlockDown()).isSameAs(fixture);
    verify(fixture.driver()).scrollBlockDown(fixture.target());
  }

  @Test
  public void should_Call_ScrollBlockDown_With_Times_In_Driver_And_Return_Self() {
    assertThat(fixture.scrollBlockDown(2)).isSameAs(fixture);
    verify(fixture.driver()).scrollBlockDown(fixture.target(), 2);
  }

  @Test
  public void should_Call_ScrollBlockUp_In_Driver_And_Return_Self() {
    assertThat(fixture.scrollBlockUp()).isSameAs(fixture);
    verify(fixture.driver()).scrollBlockUp(fixture.target());
  }

  @Test
  public void should_Call_ScrollBlockUp_With_Times_In_Driver_And_Return_Self() {
    assertThat(fixture.scrollBlockUp(2)).isSameAs(fixture);
    verify(fixture.driver()).scrollBlockUp(fixture.target(), 2);
  }

  @Test
  public void should_Call_ScrollTo_In_Driver_And_Return_Self() {
    assertThat(fixture.scrollTo(6)).isSameAs(fixture);
    verify(fixture.driver()).scrollTo(fixture.target(), 6);
  }

  @Test
  public void should_Call_ScrollToMaximum_In_Driver_And_Return_Self() {
    assertThat(fixture.scrollToMaximum()).isSameAs(fixture);
    verify(fixture.driver()).scrollToMaximum(fixture.target());
  }

  @Test
  public void should_Call_ScrollToMinimum_In_Driver_And_Return_Self() {
    assertThat(fixture.scrollToMinimum()).isSameAs(fixture);
    verify(fixture.driver()).scrollToMinimum(fixture.target());
  }

  @Test
  public void should_Call_ScrollUnitDown_In_Driver_And_Return_Self() {
    assertThat(fixture.scrollUnitDown()).isSameAs(fixture);
    verify(fixture.driver()).scrollUnitDown(fixture.target());
  }

  @Test
  public void should_Call_ScrollUnitDown_With_Times_In_Driver_And_Return_Self() {
    assertThat(fixture.scrollUnitDown(2)).isSameAs(fixture);
    verify(fixture.driver()).scrollUnitDown(fixture.target(), 2);
  }

  @Test
  public void should_Call_ScrollUnitUp_In_Driver_And_Return_Self() {
    assertThat(fixture.scrollUnitUp()).isSameAs(fixture);
    verify(fixture.driver()).scrollUnitUp(fixture.target());
  }

  @Test
  public void should_Call_ScrollUnitUp_With_Times_In_Driver_And_Return_Self() {
    assertThat(fixture.scrollUnitUp(2)).isSameAs(fixture);
    verify(fixture.driver()).scrollUnitUp(fixture.target(), 2);
  }

  @Test
  public void should_Call_RequireValue_In_Driver_And_Return_Self() {
    assertThat(fixture.requireValue(6)).isSameAs(fixture);
    verify(fixture.driver()).requireValue(fixture.target(), 6);
  }
}
