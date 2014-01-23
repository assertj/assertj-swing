/*
 * Created on Dec 16, 2006
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
import javax.swing.AbstractButton;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.AbstractButtonDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code AbstractButton}s.
 *
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 * @param <T> the type of {@code AbstractButton} that this fixture can manage.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class AbstractButtonFixture<S, T extends AbstractButton> extends
    AbstractJPopupMenuInvokerFixture<S, T, AbstractButtonDriver> implements TextDisplayFixture<S> {
  /**
   * Creates a new {@link AbstractButtonFixture}.
   *
   * @param selfType the "self type."
   * @param target the {@code JButton} to be managed by this fixture.
   * @param robot performs simulation of user events on the given {@code AbstractButton}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public AbstractButtonFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nonnull T target) {
    super(selfType, robot, target);
  }

  /**
   * Creates a new {@link AbstractButtonFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on a {@code AbstractButton}.
   * @param buttonName the name of the {@code AbstractButton} to find using the given {@code RobotFixture}.
   * @param type the type of the {@code AbstractButton} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code type} is {@code null}.
   * @throws ComponentLookupException if a matching {@code AbstractButton} could not be found.
   * @throws ComponentLookupException if more than one matching {@code AbstractButton} is found.
   */
  public AbstractButtonFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nullable String buttonName,
      @Nonnull Class<? extends T> type) {
    super(selfType, robot, buttonName, type);
  }

  @Override
  protected @Nonnull AbstractButtonDriver createDriver(@Nonnull Robot robot) {
    return new AbstractButtonDriver(robot);
  }

  /**
   * @return the text of this fixture's {@code AbstractButton}.
   */
  @Override
  public final String text() {
    return driver().textOf(target());
  }

  /**
   * Asserts that the text of this fixture's {@link AbstractButton} matches the specified value.
   *
   * @param expected the text to match. It can be a regular expression.
   * @return this fixture.
   * @throws AssertionError if the text of the target {@code AbstractButton} does not match the given one.
   */
  @Override
  public final @Nonnull S requireText(@Nullable String expected) {
    driver().requireText(target(), expected);
    return myself();
  }

  /**
   * Asserts that the text of this fixture's {@link AbstractButton} matches the given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the text of the target {@code AbstractButton} does not match the given regular expression
   *           pattern.
   */
  @Override
  public final @Nonnull S requireText(@Nonnull Pattern pattern) {
    driver().requireText(target(), pattern);
    return myself();
  }
}
