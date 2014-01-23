/*
 * Created on Jun 14, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.ComponentLookupScope.SHOWING_ONLY;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.awt.Frame;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.Robot;
import org.fest.swing.core.Settings;
import org.fest.swing.driver.ComponentDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link AbstractComponentFixture#AbstractComponentFixture(Class, Robot, String, Class)}.
 *
 * @author Alex Ruiz
 */
public class AbstractComponentFixture_constructor_withLookupByNameAndType_Test {
  private Robot robot;
  private String name;
  private Class<Frame> type;

  @Before
  public void setUp() {
    robot = mock(Robot.class);
    name = "frame";
    type = Frame.class;
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_selfType_is_null() {
    new ComponentFixture(null, robot, name, type);
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_robot_is_null() {
    new ComponentFixture(ComponentFixture.class, null, name, type);
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_class_is_null() {
    new ComponentFixture(ComponentFixture.class, robot, name, null);
  }

  @Test
  public void should_lookup_Component_by_name_and_type() {
    Frame frame = mock(type);
    Settings settings = mock(Settings.class);
    when(robot.settings()).thenReturn(settings);
    when(settings.componentLookupScope()).thenReturn(SHOWING_ONLY);
    ComponentFinder finder = mock(ComponentFinder.class);
    when(robot.finder()).thenReturn(finder);
    when(finder.findByName(name, type, true)).thenReturn(frame);
    ComponentFixture fixture = new ComponentFixture(ComponentFixture.class, robot, name, type);
    assertThat(fixture.robot()).isSameAs(robot);
    assertThat(fixture.target()).isSameAs(frame);
  }

  private static class ComponentFixture extends AbstractComponentFixture<ComponentFixture, Component, ComponentDriver> {
    public ComponentFixture(@Nonnull Class<ComponentFixture> selfType, @Nonnull Robot robot, @Nullable String name,
        @Nonnull Class<? extends Component> type) {
      super(selfType, robot, name, type);
    }

    @Override
    protected @Nonnull ComponentDriver createDriver(@Nonnull Robot robot) {
      return new ComponentDriver(robot);
    }
  }
}
