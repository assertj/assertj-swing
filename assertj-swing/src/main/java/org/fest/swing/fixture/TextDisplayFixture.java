/*
 * Created on Apr 10, 2007
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

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Supports functional testing of {@code Component}s that display text.
 * 
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 * 
 * @author Alex Ruiz
 */
public interface TextDisplayFixture<S> {
  /**
   * @return the text of this fixture's {@code Component}.
   */
  @Nullable String text();

  /**
   * Asserts that the text of this fixture's {@code Component} is equal to or matches the specified {@code String}.
   * 
   * @param expected the text to match. It can be a regular expression.
   * @return this fixture.
   * @throws AssertionError if the text of the target component is not equal to or does not match the given one.
   */
  @Nonnull S requireText(@Nullable String expected);

  /**
   * Asserts that the text of this fixture's {@code Component} matches the given regular expression pattern.
   * 
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the text of the target component does not match the given regular expression pattern.
   * @since 1.2
   */
  @Nonnull S requireText(@Nonnull Pattern pattern);
}
