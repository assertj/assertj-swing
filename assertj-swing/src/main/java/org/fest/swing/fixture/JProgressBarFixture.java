/*
 * Created on Dec 19, 2009
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

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JProgressBar;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JProgressBarDriver;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.timing.Timeout;

/**
 * Supports functional testing of {@code JProgressBar}s.
 *
 * @author Alex Ruiz
 *
 * @since 1.2
 */
public class JProgressBarFixture extends
    AbstractJComponentFixture<JProgressBarFixture, JProgressBar, JProgressBarDriver> implements
    TextDisplayFixture<JProgressBarFixture> {
  /**
   * Creates a new {@link JProgressBarFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JProgressBar}.
   * @param target the {@code JProgressBar} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JProgressBarFixture(@Nonnull Robot robot, @Nonnull JProgressBar target) {
    super(JProgressBarFixture.class, robot, target);
  }

  /**
   * Creates a new {@link JProgressBarFixture}.
   *
   * @param robot performs simulation of user events on a {@code JProgressBar}.
   * @param labelName the name of the {@code JProgressBar} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JProgressBar} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JProgressBar} is found.
   */
  public JProgressBarFixture(@Nonnull Robot robot, @Nonnull String labelName) {
    super(JProgressBarFixture.class, robot, labelName, JProgressBar.class);
  }

  @Override
  protected @Nonnull JProgressBarDriver createDriver(@Nonnull Robot robot) {
    return new JProgressBarDriver(robot);
  }


  /**
   * Asserts that the value of this fixture's {@code JProgressBar} is equal to the given one.
   *
   * @param value the expected value.
   * @return this fixture.
   * @throws AssertionError if the value of this fixture's {@code JProgressBar} is not equal to the given one.
   */
  public @Nonnull JProgressBarFixture requireValue(int value) {
    driver().requireValue(target(), value);
    return this;
  }

  /**
   * Asserts that this fixture's {@code JProgressBar} is in determinate mode.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code JProgressBar} is not in determinate mode.
   */
  public @Nonnull JProgressBarFixture requireDeterminate() {
    driver().requireDeterminate(target());
    return this;
  }

  /**
   * Asserts that this fixture's {@code JProgressBar} is in indeterminate mode.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code JProgressBar} is not in indeterminate mode.
   */
  public @Nonnull JProgressBarFixture requireIndeterminate() {
    driver().requireIndeterminate(target());
    return this;
  }

  /**
   * @return the text of this fixture's {@code JProgressBar}.
   */
  @Override
  public String text() {
    return driver().textOf(target());
  }

  /**
   * Asserts that the text of this fixture's {@code JProgressBar} is equal to the specified {@code String}.
   *
   * @param expected the text to match.
   * @return this fixture.
   * @throws AssertionError if the text of this fixture's {@code JProgressBar} is not equal to the given one.
   */
  @Override
  public @Nonnull JProgressBarFixture requireText(@Nullable String expected) {
    driver().requireText(target(), expected);
    return this;
  }

  /**
   * Asserts that the text of this fixture's {@code JProgressBar} matches the given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws AssertionError if the text of this fixture's {@code JProgressBar} does not match the given regular
   *           expression pattern.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   */
  @Override
  public @Nonnull JProgressBarFixture requireText(@Nonnull Pattern pattern) {
    driver().requireText(target(), pattern);
    return this;
  }

  /**
   * Waits until the value of this fixture's {@code JProgressBar} is equal to the given value.
   *
   * @param value the expected value.
   * @return this fixture.
   * @throws IllegalArgumentException if the given value is less than the {@code JProgressBar}'s minimum value.
   * @throws IllegalArgumentException if the given value is greater than the {@code JProgressBar}'s maximum value.
   * @throws WaitTimedOutError if the value of the {@code JProgressBar} does not reach the expected value within 30
   *           seconds.
   */
  public @Nonnull JProgressBarFixture waitUntilValueIs(int value) {
    driver().waitUntilValueIs(target(), value);
    return this;
  }

  /**
   * Waits until the value of this fixture's {@code JProgressBar} is equal to the given value.
   *
   * @param value the expected value.
   * @param timeout the amount of time to wait.
   * @return this fixture.
   * @throws IllegalArgumentException if the given value is less than the {@code JProgressBar}'s minimum value.
   * @throws IllegalArgumentException if the given value is greater than the {@code JProgressBar}'s maximum value.
   * @throws NullPointerException if the given timeout is {@code null}.
   * @throws WaitTimedOutError if the value of the {@code JProgressBar} does not reach the expected value within the
   *           specified timeout.
   */
  public @Nonnull JProgressBarFixture waitUntilValueIs(int value, @Nonnull Timeout timeout) {
    driver().waitUntilValueIs(target(), value, timeout);
    return this;
  }

  /**
   * Waits until the value of this fixture's {@code JProgressBar} is in determinate mode.
   *
   * @return this fixture.
   * @throws WaitTimedOutError if the {@code JProgressBar} does not reach determinate mode within 30 seconds.
   */
  public @Nonnull JProgressBarFixture waitUntilIsDeterminate() {
    driver().waitUntilIsDeterminate(target());
    return this;
  }

  /**
   * Waits until the value of this fixture's {@code JProgressBar} is in determinate mode.
   *
   * @param timeout the amount of time to wait.
   * @return this fixture.
   * @throws NullPointerException if the given timeout is {@code null}.
   * @throws WaitTimedOutError if the {@code JProgressBar} does not reach determinate mode within the specified timeout.
   */
  public @Nonnull JProgressBarFixture waitUntilIsDeterminate(@Nonnull Timeout timeout) {
    driver().waitUntilIsDeterminate(target(), timeout);
    return this;
  }
}
