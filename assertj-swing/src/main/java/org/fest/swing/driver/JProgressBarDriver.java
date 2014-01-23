/*
 * Created on Dec 1, 2009
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
package org.fest.swing.driver;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JProgressBarIndeterminateQuery.isIndeterminate;
import static org.fest.swing.driver.JProgressBarMinimumAndMaximumQuery.minimumAndMaximumOf;
import static org.fest.swing.driver.JProgressBarStringQuery.stringOf;
import static org.fest.swing.driver.JProgressBarValueQuery.valueOf;
import static org.fest.swing.driver.JProgressBarWaitUntilIsDeterminate.waitUntilValueIsDeterminate;
import static org.fest.swing.driver.JProgressBarWaitUntilValueIsEqualToExpectedTask.waitUntilValueIsEqualToExpected;
import static org.fest.swing.driver.TextAssert.verifyThat;
import static org.fest.swing.timing.Timeout.timeout;
import static org.fest.util.Preconditions.checkNotNull;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JProgressBar;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.timing.Timeout;
import org.fest.swing.util.Pair;
import org.fest.util.InternalApi;

/**
 * <p>
 * Supports functional testing of {@code JProgressBar}s.
 * </p>
 * 
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.fest.swing.fixture} in your tests.
 * </p>
 * 
 * @author Alex Ruiz
 * 
 * @since 1.2
 */
@InternalApi
public class JProgressBarDriver extends JComponentDriver implements TextDisplayDriver<JProgressBar> {
  private static final Timeout DEFAULT_TIMEOUT = timeout(30, SECONDS);
  private static final String TEXT_PROPERTY = "string";

  /**
   * Creates a new {@link JProgressBarDriver}.
   * 
   * @param robot the robot to use to simulate user input.
   */
  public JProgressBarDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Asserts that the text of the {@code JProgressBar} is equal to the specified {@code String}.
   * 
   * @param progressBar the target {@code JProgressBar}.
   * @param expected the text to match.
   * @throws AssertionError if the text of the {@code JProgressBar} is not equal to the given one.
   * @see JProgressBar#getString()
   */
  @RunsInEDT
  @Override
  public void requireText(@Nonnull JProgressBar progressBar, @Nullable String expected) {
    verifyThat(stringOf(progressBar)).as(propertyName(progressBar, TEXT_PROPERTY)).isEqualOrMatches(expected);
  }

  /**
   * Asserts that the text of the {@code JProgressBar} matches the given regular expression pattern.
   * 
   * @param progressBar the target {@code JProgressBar}.
   * @param pattern the regular expression pattern to match.
   * @throws AssertionError if the text of the {@code JProgressBar} does not match the given regular expression pattern.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @see JProgressBar#getString()
   */
  @RunsInEDT
  @Override
  public void requireText(@Nonnull JProgressBar progressBar, @Nonnull Pattern pattern) {
    verifyThat(stringOf(progressBar)).as(propertyName(progressBar, TEXT_PROPERTY)).matches(pattern);
  }

  /**
   * Verifies that the value of the given {@code JProgressBar} is equal to the given one.
   * 
   * @param progressBar the target {@code JProgressBar}.
   * @param value the expected value.
   * @throws AssertionError if the value of the {@code JProgressBar} is not equal to the given one.
   */
  @RunsInEDT
  public void requireValue(@Nonnull JProgressBar progressBar, int value) {
    assertThat(valueOf(progressBar)).as(propertyName(progressBar, "value")).isEqualTo(value);
  }

  /**
   * Verifies that the given {@code JProgressBar} is in indeterminate mode.
   * 
   * @param progressBar the target {@code JProgressBar}.
   * @throws AssertionError if the given {@code JProgressBar} is not in indeterminate mode.
   */
  @RunsInEDT
  public void requireIndeterminate(@Nonnull JProgressBar progressBar) {
    requireIndeterminate(progressBar, true);
  }

  /**
   * Verifies that the given {@code JProgressBar} is in determinate mode.
   * 
   * @param progressBar the target {@code JProgressBar}.
   * @throws AssertionError if the given {@code JProgressBar} is not in determinate mode.
   */
  @RunsInEDT
  public void requireDeterminate(@Nonnull JProgressBar progressBar) {
    requireIndeterminate(progressBar, false);
  }

  @RunsInEDT
  private void requireIndeterminate(@Nonnull JProgressBar progressBar, boolean indeterminate) {
    assertThat(isIndeterminate(progressBar)).as(propertyName(progressBar, "indeterminate")).isEqualTo(indeterminate);
  }

  /**
   * Waits until the value of the given {@code JProgressBar} is equal to the given value.
   * 
   * @param progressBar the target {@code JProgressBar}.
   * @param value the expected value.
   * @throws IllegalArgumentException if the given value is less than the {@code JProgressBar}'s minimum value.
   * @throws IllegalArgumentException if the given value is greater than the {@code JProgressBar}'s maximum value.
   * @throws WaitTimedOutError if the value of the {@code JProgressBar} does not reach the expected value within 30
   *           seconds.
   */
  @RunsInEDT
  public void waitUntilValueIs(@Nonnull JProgressBar progressBar, int value) {
    waitUntilValueIs(progressBar, value, DEFAULT_TIMEOUT);
  }

  /**
   * Waits until the value of the given {@code JProgressBar} is equal to the given value.
   * 
   * @param progressBar the target {@code JProgressBar}.
   * @param value the expected value.
   * @param timeout the amount of time to wait.
   * @throws IllegalArgumentException if the given value is less than the {@code JProgressBar}'s minimum value.
   * @throws IllegalArgumentException if the given value is greater than the {@code JProgressBar}'s maximum value.
   * @throws NullPointerException if the given timeout is {@code null}.
   * @throws WaitTimedOutError if the value of the {@code JProgressBar} does not reach the expected value within the
   *           specified timeout.
   */
  @RunsInEDT
  public void waitUntilValueIs(@Nonnull JProgressBar progressBar, int value, @Nonnull Timeout timeout) {
    checkInBetweenMinAndMax(progressBar, value);
    checkNotNull(timeout);
    waitUntilValueIsEqualToExpected(progressBar, value, timeout);
  }

  @RunsInEDT
  private void checkInBetweenMinAndMax(@Nonnull JProgressBar progressBar, int value) {
    Pair<Integer, Integer> minAndMax = minimumAndMaximumOf(progressBar);
    assertIsInBetweenMinAndMax(value, minAndMax.first, minAndMax.second);
  }

  private void assertIsInBetweenMinAndMax(int value, int min, int max) {
    if (value >= min && value <= max) {
      return;
    }
    String msg = String.format("Value <%d> should be between <[%d, %d]>", value, min, max);
    throw new IllegalArgumentException(msg);
  }

  /**
   * Waits until the value of the given {@code JProgressBar} is in determinate mode.
   * 
   * @param progressBar the target {@code JProgressBar}.
   * @throws WaitTimedOutError if the {@code JProgressBar} does not reach determinate mode within 30 seconds.
   */
  @RunsInEDT
  public void waitUntilIsDeterminate(@Nonnull JProgressBar progressBar) {
    waitUntilIsDeterminate(progressBar, DEFAULT_TIMEOUT);
  }

  /**
   * Waits until the value of the given {@code JProgressBar} is in determinate mode.
   * 
   * @param progressBar the target {@code JProgressBar}.
   * @param timeout the amount of time to wait.
   * @throws NullPointerException if the given timeout is {@code null}.
   * @throws WaitTimedOutError if the {@code JProgressBar} does not reach determinate mode within the specified timeout.
   */
  @RunsInEDT
  public void waitUntilIsDeterminate(@Nonnull JProgressBar progressBar, @Nonnull Timeout timeout) {
    checkNotNull(timeout);
    waitUntilValueIsDeterminate(progressBar, timeout);
  }

  /**
   * Returns the text of the given {@code JProgressBar}.
   * 
   * @param progressBar the target {@code JProgressBar}.
   * @return the text of the given {@code JProgressBar}.
   */
  @Override
  @RunsInEDT
  public @Nullable String textOf(@Nonnull JProgressBar progressBar) {
    return stringOf(progressBar);
  }
}
