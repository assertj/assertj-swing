/*
 * Created on Feb 28, 2008
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

import static org.fest.swing.driver.TextAssert.verifyThat;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JLabel;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.util.InternalApi;

/**
 * <p>
 * Supports functional testing of {@code JLabel}s.
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
public class JLabelDriver extends JComponentDriver implements TextDisplayDriver<JLabel> {
  private static final String TEXT_PROPERTY = "text";

  /**
   * Creates a new {@link JLabelDriver}.
   * 
   * @param robot the robot to use to simulate user input.
   */
  public JLabelDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Asserts that the text of the {@code JLabel} is equal to the specified {@code String}.
   * 
   * @param label the target {@code JLabel}.
   * @param expected the text to match.
   * @throws AssertionError if the text of the {@code JLabel} is not equal to the given one.
   */
  @RunsInEDT
  @Override
  public void requireText(@Nonnull JLabel label, @Nullable String expected) {
    verifyThat(textOf(label)).as(propertyName(label, TEXT_PROPERTY)).isEqualOrMatches(expected);
  }

  /**
   * Asserts that the text of the {@code JLabel} matches the given regular expression pattern.
   * 
   * @param label the target {@code JLabel}.
   * @param pattern the regular expression pattern to match.
   * @throws AssertionError if the text of the {@code JLabel} does not match the given regular expression pattern.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @since 1.2
   */
  @RunsInEDT
  @Override
  public void requireText(@Nonnull JLabel label, @Nonnull Pattern pattern) {
    verifyThat(textOf(label)).as(propertyName(label, TEXT_PROPERTY)).matches(pattern);
  }

  /**
   * Returns the text of the given {@code JLabel}.
   * 
   * @param label the given {@code JLabel}.
   * @return the text of the given {@code JLabel}.
   */
  @RunsInEDT
  @Override
  public @Nullable String textOf(@Nonnull JLabel label) {
    return JLabelTextQuery.textOf(label);
  }
}
