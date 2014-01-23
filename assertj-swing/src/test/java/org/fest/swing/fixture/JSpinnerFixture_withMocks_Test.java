/*
 * Created on Jul 1, 2007
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
import static org.mockito.Mockito.when;

import javax.swing.JSpinner;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JSpinnerDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JSpinnerFixture}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JSpinnerFixture_withMocks_Test {
  private JSpinnerFixture fixture;

  @Before
  public void setUp() {
    fixture = new JSpinnerFixture(mock(Robot.class), mock(JSpinner.class));
    fixture.replaceDriverWith(mock(JSpinnerDriver.class));
  }

  @Test
  public void should_call_increment_in_driver_and_return_self() {
    assertThat(fixture.increment()).isSameAs(fixture);
    verify(fixture.driver()).increment(fixture.target());
  }

  @Test
  public void should_call_increment_with_times_in_driver_and_return_self() {
    assertThat(fixture.increment(6)).isSameAs(fixture);
    verify(fixture.driver()).increment(fixture.target(), 6);
  }

  @Test
  public void should_call_decrement_in_driver_and_return_self() {
    assertThat(fixture.decrement()).isSameAs(fixture);
    verify(fixture.driver()).decrement(fixture.target());
  }

  @Test
  public void should_call_decrement_with_times_in_driver_and_return_self() {
    assertThat(fixture.decrement(6)).isSameAs(fixture);
    verify(fixture.driver()).decrement(fixture.target(), 6);
  }

  @Test
  public void should_call_enterText_in_driver_and_return_self() {
    assertThat(fixture.enterText("Hello")).isSameAs(fixture);
    verify(fixture.driver()).enterText(fixture.target(), "Hello");
  }

  @Test
  public void should_call_enterTextAndCommit_in_driver_and_return_self() {
    assertThat(fixture.enterTextAndCommit("Hello")).isSameAs(fixture);
    verify(fixture.driver()).enterTextAndCommit(fixture.target(), "Hello");
  }

  @Test
  public void should_call_select_in_driver_and_return_self() {
    assertThat(fixture.select("Six")).isSameAs(fixture);
    verify(fixture.driver()).selectValue(fixture.target(), "Six");
  }

  @Test
  public void should_call_requireValue_in_driver_and_return_self() {
    assertThat(fixture.requireValue("Six")).isSameAs(fixture);
    verify(fixture.driver()).requireValue(fixture.target(), "Six");
  }

  @Test
  public void should_return_text_using_driver() {
    JSpinnerDriver driver = fixture.driver();
    JSpinner target = fixture.target();
    when(driver.textOf(target)).thenReturn("Six");
    assertThat(fixture.text()).isEqualTo("Six");
    verify(driver).textOf(target);
  }
}
