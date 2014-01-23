/*
 * Created on Jul 21, 2009
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
import javax.swing.JComponent;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JComponentDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JComponent}s.
 *
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 * @param <T> the type of {@code JComponent} that this fixture can manage.
 * @param <D> the type of {@link JComponentDriver} that this fixture uses internally.
 *
 * @author Alex Ruiz
 */
public abstract class AbstractJComponentFixture<S, T extends JComponent, D extends JComponentDriver> extends
    AbstractComponentFixture<S, T, D> implements JComponentFixture<S> {
  /**
   * Creates a new {@link AbstractJComponentFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on a {@code JComponent}.
   * @param type the type of the {@code JComponent} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code type} is {@code null}.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public AbstractJComponentFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nonnull Class<? extends T> type) {
    super(selfType, robot, type);
  }

  /**
   * Creates a new {@link AbstractJComponentFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on a {@code JComponent}.
   * @param name the name of the {@code JComponent} to find using the given {@code Robot}.
   * @param type the type of the {@code JComponent} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code type} is {@code null}.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public AbstractJComponentFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nullable String name,
      @Nonnull Class<? extends T> type) {
    super(selfType, robot, name, type);
  }

  /**
   * Creates a new {@link AbstractJComponentFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on the given {@code JComponent}.
   * @param target the {@code JComponent} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public AbstractJComponentFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nonnull T target) {
    super(selfType, robot, target);
  }

  /**
   * Returns the client property stored in this fixture's {@code JComponent}, under the given key.
   *
   * @param key the key to use to retrieve the client property.
   * @return the value of the client property stored under the given key, or {@code null} if the property was not found.
   * @throws NullPointerException if the given key is {@code null}.
   * @since 1.2
   */
  @Override
  public final @Nullable Object clientProperty(@Nonnull Object key) {
    return driver().clientProperty(target(), key);
  }

  /**
   * Asserts that the toolTip in this fixture's {@code JComponent} matches the given value.
   *
   * @param expected the given value. It can be a regular expression.
   * @return this fixture.
   * @throws AssertionError if the toolTip in this fixture's {@code JComponent} does not match the given value.
   */
  @Override
  public final @Nonnull S requireToolTip(@Nullable String expected) {
    driver().requireToolTip(target(), expected);
    return myself();
  }

  /**
   * Asserts that the toolTip in this fixture's {@code JComponent} matches the given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the toolTip in this fixture's {@code JComponent} does not match the given value.
   */
  @Override
  public final @Nonnull S requireToolTip(@Nonnull Pattern pattern) {
    driver().requireToolTip(target(), pattern);
    return myself();
  }
}
