/*
 * Created on Jul 5, 2007
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

import java.awt.BorderLayout;
import java.awt.Point;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JToolBar;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JToolBarDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JToolBar}s.
 *
 * @author Alex Ruiz
 */
public class JToolBarFixture extends AbstractSwingContainerFixture<JToolBarFixture, JToolBar, JToolBarDriver> {
  /**
   * Constraints used to unfloat a floating {@code JToolBar}s.
   *
   * @author Alex Ruiz
   */
  public static enum UnfloatConstraint {
    NORTH(BorderLayout.NORTH), EAST(BorderLayout.EAST), SOUTH(BorderLayout.SOUTH), WEST(BorderLayout.WEST);

    private final String value;

    UnfloatConstraint(@Nonnull String value) {
      this.value = value;
    }

    public @Nonnull String value() {
      return value;
    }
  }

  /**
   * Creates a new {@link JToolBarFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JToolBar}.
   * @param target the {@code JToolBar} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JToolBarFixture(@Nonnull Robot robot, @Nonnull JToolBar target) {
    super(JToolBarFixture.class, robot, target);
  }

  /**
   * Creates a new {@link JToolBarFixture}.
   *
   * @param robot performs simulation of user events on a {@code JToolBar}.
   * @param toolbarName the name of the {@code JToolBar} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JToolBar} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JToolBar} is found.
   */
  public JToolBarFixture(@Nonnull Robot robot, @Nullable String toolbarName) {
    super(JToolBarFixture.class, robot, toolbarName, JToolBar.class);
  }

  @Override
  protected @Nonnull JToolBarDriver createDriver(@Nonnull Robot robot) {
    return new JToolBarDriver(robot);
  }

  /**
   * Simulates a user dragging this fixture's {@code JToolBar} to the given location, causing it to float.
   *
   * @param point the point where the {@code JToolBar} will be floating to.
   * @return this fixture.
   * @throws ActionFailedException if the {@code JToolBar} is not floatable.
   * @throws ActionFailedException if the {@code JToolBar} cannot be dragged.
   */
  public @Nonnull JToolBarFixture floatTo(@Nonnull Point point) {
    driver().floatTo(target(), point.x, point.y);
    return this;
  }

  /**
   * Simulates a user unfloating this fixture's {@code JToolBar}.
   *
   * @return this fixture.
   * @throws ActionFailedException if the dock container cannot be found.
   */
  public @Nonnull JToolBarFixture unfloat() {
    driver().unfloat(target());
    return this;
  }

  /**
   * Simulates a user dropping this fixture's {@code JToolBar} to the requested constraint position.
   *
   * @param constraint the constraint position.
   * @return this fixture.
   * @throws ActionFailedException if the dock container cannot be found.
   */
  public @Nonnull JToolBarFixture unfloat(@Nonnull UnfloatConstraint constraint) {
    driver().unfloat(target(), constraint.value());
    return this;
  }
}
