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
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.core.ComponentLookupScope.ALL;
import static org.assertj.swing.core.ComponentLookupScope.DEFAULT;
import static org.assertj.swing.core.ComponentLookupScope.SHOWING_ONLY;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.util.Collection;

import javax.annotation.Nonnull;

import org.assertj.swing.core.ComponentLookupScope;
import org.assertj.swing.core.Robot;
import org.assertj.swing.core.Settings;
import org.assertj.swing.driver.ComponentDriver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link AbstractComponentFixture#requireShowing()}.
 * 
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class AbstractComponentFixture_requireShowing_Test {
  @Parameters
  public static Collection<Object[]> parameters() {
    return newArrayList(new Object[][] { { ALL }, { DEFAULT }, { SHOWING_ONLY } });
  }

  private final ComponentLookupScope scope;

  public AbstractComponentFixture_requireShowing_Test(ComponentLookupScope scope) {
    this.scope = scope;
  }

  private Settings settings;
  private ConcreteComponentFixture fixture;

  @Before
  public void setUp() {
    settings = mock(Settings.class);
    fixture = new ConcreteComponentFixture();
  }

  @Test
  public void should_Check_Settings() {
    when(fixture.robot().settings()).thenReturn(settings);
    when(settings.componentLookupScope()).thenReturn(scope);
    assertThat(fixture.requireShowing()).isEqualTo(scope.requireShowing());
  }

  private static class ConcreteComponentFixture extends
      AbstractComponentFixture<ConcreteComponentFixture, Component, ComponentDriver> {
    public ConcreteComponentFixture() {
      super(ConcreteComponentFixture.class, mock(Robot.class), mock(Component.class));
    }

    @Override
    protected @Nonnull ComponentDriver createDriver(@Nonnull Robot robot) {
      return mock(ComponentDriver.class);
    }
  }
}
