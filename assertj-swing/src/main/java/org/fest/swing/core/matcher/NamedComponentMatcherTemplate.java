/*
 * Created on Jan 12, 2009
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
package org.fest.swing.core.matcher;

import static org.fest.swing.util.Strings.areEqualOrMatch;
import static org.fest.swing.util.Strings.match;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.quote;

import java.awt.Component;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.core.GenericTypeMatcher;

/**
 * Template for matching AWT or Swing {@code Component} by name. Subclasses are free to add other properties to use as
 * search criteria.
 *
 * @param <T> the type of {@code Component} supported by this matcher.
 *
 * @author Alex Ruiz
 */
public abstract class NamedComponentMatcherTemplate<T extends Component> extends GenericTypeMatcher<T> {
  /**
   * Indicates that a property value to use as search criteria has not been set.
   */
  private static final Object ANY = new Object() {
    @Override
    public String toString() {
      return "<Any>";
    }
  };

  /** The name to match. **/
  private final Object name;

  /**
   * Creates a new {@link NamedComponentMatcherTemplate}.
   *
   * @param supportedType the type supported by this matcher.
   * @throws NullPointerException if the given type is {@code null}.
   */
  protected NamedComponentMatcherTemplate(@Nonnull Class<T> supportedType) {
    super(supportedType);
    this.name = ANY;
  }

  /**
   * Creates a new {@link NamedComponentMatcherTemplate}.
   *
   * @param supportedType the type supported by this matcher.
   * @param name the name to match.
   * @throws NullPointerException if the given type is {@code null}.
   */
  protected NamedComponentMatcherTemplate(@Nonnull Class<T> supportedType, @Nullable Object name) {
    super(supportedType);
    this.name = name;
  }

  /**
   * Returns the name to match surrounded by double quotes. If the name to match has not been set, it will return
   * {@link #anyValue()}. This method is commonly used in implementations of {@code toString}.
   *
   * @return the component name to match surrounded by double quotes, or {@link #anyValue()} if the name to match has
   *         not been set.
   */
  protected final @Nullable Object quotedName() {
    return quoted(name);
  }

  /**
   * Returns the given property value to match surrounded by double quotes. If the property has not been set, it will
   * return {@link #anyValue()}. This method is commonly used in implementations of {@code toString}.
   *
   * @param propertyValue the given property value.
   * @return the given property value to match surrounded by double quotes, or {@link #anyValue()} if the property value
   *         has not been set.
   */
  protected final @Nullable Object quoted(@Nullable Object propertyValue) {
    if (ANY.equals(propertyValue)) {
      return ANY;
    }
    if (propertyValue instanceof Pattern) {
      String pattern = ((Pattern) propertyValue).pattern();
      return quote(pattern);
    }
    return quote(propertyValue);
  }

  /**
   * Indicates whether the given value matches the name in this matcher. It always returns {@code true} if this
   * matcher's name is {@link #anyValue()}.
   *
   * @param actual the actual name of an AWT or Swing {@code Component}.
   * @return {@code true} if this matcher's name is {@code ANY} or if both the actual name is equal to the one in this
   *         matcher. Otherwise {@code false}.
   */
  protected final boolean isNameMatching(@Nullable String actual) {
    if (ANY.equals(name)) {
      return true;
    }
    return areEqual(name, actual);
  }

  /**
   * Indicates whether the given value matches the expected value in this matcher. Matching is performed as follows:
   * <ol>
   * <li>it always returns {@code true} if the expected value is {@link #anyValue()}</li>
   * <li>if both the expected and actual values are {@code String}s, it checks for equality first. If this fails, it
   * tries to match the values assuming the expected value can be a regular expression</li>
   * <li>if the expected value is a {@code Pattern} and the actual value is a {@code CharSequence}, regular expression
   * matching is performed</li>
   * <li>otherwise, it checks that both the expected and actual values are equal</li>
   * </ol>
   *
   * @param expected the expected value in this matcher.
   * @param actual the actual property value.
   * @return {@code true} if the values match, otherwise {@code false}.
   */
  protected final boolean arePropertyValuesMatching(@Nullable Object expected, @Nullable Object actual) {
    if (ANY.equals(expected)) {
      return true;
    }
    if (expected instanceof String && actual instanceof String) {
      return areEqualOrMatch((String) expected, (String) actual);
    }
    if (expected instanceof Pattern && actual instanceof CharSequence) {
      return match((Pattern) expected, (CharSequence) actual);
    }
    return areEqual(expected, actual);
  }

  protected final @Nullable Object name() {
    return name;
  }

  protected static final @Nonnull Object anyValue() {
    return ANY;
  }
}
