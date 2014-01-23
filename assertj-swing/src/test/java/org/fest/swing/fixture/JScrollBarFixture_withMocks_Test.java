/*
 * Created on Dec 25, 2007
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

import javax.swing.JScrollBar;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JScrollBarDriver;
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
  public void should_call_scrollBlockDown_in_driver_and_return_self() {
    assertThat(fixture.scrollBlockDown()).isSameAs(fixture);
    verify(fixture.driver()).scrollBlockDown(fixture.target());
  }

  @Test
  public void should_call_scrollBlockDown_with_times_in_driver_and_return_self() {
    assertThat(fixture.scrollBlockDown(2)).isSameAs(fixture);
    verify(fixture.driver()).scrollBlockDown(fixture.target(), 2);
  }

  @Test
  public void should_call_scrollBlockUp_in_driver_and_return_self() {
    assertThat(fixture.scrollBlockUp()).isSameAs(fixture);
    verify(fixture.driver()).scrollBlockUp(fixture.target());
  }

  @Test
  public void should_call_scrollBlockUp_with_times_in_driver_and_return_self() {
    assertThat(fixture.scrollBlockUp(2)).isSameAs(fixture);
    verify(fixture.driver()).scrollBlockUp(fixture.target(), 2);
  }

  @Test
  public void should_call_scrollTo_in_driver_and_return_self() {
    assertThat(fixture.scrollTo(6)).isSameAs(fixture);
    verify(fixture.driver()).scrollTo(fixture.target(), 6);
  }

  @Test
  public void should_call_scrollToMaximum_in_driver_and_return_self() {
    assertThat(fixture.scrollToMaximum()).isSameAs(fixture);
    verify(fixture.driver()).scrollToMaximum(fixture.target());
  }

  @Test
  public void should_call_scrollToMinimum_in_driver_and_return_self() {
    assertThat(fixture.scrollToMinimum()).isSameAs(fixture);
    verify(fixture.driver()).scrollToMinimum(fixture.target());
  }

  @Test
  public void should_call_scrollUnitDown_in_driver_and_return_self() {
    assertThat(fixture.scrollUnitDown()).isSameAs(fixture);
    verify(fixture.driver()).scrollUnitDown(fixture.target());
  }

  @Test
  public void should_call_scrollUnitDown_with_times_in_driver_and_return_self() {
    assertThat(fixture.scrollUnitDown(2)).isSameAs(fixture);
    verify(fixture.driver()).scrollUnitDown(fixture.target(), 2);
  }

  @Test
  public void should_call_scrollUnitUp_in_driver_and_return_self() {
    assertThat(fixture.scrollUnitUp()).isSameAs(fixture);
    verify(fixture.driver()).scrollUnitUp(fixture.target());
  }

  @Test
  public void should_call_scrollUnitUp_with_times_in_driver_and_return_self() {
    assertThat(fixture.scrollUnitUp(2)).isSameAs(fixture);
    verify(fixture.driver()).scrollUnitUp(fixture.target(), 2);
  }

  @Test
  public void should_call_requireValue_in_driver_and_return_self() {
    assertThat(fixture.requireValue(6)).isSameAs(fixture);
    verify(fixture.driver()).requireValue(fixture.target(), 6);
  }
}
