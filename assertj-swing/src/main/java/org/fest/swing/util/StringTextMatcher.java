/*
 * Created on Jun 26, 2009
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
package org.fest.swing.util;

import static org.fest.swing.util.Strings.areEqualOrMatch;
import static org.fest.util.Arrays.format;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;
import static org.fest.util.Strings.quote;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Matches text to a group of {@code String} values. Matching is perform by equality or by regular expression matching.
 * 
 * @author Alex Ruiz
 */
public class StringTextMatcher implements TextMatcher {
  private final String[] values;

  /**
   * Creates a new {@link StringTextMatcher}.
   * 
   * @param values the {@code String} values to match. Each value can be a regular expression.
   * @throws NullPointerException if the array of values is {@code null}.
   * @throws IllegalArgumentException if the array of values is empty.
   */
  public StringTextMatcher(@Nonnull String... values) {
    this.values = checkNotNullOrEmpty(values);
  }

  /**
   * Indicates whether the given text matches the {@code String} values in this matcher. Each value can be a regular
   * expression.
   * 
   * @param text the text to verify.
   * @return {@code true} if the given text matches the {@code String} values in this matcher, {@code false} otherwise.
   */
  @Override
  public boolean isMatching(@Nullable String text) {
    for (String value : values) {
      if (areEqualOrMatch(value, text)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @return "value" if this matcher contains only one value, or "values" if this matcher contains more than one value.
   */
  @Override
  public @Nonnull String description() {
    return onlyOneValue() ? "value" : "values";
  }

  /**
   * @return the {@code String} values in this matcher, formatted as a single {@code String}.
   */
  @Override
  public @Nonnull String formattedValues() {
    String s = onlyOneValue() ? quote(values[0]) : format(values);
    return checkNotNull(s);
  }

  private boolean onlyOneValue() {
    return values.length == 1;
  }
}
