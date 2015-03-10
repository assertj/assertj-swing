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

import javax.annotation.Nonnull;
import javax.swing.AbstractButton;

import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.AbstractButtonDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link AbstractTwoStateButtonFixture}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class AbstractTwoStateButtonFixture_withMocks_Test {
  private TwoStateButtonFixture fixture;

  @Before
  public void setUp() {
    fixture = new TwoStateButtonFixture();
  }

  @Test
  public void should_Call_Select_In_Driver_And_Return_Self() {
    assertThat(fixture.check()).isSameAs(fixture);
    verify(fixture.driver()).select(fixture.target());
  }

  @Test
  public void should_Call_Deselect_In_Driver_And_Return_Self() {
    assertThat(fixture.uncheck()).isSameAs(fixture);
    verify(fixture.driver()).deselect(fixture.target());
  }

  @Test
  public void should_Call_RequireSelected_In_Driver_And_Return_Self() {
    assertThat(fixture.requireSelected()).isSameAs(fixture);
    verify(fixture.driver()).requireSelected(fixture.target());
  }

  @Test
  public void should_Call_RequireNotSelected_In_Driver_And_Return_Self() {
    assertThat(fixture.requireNotSelected()).isSameAs(fixture);
    verify(fixture.driver()).requireNotSelected(fixture.target());
  }

  private static class TwoStateButtonFixture extends
      AbstractTwoStateButtonFixture<TwoStateButtonFixture, AbstractButton> {
    TwoStateButtonFixture() {
      super(TwoStateButtonFixture.class, mock(Robot.class), mock(AbstractButton.class));
    }

    @Override
    protected @Nonnull AbstractButtonDriver createDriver(@Nonnull Robot robot) {
      return mock(AbstractButtonDriver.class);
    }
  }
}
