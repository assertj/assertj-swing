/*
 * Created on Jun 12, 2007
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
 * Supports functional testing of {@code Component}s that contains or display a group of items.
 *
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public interface ItemGroupFixture<S> {
  /**
   * @return the {@code String} representation of the elements in this fixture's {@code Component}.
   */
  @Nonnull String[] contents();

  /**
   * Clears the selection in this fixture's {@code Component}.
   * 
   * @return this fixture.
   * @since 1.2
   */
  @Nonnull S clearSelection();

  /**
   * Simulates a user selecting an item in this fixture's {@code Component}.
   * 
   * @param index the index of the item to select.
   * @return this fixture.
   */
  @Nonnull S selectItem(int index);

  /**
   * Simulates a user selecting an item in this fixture's {@code Component}.
   * 
   * @param value the value of the item to select. It can be a regular expression.
   * @return this fixture.
   */
  @Nonnull S selectItem(@Nullable String value);

  /**
   * Simulates a user selecting an item in this fixture's {@code Component}. The text of the item to select must match
   * the given regular expression pattern.
   * 
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @since 1.2
   */
  @Nonnull S selectItem(@Nonnull Pattern pattern);

  /**
   * Returns the value of an item in the {@code Component} managed by this fixture. If the value is not meaningful, this
   * method will return {@code null}.
   * 
   * @param index the index of the item to return.
   * @return the value of the item under the given index, or {@code null} if nothing meaningful.
   */
  @Nullable Object valueAt(int index);

  /**
   * Verifies that the value of the selected item in this fixture's {@code Component} matches the given value.
   * 
   * @param value the value to match. It can be a regular expression.
   * @return this fixture.
   * @throws AssertionError if the selected item does not match the given value.
   */
  @Nonnull S requireSelection(@Nullable String value);

  /**
   * Verifies that the value of the selected item in this fixture's {@code Component} matches the given regular
   * expression pattern.
   * 
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the selected item does not match the given regular expression pattern.
   * @since 1.2
   */
  @Nonnull S requireSelection(@Nonnull Pattern pattern);

  /**
   * Verifies that the index of the selected item in this fixture's {@code Component} is equal to the given value.
   * 
   * @param index the expected selection index.
   * @return this fixture.
   * @throws AssertionError if the selection index is not equal to the given value.
   * @since 1.2
   */
  @Nonnull S requireSelection(int index);

  /**
   * Verifies that this fixture's {@code Component} does not have a selection.
   * 
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code Component} has a selection.
   */
  @Nonnull S requireNoSelection();

  /**
   * Verifies that this fixture's {@code Component} has the expected number of items
   * 
   * @param expected the expected number of items.
   * @return this fixture.
   * @throws AssertionError if the number of items in this fixture's {@code Component} is not equal to the expected one.
   * @since 1.2
   */
  @Nonnull S requireItemCount(int expected);
}