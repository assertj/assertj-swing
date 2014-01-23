/*
 * Created on Nov 17, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.Frame;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.FrameDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link FrameFixture}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FrameFixture_withMocks_Test {
  private FrameFixture fixture;

  @Before
  public void setUp() {
    fixture = new FrameFixture(mock(Robot.class), mock(Frame.class));
    fixture.replaceDriverWith(mock(FrameDriver.class));
  }

  @Test
  public void should_call_iconify_in_driver_and_return_self() {
    assertThat(fixture.iconify()).isSameAs(fixture);
    verify(fixture.driver()).iconify(fixture.target());
  }

  @Test
  public void should_call_deiconify_in_driver_and_return_self() {
    assertThat(fixture.deiconify()).isSameAs(fixture);
    verify(fixture.driver()).deiconify(fixture.target());
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
}
