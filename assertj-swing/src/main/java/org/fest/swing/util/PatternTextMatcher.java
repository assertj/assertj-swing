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

import static org.fest.swing.util.Patterns.format;
import static org.fest.swing.util.Strings.match;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;
import static org.fest.util.Strings.quote;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Matches text to a group of {@code String} values. Matching is perform by equality or by regular expression matching.
 * 
 * @author Alex Ruiz
 */
public class PatternTextMatcher implements TextMatcher {
  private final Pattern[] patterns;

  /**
   * Creates a new {@link PatternTextMatcher}.
   * 
   * @param patterns the regular expression patterns to match.
   * @throws NullPointerException if the array of patterns is {@code null}.
   * @throws IllegalArgumentException if the array of patterns is empty.
   */
  public PatternTextMatcher(@Nonnull Pattern... patterns) {
    this.patterns = checkNotNullOrEmpty(patterns);
  }

  /**
   * Indicates whether the given text matches the regular expression patterns in this matcher.
   * 
   * @param text the text to verify.
   * @return {@code true} if the given text matches the {@code Pattern} values in this matcher, {@code false} otherwise.
   * @throws NullPointerException if any of the regular expressions is {@code null}.
   */
  @Override
  public boolean isMatching(@Nullable String text) {
    for (Pattern pattern : patterns) {
      if (match(pattern, text)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @return "pattern" if this matcher contains only one pattern, or "patterns" if this matcher contains more than one
   *         pattern.
   */
  @Override
  public @Nonnull String description() {
    return patterns.length == 1 ? "pattern" : "patterns";
  }

  /**
   * @return the regular expression patterns in this matcher, formatted as a single {@code String}.
   */
  @Override
  public @Nonnull String formattedValues() {
    if (patterns.length == 1) {
      return checkNotNull(quote(patterns[0].pattern()));
    }
    return format(patterns);
  }
}
