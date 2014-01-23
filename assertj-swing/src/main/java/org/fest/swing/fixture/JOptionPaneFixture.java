/*
 * Created on Oct 20, 2006
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
 * Copyright @2006-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.util.Preconditions.checkNotNull;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JOptionPane;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JOptionPaneDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JOptionPane}s.
 *
 * @author Alex Ruiz
 */
public class JOptionPaneFixture extends AbstractContainerFixture<JOptionPaneFixture, JOptionPane, JOptionPaneDriver> {
  /**
   * Creates a new {@link JOptionPaneFixture}.
   *
   * @param robot finds a showing {@code JOptionPane}, which will be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a showing {@code JOptionPane} could not be found.
   * @throws ComponentLookupException if more than one showing {@code JOptionPane} is found.
   */
  public JOptionPaneFixture(@Nonnull Robot robot) {
    this(robot, findShowingOptionPane(robot));
  }

  private static @Nonnull JOptionPane findShowingOptionPane(@Nonnull Robot robot) {
    checkNotNull(robot);
    return robot.finder().findByType(JOptionPane.class, true);
  }

  /**
   * Creates a new {@link JOptionPaneFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JOptionPane}.
   * @param target the {@code JOptionPane} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws IllegalArgumentException if {@code target} is {@code null}.
   */
  public JOptionPaneFixture(@Nonnull Robot robot, @Nonnull JOptionPane target) {
    super(JOptionPaneFixture.class, robot, target);
  }

  @Override
  protected @Nonnull JOptionPaneDriver createDriver(@Nonnull Robot robot) {
    return new JOptionPaneDriver(robot);
  }

  /**
   * @return the title of this fixture's {@code JOptionPane}.
   * @since 1.2
   */
  public @Nullable String title() {
    return driver().title(target());
  }

  /**
   * Returns a fixture wrapping the "OK" button in this fixture's {@code JOptionPane}. This method is locale-independent
   * and platform-independent.
   *
   * @return a fixture wrapping the "OK" button.
   * @throws ComponentLookupException if the a "OK" button cannot be found.
   */
  public @Nonnull JButtonFixture okButton() {
    return new JButtonFixture(robot(), driver().okButton(target()));
  }

  /**
   * Returns a fixture wrapping the "Cancel" button in this fixture's {@code JOptionPane}. This method is
   * locale-independent and platform-independent.
   *
   * @return a fixture wrapping the "Cancel" button.
   * @throws ComponentLookupException if the a "Cancel" button cannot be found.
   */
  public @Nonnull JButtonFixture cancelButton() {
    return new JButtonFixture(robot(), driver().cancelButton(target()));
  }

  /**
   * Returns a fixture wrapping the "Yes" button in this fixture's {@code JOptionPane}. This method is
   * locale-independent and platform-independent.
   *
   * @return a fixture wrapping the "Yes" button.
   * @throws ComponentLookupException if the a "Yes" button cannot be found.
   */
  public @Nonnull JButtonFixture yesButton() {
    return new JButtonFixture(robot(), driver().yesButton(target()));
  }

  /**
   * Returns a fixture wrapping the "No" button in this fixture's {@code JOptionPane}. This method is locale-independent
   * and platform-independent.
   *
   * @return a fixture wrapping the "No" button.
   * @throws ComponentLookupException if the a "No" button cannot be found.
   */
  public @Nonnull JButtonFixture noButton() {
    return new JButtonFixture(robot(), driver().noButton(target()));
  }

  /**
   * Finds and returns a fixture wrapping a button (this fixture's {@code JOptionPane}) matching the given text.
   *
   * @param text the text of the button to find. It can be a regular expression.
   * @return a fixture wrapping a button matching the given text.
   * @throws ComponentLookupException if the a button with the given text cannot be found.
   */
  public @Nonnull JButtonFixture buttonWithText(@Nullable String text) {
    return new JButtonFixture(robot(), driver().buttonWithText(target(), text));
  }

  /**
   * Finds and returns a fixture wrapping a button (this fixture's {@code JOptionPane}) matching the given text.
   *
   * @param pattern the regular expression pattern to match.
   * @return a fixture wrapping a button matching the given regular expression pattern.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws ComponentLookupException if the a button with the given text cannot be found.
   * @since 1.2
   */
  public @Nonnull JButtonFixture buttonWithText(@Nonnull Pattern pattern) {
    return new JButtonFixture(robot(), driver().buttonWithText(target(), pattern));
  }

  /**
   * Asserts that this fixture's {@code JOptionPane} is displaying an error message.
   *
   * @return this fixture.
   */
  public @Nonnull JOptionPaneFixture requireErrorMessage() {
    driver().requireErrorMessage(target());
    return this;
  }

  /**
   * Asserts that this fixture's {@code JOptionPane} is displaying an information message.
   *
   * @return this fixture.
   */
  public @Nonnull JOptionPaneFixture requireInformationMessage() {
    driver().requireInformationMessage(target());
    return this;
  }

  /**
   * Asserts that this fixture's {@code JOptionPane} is displaying a warning message.
   *
   * @return this fixture.
   */
  public @Nonnull JOptionPaneFixture requireWarningMessage() {
    driver().requireWarningMessage(target());
    return this;
  }

  /**
   * Asserts that this fixture's {@code JOptionPane} is displaying a question.
   *
   * @return this fixture.
   */
  public @Nonnull JOptionPaneFixture requireQuestionMessage() {
    driver().requireQuestionMessage(target());
    return this;
  }

  /**
   * Asserts that this fixture's {@code JOptionPane} is displaying a plain message.
   *
   * @return this fixture.
   */
  public @Nonnull JOptionPaneFixture requirePlainMessage() {
    driver().requirePlainMessage(target());
    return this;
  }

  /**
   * Asserts that the title of this fixture's {@code JOptionPane} matches the given value.
   *
   * @param title the title to match. It can be a regular expression.
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code JOptionPaneFixture} does not have the given title.
   */
  public @Nonnull JOptionPaneFixture requireTitle(@Nullable String title) {
    driver().requireTitle(target(), title);
    return this;
  }

  /**
   * Asserts that the title of this fixture's {@code JOptionPane} matches the given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression is {@code null}.
   * @throws AssertionError if this fixture's {@code JOptionPaneFixture} does not have the given title.
   * @since 1.2
   */
  public @Nonnull JOptionPaneFixture requireTitle(@Nonnull Pattern pattern) {
    driver().requireTitle(target(), pattern);
    return this;
  }

  /**
   * Asserts that the message of this fixture's {@code JOptionPane} matches the given value.
   *
   * @param message the message to verify. If it is a {@code String}, it can be specified as a regular expression.
   * @return this fixture.
   * @throws AssertionError if the message in this fixture's {@code JOptionPaneFixture} is not equal to or does not
   *           match the given message.
   */
  public @Nonnull JOptionPaneFixture requireMessage(@Nullable Object message) {
    driver().requireMessage(target(), message);
    return this;
  }

  /**
   * Asserts that the message of this fixture's {@code JOptionPane} matches the given regular expression pattern. If the
   * message in the {@code JOptionPane} is not a {@code String}, this method will use the {@code toString}
   * representation of such message.
   *
   * @param pattern the regular expression to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the message in this fixture's {@code JOptionPaneFixture} does not match the given regular
   *           expression pattern.
   * @since 1.2
   */
  public @Nonnull JOptionPaneFixture requireMessage(@Nonnull Pattern pattern) {
    driver().requireMessage(target(), pattern);
    return this;
  }

  /**
   * Asserts that this fixture's {@code JOptionPane} has the given options.
   *
   * @param options the options to verify.
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code JOptionPaneFixture} does not have the given options.
   */
  public @Nonnull JOptionPaneFixture requireOptions(@Nonnull Object[] options) {
    driver().requireOptions(target(), options);
    return this;
  }
}
