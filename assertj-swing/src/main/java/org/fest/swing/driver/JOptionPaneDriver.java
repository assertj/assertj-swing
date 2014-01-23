/*
 * Created on Feb 27, 2008
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
package org.fest.swing.driver;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JOptionPaneMessageQuery.messageOf;
import static org.fest.swing.driver.JOptionPaneMessageTypeQuery.messageTypeOf;
import static org.fest.swing.driver.JOptionPaneMessageTypes.messageTypeAsText;
import static org.fest.swing.driver.JOptionPaneOptionsQuery.optionsOf;
import static org.fest.swing.driver.JOptionPaneTitleQuery.titleOf;
import static org.fest.swing.driver.TextAssert.verifyThat;
import static org.fest.util.Preconditions.checkNotNull;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.core.matcher.JButtonMatcher;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.util.InternalApi;

/**
 * <p>
 * Supports functional testing of {@code JOptionPane}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.fest.swing.fixture} in your tests.
 * </p>
 *
 * @author Alex Ruiz
 */
@InternalApi
public class JOptionPaneDriver extends JComponentDriver {
  private static final String MESSAGE_PROPERTY = "message";
  private static final String MESSAGE_TYPE_PROPERTY = "messageType";
  private static final String OPTIONS_PROPERTY = "options";
  private static final String TITLE_PROPERTY = "title";

  /**
   * Creates a new {@link JOptionPaneDriver}.
   *
   * @param robot the robot to use to simulate user input.
   */
  public JOptionPaneDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Asserts that the title in the given {@code JOptionPane} matches the given value.
   *
   * @param optionPane the target {@code JOptionPane}.
   * @param title the title to match. It can be a regular expression.
   * @throws AssertionError if the {@code JOptionPane} does not have the given title.
   */
  @RunsInEDT
  public void requireTitle(@Nonnull JOptionPane optionPane, @Nullable String title) {
    verifyThat(title(optionPane)).as(propertyName(optionPane, TITLE_PROPERTY)).isEqualOrMatches(title);
  }

  /**
   * Asserts that the title in the given {@code JOptionPane} matches the given regular expression pattern.
   *
   * @param optionPane the target {@code JOptionPane}.
   * @param pattern the regular expression pattern to match.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the {@code JOptionPane} does not have the given title.
   * @since 1.2
   */
  @RunsInEDT
  public void requireTitle(@Nonnull JOptionPane optionPane, @Nonnull Pattern pattern) {
    verifyThat(title(optionPane)).as(propertyName(optionPane, TITLE_PROPERTY)).matches(pattern);
  }

  /**
   * Returns the title of the given {@code JOptionPane}.
   *
   * @param optionPane the target {@code JOptionPane}.
   * @return the title of the given {@code JOptionPane}.
   * @since 1.2
   */
  @RunsInEDT
  public @Nullable String title(@Nonnull JOptionPane optionPane) {
    return titleOf(optionPane);
  }

  /**
   * Asserts that the title of the {@code JOptionPane} matches the given value. If the given value is a regular
   * expression and the message in the {@code JOptionPane} is not a {@code String}, this method will use the
   * {@code toString} representation of such message. message in the {@code JOptionPane} is not a {@code String}, this
   * method will use the {@code toString} representation of such message.
   *
   * @param optionPane the target {@code JOptionPane}.
   * @param message the message to verify. If it is a {@code String}, it can be specified as a regular expression.
   * @throws AssertionError if the message in the {@code JOptionPane} is not equal to or does not match the given
   *           message.
   */
  @RunsInEDT
  public void requireMessage(@Nonnull JOptionPane optionPane, @Nullable Object message) {
    Object actual = messageOf(optionPane);
    if (message instanceof String && actual != null) {
      requireMessage(optionPane, (String) message, actual.toString());
      return;
    }
    assertThat(actual).as(messageProperty(optionPane)).isEqualTo(message);
  }

  @RunsInEDT
  private void requireMessage(@Nonnull JOptionPane optionPane, @Nullable String expected, @Nullable String actual) {
    verifyThat(actual).as(messageProperty(optionPane)).isEqualOrMatches(expected);
  }

  /**
   * Asserts that the title of the {@code JOptionPane} matches the given regular expression pattern. If the message in
   * the {@code JOptionPane} is not a {@code String}, this method will use the {@code toString} representation of such
   * message.
   *
   * @param optionPane the target {@code JOptionPane}.
   * @param pattern the regular expression to match.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the message in the {@code JOptionPaneFixture} does not match the given regular expression
   *           pattern.
   * @since 1.2
   */
  @RunsInEDT
  public void requireMessage(@Nonnull JOptionPane optionPane, @Nonnull Pattern pattern) {
    Object actual = messageOf(optionPane);
    String s = actual == null ? null : actual.toString();
    verifyThat(s).as(messageProperty(optionPane)).matches(pattern);
  }

  private Description messageProperty(@Nonnull JOptionPane optionPane) {
    return propertyName(optionPane, MESSAGE_PROPERTY);
  }

  /**
   * Asserts that the {@code JOptionPane} has the given options.
   *
   * @param optionPane the target {@code JOptionPane}.
   * @param options the options to verify.
   * @throws AssertionError if the {@code JOptionPane} does not have the given options.
   */
  @RunsInEDT
  public void requireOptions(@Nonnull JOptionPane optionPane, @Nonnull Object[] options) {
    assertThat(optionsOf(optionPane)).as(propertyName(optionPane, OPTIONS_PROPERTY)).isEqualTo(options);
  }

  /**
   * Finds the "OK" button in the {@code JOptionPane}. This method is independent of locale and platform.
   *
   * @param optionPane the target {@code JOptionPane}.
   * @return the "OK" button.
   * @throws ComponentLookupException if the a "OK" button cannot be found.
   */
  @RunsInEDT
  public @Nonnull JButton okButton(@Nonnull JOptionPane optionPane) {
    return buttonWithTextFromUIManager(optionPane, "OptionPane.okButtonText");
  }

  /**
   * Finds the "Cancel" button in the {@code JOptionPane}. This method is independent of locale and platform.
   *
   * @param optionPane the target {@code JOptionPane}.
   * @return the "Cancel" button.
   * @throws ComponentLookupException if the a "Cancel" button cannot be found.
   */
  @RunsInEDT
  public @Nonnull JButton cancelButton(@Nonnull JOptionPane optionPane) {
    return buttonWithTextFromUIManager(optionPane, "OptionPane.cancelButtonText");
  }

  /**
   * Finds the "Yes" button in the {@code JOptionPane}. This method is independent of locale and platform.
   *
   * @param optionPane the target {@code JOptionPane}.
   * @return the "Yes" button.
   * @throws ComponentLookupException if the a "Yes" button cannot be found.
   */
  @RunsInEDT
  public @Nonnull JButton yesButton(@Nonnull JOptionPane optionPane) {
    return buttonWithTextFromUIManager(optionPane, "OptionPane.yesButtonText");
  }

  /**
   * Finds the "No" button in the {@code JOptionPane}. This method is independent of locale and platform.
   *
   * @param optionPane the target {@code JOptionPane}.
   * @return the "No" button.
   * @throws ComponentLookupException if the a "No" button cannot be found.
   */
  @RunsInEDT
  public @Nonnull JButton noButton(@Nonnull JOptionPane optionPane) {
    return buttonWithTextFromUIManager(optionPane, "OptionPane.noButtonText");
  }

  @RunsInEDT
  private @Nonnull JButton buttonWithTextFromUIManager(@Nonnull JOptionPane optionPane, @Nonnull String key) {
    return buttonWithText(optionPane, checkNotNull(UIManager.getString(key)));
  }

  /**
   * Finds a button in the {@code JOptionPane} containing the given text.
   *
   * @param optionPane the target {@code JOptionPane}.
   * @param text the text of the button to find and return. It can be a regular expression.
   * @return a button containing the given text.
   * @throws ComponentLookupException if the a button with the given text cannot be found.
   */
  @RunsInEDT
  public @Nonnull JButton buttonWithText(@Nonnull JOptionPane optionPane, @Nullable String text) {
    return robot.finder().find(optionPane, JButtonMatcher.withText(text).andShowing());
  }

  /**
   * Finds a button in the {@code JOptionPane} whose text matches the given regular expression pattern.
   *
   * @param optionPane the target {@code JOptionPane}.
   * @param pattern the regular expression pattern to match.
   * @return a button containing the given text.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws ComponentLookupException if the a button with the given text cannot be found.
   * @since 1.2
   */
  @RunsInEDT
  public @Nonnull JButton buttonWithText(@Nonnull JOptionPane optionPane, @Nonnull Pattern pattern) {
    return robot.finder().find(optionPane, JButtonMatcher.withText(pattern).andShowing());
  }

  /**
   * Asserts that the {@code JOptionPane} is displaying an error message.
   *
   * @param optionPane the target {@code JOptionPane}.
   */
  @RunsInEDT
  public void requireErrorMessage(@Nonnull JOptionPane optionPane) {
    assertEqualMessageType(optionPane, ERROR_MESSAGE);
  }

  /**
   * Asserts that the {@code JOptionPane} is displaying an information message.
   *
   * @param optionPane the target {@code JOptionPane}.
   */
  @RunsInEDT
  public void requireInformationMessage(@Nonnull JOptionPane optionPane) {
    assertEqualMessageType(optionPane, INFORMATION_MESSAGE);
  }

  /**
   * Asserts that the {@code JOptionPane} is displaying a warning message.
   *
   * @param optionPane the target {@code JOptionPane}.
   */
  @RunsInEDT
  public void requireWarningMessage(@Nonnull JOptionPane optionPane) {
    assertEqualMessageType(optionPane, WARNING_MESSAGE);
  }

  /**
   * Asserts that the {@code JOptionPane} is displaying a question.
   *
   * @param optionPane the target {@code JOptionPane}.
   */
  @RunsInEDT
  public void requireQuestionMessage(@Nonnull JOptionPane optionPane) {
    assertEqualMessageType(optionPane, QUESTION_MESSAGE);
  }

  /**
   * Asserts that the {@code JOptionPane} is displaying a plain message.
   *
   * @param optionPane the target {@code JOptionPane}.
   */
  @RunsInEDT
  public void requirePlainMessage(@Nonnull JOptionPane optionPane) {
    assertEqualMessageType(optionPane, PLAIN_MESSAGE);
  }

  @RunsInEDT
  private void assertEqualMessageType(@Nonnull JOptionPane optionPane, int expected) {
    String actualType = actualMessageTypeAsText(optionPane);
    assertThat(actualType).as(propertyName(optionPane, MESSAGE_TYPE_PROPERTY)).isEqualTo(messageTypeAsText(expected));
  }

  @RunsInEDT
  private String actualMessageTypeAsText(final @Nonnull JOptionPane optionPane) {
    return messageTypeAsText(messageTypeOf(optionPane));
  }
}
