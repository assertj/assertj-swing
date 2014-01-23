/*
 * Created on Dec 20, 2009
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

import java.awt.Component;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Supports functional testing of an AWT or Swing {@code Component}s that displays text.
 *
 * @param <T> the type of {@code Component} this driver supports.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 *
 * @since 1.2
 */
public interface TextDisplayDriver<T extends Component> {
  /**
   * Asserts that the text in the given component is equal to or matches the specified {@code String}.
   *
   * @param component the given component.
   * @param expected the text to match. It can be a regular expression.
   * @throws AssertionError if the text of the component is not equal to or does not match the given one.
   */
  void requireText(@Nonnull T component, @Nullable String expected);

  /**
   * Asserts that the text in the given component matches the given regular expression pattern.
   *
   * @param component the given component.
   * @param pattern the regular expression pattern to match.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the text of the component does not match the given regular expression pattern.
   * @since 1.2
   */
  void requireText(@Nonnull T component, @Nonnull Pattern pattern);

  /**
   * Returns the text of the given component.
   *
   * @param component the given component.
   * @return the text of the given component.
   */
  @Nullable String textOf(@Nonnull T component);
}