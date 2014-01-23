/*
 * Created on Nov 18, 2009
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

import javax.annotation.Nonnull;
import javax.swing.AbstractButton;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.AbstractButtonDriver;
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
  public void should_call_select_in_driver_and_return_self() {
    assertThat(fixture.check()).isSameAs(fixture);
    verify(fixture.driver()).select(fixture.target());
  }

  @Test
  public void should_call_deselect_in_driver_and_return_self() {
    assertThat(fixture.uncheck()).isSameAs(fixture);
    verify(fixture.driver()).deselect(fixture.target());
  }

  @Test
  public void should_call_requireSelected_in_driver_and_return_self() {
    assertThat(fixture.requireSelected()).isSameAs(fixture);
    verify(fixture.driver()).requireSelected(fixture.target());
  }

  @Test
  public void should_call_requireNotSelected_in_driver_and_return_self() {
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
