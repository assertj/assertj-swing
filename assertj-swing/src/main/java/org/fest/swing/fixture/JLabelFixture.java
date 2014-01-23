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

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JLabel;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JLabelDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JLabel}s.
 *
 * @author Alex Ruiz
 */
public class JLabelFixture extends AbstractJPopupMenuInvokerFixture<JLabelFixture, JLabel, JLabelDriver> implements
    TextDisplayFixture<JLabelFixture> {
  /**
   * Creates a new {@link JLabelFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JLabel}.
   * @param target the {@code JLabel} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JLabelFixture(@Nonnull Robot robot, @Nonnull JLabel target) {
    super(JLabelFixture.class, robot, target);
  }

  /**
   * Creates a new {@link JLabelFixture}.
   *
   * @param robot performs simulation of user events on a {@code JLabel}.
   * @param labelName the name of the {@code JLabel} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JLabel} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JLabel} is found.
   */
  public JLabelFixture(@Nonnull Robot robot, @Nullable String labelName) {
    super(JLabelFixture.class, robot, labelName, JLabel.class);
  }

  @Override
  protected @Nonnull JLabelDriver createDriver(@Nonnull Robot robot) {
    return new JLabelDriver(robot);
  }

  /**
   * @return the text of this fixture's {@code JLabel}.
   */
  @Override
  public @Nullable String text() {
    return driver().textOf(target());
  }

  /**
   * Asserts that the text of this fixture's {@code JLabel} is equal to the specified {@code String}.
   *
   * @param expected the text to match.
   * @return this fixture.
   * @throws AssertionError if the text of this fixture's {@code JLabel} is not equal to the given one.
   */
  @Override
  public @Nonnull JLabelFixture requireText(@Nullable String expected) {
    driver().requireText(target(), expected);
    return this;
  }

  /**
   * Asserts that the text of this fixture's {@code JLabel} matches the given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws AssertionError if the text of this fixture's {@code JLabel} does not match the given regular expression
   *           pattern.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @since 1.2
   */
  @Override
  public @Nonnull JLabelFixture requireText(@Nonnull Pattern pattern) {
    driver().requireText(target(), pattern);
    return this;
  }
}
