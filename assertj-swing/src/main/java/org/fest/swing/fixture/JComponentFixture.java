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

/**
 * Supports functional testing of {@code JComponent}s.
 *
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 *
 * @author Alex Ruiz
 */
public interface JComponentFixture<S> {
  /**
   * Returns the client property stored in this fixture's {@code JComponent}, under the given key.
   *
   * @param key the key to use to retrieve the client property.
   * @return the value of the client property stored under the given key, or {@code null} if the property was not found.
   * @throws NullPointerException if the given key is {@code null}.
   * @since 1.2
   */
  @Nullable Object clientProperty(@Nonnull Object key);

  /**
   * Asserts that the toolTip in this fixture's {@code JComponent} matches the given value.
   *
   * @param expected the given value. It can be a regular expression.
   * @return this fixture.
   * @throws AssertionError if the toolTip in this fixture's {@code JComponent} does not match the given value.
   */
  @Nonnull S requireToolTip(@Nullable String expected);

  /**
   * Asserts that the toolTip in this fixture's {@code JComponent} matches the given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the toolTip in this fixture's {@code JComponent} does not match the given value.
   */
  @Nonnull S requireToolTip(@Nonnull Pattern pattern);
}