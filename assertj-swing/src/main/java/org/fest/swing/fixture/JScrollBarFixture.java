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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JScrollBar;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JScrollBarDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JScrollBar}s.
 *
 * @author Alex Ruiz
 */
public class JScrollBarFixture extends
    AbstractJPopupMenuInvokerFixture<JScrollBarFixture, JScrollBar, JScrollBarDriver> {
  /**
   * Creates a new {@link JScrollBarFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JScrollBar}.
   * @param target the {@code JScrollBar} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JScrollBarFixture(@Nonnull Robot robot, @Nonnull JScrollBar target) {
    super(JScrollBarFixture.class, robot, target);
  }

  /**
   * Creates a new {@link JScrollBarFixture}.
   *
   * @param robot performs simulation of user events on a {@code JScrollBar}.
   * @param scrollBarName the name of the {@code JScrollBar} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JScrollBar} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JScrollBar} is found.
   */
  public JScrollBarFixture(@Nonnull Robot robot, @Nullable String scrollBarName) {
    super(JScrollBarFixture.class, robot, scrollBarName, JScrollBar.class);
  }

  @Override
  protected @Nonnull JScrollBarDriver createDriver(@Nonnull Robot robot) {
    return new JScrollBarDriver(robot);
  }

  /**
   * Simulates a user scrolling down one block (usually a page.)
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is disabled.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is not showing on the screen.
   */
  public @Nonnull JScrollBarFixture scrollBlockDown() {
    driver().scrollBlockDown(target());
    return this;
  }

  /**
   * Simulates a user scrolling down one block (usually a page,) the given number of times.
   *
   * @param times the number of times to scroll down one block.
   * @return this fixture.
   * @throws IllegalArgumentException if {@code times} is less than or equal to zero.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is disabled.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is not showing on the screen.
   */
  public @Nonnull JScrollBarFixture scrollBlockDown(int times) {
    driver().scrollBlockDown(target(), times);
    return this;
  }

  /**
   * Simulates a user scrolling up one block (usually a page.)
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is disabled.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is not showing on the screen.
   */
  public @Nonnull JScrollBarFixture scrollBlockUp() {
    driver().scrollBlockUp(target());
    return this;
  }

  /**
   * Simulates a user scrolling up one block (usually a page,) the given number of times.
   *
   * @param times the number of times to scroll up one block.
   * @return this fixture.
   * @throws IllegalArgumentException if {@code times} is less than or equal to zero.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is disabled.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is not showing on the screen.
   */
  public @Nonnull JScrollBarFixture scrollBlockUp(int times) {
    driver().scrollBlockUp(target(), times);
    return this;
  }

  /**
   * Simulates a user scrolling to the given position.
   *
   * @param position the position to scroll to.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is disabled.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is not showing on the screen.
   * @throws IllegalArgumentException if the given position is not within the {@code JScrollBar} bounds.
   */
  public @Nonnull JScrollBarFixture scrollTo(int position) {
    driver().scrollTo(target(), position);
    return this;
  }

  /**
   * Simulates a user scrolling to the maximum position of this fixture's {@code JScrollBar}.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is disabled.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is not showing on the screen.
   */
  public @Nonnull JScrollBarFixture scrollToMaximum() {
    driver().scrollToMaximum(target());
    return this;
  }

  /**
   * Simulates a user scrolling to the minimum position of this fixture's {@code JScrollBar}.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is disabled.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is not showing on the screen.
   */
  public @Nonnull JScrollBarFixture scrollToMinimum() {
    driver().scrollToMinimum(target());
    return this;
  }

  /**
   * Simulates a user scrolling down one unit (usually a line.)
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is disabled.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is not showing on the screen.
   */
  public @Nonnull JScrollBarFixture scrollUnitDown() {
    driver().scrollUnitDown(target());
    return this;
  }

  /**
   * Simulates a user scrolling down one unit (usually a line,) the given number of times.
   *
   * @param times the number of times to scroll down one unit.
   * @return this fixture.
   * @throws IllegalArgumentException if {@code times} is less than or equal to zero.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is disabled.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is not showing on the screen.
   */
  public @Nonnull JScrollBarFixture scrollUnitDown(int times) {
    driver().scrollUnitDown(target(), times);
    return this;
  }

  /**
   * Simulates a user scrolling up one unit (usually a line.)
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is disabled.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is not showing on the screen.
   */
  public @Nonnull JScrollBarFixture scrollUnitUp() {
    driver().scrollUnitUp(target());
    return this;
  }

  /**
   * Simulates a user scrolling up one unit (usually a line,) the given number of times.
   *
   * @param times the number of times to scroll up one unit.
   * @return this fixture.
   * @throws IllegalArgumentException if {@code times} is less than or equal to zero.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is disabled.
   * @throws IllegalStateException if this fixture's {@code JScrollBar} is not showing on the screen.
   */
  public @Nonnull JScrollBarFixture scrollUnitUp(int times) {
    driver().scrollUnitUp(target(), times);
    return this;
  }

  /**
   * Asserts that the value of this fixture's {@code JScrollBar} is equal to the given one.
   *
   * @param value the expected value.
   * @return this fixture.
   * @throws AssertionError if the value of this fixture's {@code JScrollBar} is not equal to the given one.
   */
  public @Nonnull JScrollBarFixture requireValue(int value) {
    driver().requireValue(target(), value);
    return this;
  }
}
