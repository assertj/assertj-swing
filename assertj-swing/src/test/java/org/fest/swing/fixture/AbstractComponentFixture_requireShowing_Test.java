/*
 * Created on Feb 18, 2013
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
 * Copyright @2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.ComponentLookupScope.ALL;
import static org.fest.swing.core.ComponentLookupScope.DEFAULT;
import static org.fest.swing.core.ComponentLookupScope.SHOWING_ONLY;
import static org.fest.util.Lists.newArrayList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.util.Collection;

import javax.annotation.Nonnull;

import org.fest.swing.core.ComponentLookupScope;
import org.fest.swing.core.Robot;
import org.fest.swing.core.Settings;
import org.fest.swing.driver.ComponentDriver;
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
  public void should_check_settings() {
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
    protected @Nonnull
    ComponentDriver createDriver(@Nonnull Robot robot) {
      return mock(ComponentDriver.class);
    }
  }
}
