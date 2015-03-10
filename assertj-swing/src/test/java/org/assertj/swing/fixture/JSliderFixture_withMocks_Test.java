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

import javax.swing.JSlider;

import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JSliderDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JSliderFixture}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JSliderFixture_withMocks_Test {
  private JSliderFixture fixture;

  @Before
  public void setUp() {
    fixture = new JSliderFixture(mock(Robot.class), mock(JSlider.class));
    fixture.replaceDriverWith(mock(JSliderDriver.class));
  }

  @Test
  public void should_Call_SlideTo_In_Driver_And_Return_Self() {
    assertThat(fixture.slideTo(6)).isSameAs(fixture);
    verify(fixture.driver()).slide(fixture.target(), 6);
  }

  @Test
  public void should_Call_SlideToMaximum_In_Driver_And_Return_Self() {
    assertThat(fixture.slideToMaximum()).isSameAs(fixture);
    verify(fixture.driver()).slideToMaximum(fixture.target());
  }

  @Test
  public void should_Call_SlideToMinimum_In_Driver_And_Return_Self() {
    assertThat(fixture.slideToMinimum()).isSameAs(fixture);
    verify(fixture.driver()).slideToMinimum(fixture.target());
  }
}
