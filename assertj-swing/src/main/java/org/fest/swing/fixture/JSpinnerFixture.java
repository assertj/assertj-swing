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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JSpinner;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JSpinnerDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.UnexpectedException;

/**
 * Supports functional testing of {@code JSpinner}s:
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JSpinnerFixture extends AbstractJPopupMenuInvokerFixture<JSpinnerFixture, JSpinner, JSpinnerDriver> {
  /**
   * Creates a new {@link JSpinnerFixture}.
   *
   * @param robot performs simulation of user events on a {@code JSpinner}.
   * @param spinnerName the name of the {@code JSpinner} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JSpinner} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JSpinner} is found.
   */
  public JSpinnerFixture(@Nonnull Robot robot, @Nullable String spinnerName) {
    super(JSpinnerFixture.class, robot, spinnerName, JSpinner.class);
  }

  /**
   * Creates a new {@link JSpinnerFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JSpinner}.
   * @param target the {@code JSpinner} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JSpinnerFixture(@Nonnull Robot robot, @Nonnull JSpinner target) {
    super(JSpinnerFixture.class, robot, target);
  }

  @Override
  protected @Nonnull JSpinnerDriver createDriver(@Nonnull Robot robot) {
    return new JSpinnerDriver(robot);
  }

  /**
   * Simulates a user incrementing the value of this fixture's {@code JSpinner} the given number of times.
   *
   * @param times how many times the value of this fixture's {@code JSpinner} should be incremented.
   * @return this fixture.
   * @throws IllegalArgumentException if {@code times} is less than or equal to zero.
   * @throws IllegalStateException if this fixture's {@code JSpinner} is disabled.
   * @throws IllegalStateException if this fixture's {@code JSpinner} is not showing on the screen.
   */
  public @Nonnull JSpinnerFixture increment(int times) {
    driver().increment(target(), times);
    return this;
  }

  /**
   * Simulates a user incrementing the value of this fixture's {@code JSpinner} one time.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JSpinner} is disabled.
   * @throws IllegalStateException if this fixture's {@code JSpinner} is not showing on the screen.
   */
  public @Nonnull JSpinnerFixture increment() {
    driver().increment(target());
    return this;
  }

  /**
   * Simulates a user decrementing the value of this fixture's {@code JSpinner} the given number of times.
   *
   * @param times how many times the value of this fixture's {@code JSpinner} should be decremented.
   * @return this fixture.
   * @throws IllegalArgumentException if {@code times} is less than or equal to zero.
   * @throws IllegalStateException if this fixture's {@code JSpinner} is disabled.
   * @throws IllegalStateException if this fixture's {@code JSpinner} is not showing on the screen.
   */
  public @Nonnull JSpinnerFixture decrement(int times) {
    driver().decrement(target(), times);
    return this;
  }

  /**
   * Simulates a user decrementing the value of this fixture's {@code JSpinner} one time.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JSpinner} is disabled.
   * @throws IllegalStateException if this fixture's {@code JSpinner} is not showing on the screen.
   */
  public @Nonnull JSpinnerFixture decrement() {
    driver().decrement(target());
    return this;
  }

  /**
   * Simulates a user entering the given text in this fixture's {@code JSpinner} (assuming its editor has a
   * {@code JTextComponent} under it.) This method does not commit the value to the {@code JSpinner}.
   *
   * @param text the text to enter.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JSpinner} is disabled.
   * @throws IllegalStateException if this fixture's {@code JSpinner} is not showing on the screen.
   * @throws ActionFailedException if the editor of the {@code JSpinner} is not a {@code JTextComponent} or cannot be
   *           found.
   * @throws UnexpectedException if the entering the text in the {@code JSpinner}'s editor fails.
   */
  public @Nonnull JSpinnerFixture enterText(@Nonnull String text) {
    driver().enterText(target(), text);
    return this;
  }

  /**
   * Simulates a user entering and committing the given text in this fixture's {@code JSpinner} (assuming its editor has
   * a {@code JTextComponent} under it.)
   *
   * @param text the text to enter.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JSpinner} is disabled.
   * @throws IllegalStateException if this fixture's {@code JSpinner} is not showing on the screen.
   * @throws ActionFailedException if the editor of the {@code JSpinner} is not a {@code JTextComponent} or cannot be
   *           found.
   * @throws UnexpectedException if the entering the text in the {@code JSpinner}'s editor fails.
   */
  public @Nonnull JSpinnerFixture enterTextAndCommit(String text) {
    driver().enterTextAndCommit(target(), text);
    return this;
  }

  /**
   * Selects the given value in this fixture's {@code JSpinner}.
   *
   * @param value the value to select.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JSpinner} is disabled.
   * @throws IllegalStateException if this fixture's {@code JSpinner} is not showing on the screen.
   * @throws IllegalArgumentException if the {@code JSpinner} does not support the specified {@code value}.
   */
  public JSpinnerFixture select(@Nonnull Object value) {
    driver().selectValue(target(), value);
    return this;
  }

  /**
   * Verifies that the value of this fixture's {@code JSpinner} is equal to the given one.
   *
   * @param value the expected value of this fixture's {@code JSpinner}.
   * @return this fixture.
   * @throws AssertionError if the value of this fixture's {@code JSpinner} is not equal to the given one.
   */
  public @Nonnull JSpinnerFixture requireValue(@Nonnull Object value) {
    driver().requireValue(target(), value);
    return this;
  }

  /**
   * Returns the text displayed by this fixture's {@code JSpinner}. This method first tries to get the text displayed in
   * the {@code JSpinner}'s editor, assuming it is a {@code JTextComponent}. If the text from the editor cannot be
   * retrieved, it will return the {@code String} representation of the value in the {@code JSpinner}'s model.
   *
   * @return the text displayed by this fixture's {@code JSpinner}.
   * @since 1.2
   */
  public @Nullable String text() {
    return driver().textOf(target());
  }
}
