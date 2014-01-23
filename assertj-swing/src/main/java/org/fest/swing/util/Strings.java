/*
 * Created on Jan 13, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.util;

import static org.fest.util.Objects.areEqual;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.util.Preconditions;

/**
 * Utility methods related to {@code String}s.
 * 
 * @author Alex Ruiz
 * @author Uli Schrempp
 */
public final class Strings {
  /**
   * Indicates whether the given {@code String} is the default {@code toString()} implementation of an {@code Object}.
   * 
   * @param s the given {@code String}.
   * @return {@code true} if the given {@code String} is the default {@code toString()} implementation, {@code false}
   *         otherwise.
   */
  public static boolean isDefaultToString(@Nullable String s) {
    if (s == null || s.isEmpty()) {
      return false;
    }
    int at = s.indexOf("@");
    if (at == -1) {
      return false;
    }
    String hash = s.substring(at + 1, s.length());
    try {
      Integer.parseInt(hash, 16);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Indicates if the given {@code String}s match. To match, one of the following conditions needs to be true:
   * <ul>
   * <li>both {@code String}s have to be equal</li>
   * <li>{@code s} matches the regular expression in {@code pattern}</li>
   * </ul>
   * 
   * @param pattern a {@code String} to match (it can be a regular expression.)
   * @param s the {@code String} to verify.
   * @return {@code true} if the given {@code String}s match, {@code false} otherwise.
   */
  public static boolean areEqualOrMatch(@Nullable String pattern, @Nullable String s) {
    if (areEqual(pattern, s)) {
      return true;
    }
    if (pattern != null && s != null) {
      try {
        return s.matches(pattern);
      } catch (PatternSyntaxException invalidRegex) {
        return s.contains(pattern);
      }
    }
    return false;
  }

  /**
   * Indicates if the given {@code String} matches the given regular expression pattern.
   * 
   * @param p the given regular expression pattern.
   * @param s the {@code String} to evaluate.
   * @return {@code true} if the given {@code String} matches the given regular expression pattern, {@code false}
   *         otherwise. It also returns {@code false} if the given {@code String} is {@code null}.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   */
  public static boolean match(@Nonnull Pattern p, @Nullable String s) {
    return match(p, (CharSequence) s);
  }

  /**
   * Indicates if the given {@code CharSequence} matches the given regular expression pattern.
   * 
   * @param p the given regular expression pattern.
   * @param s the {@code CharSequence} to evaluate.
   * @return {@code true} if the given {@code CharSequence} matches the given regular expression pattern, {@code false}
   *         otherwise. It also returns {@code false} if the given {@code CharSequence} is {@code null}.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   */
  public static boolean match(@Nonnull Pattern p, @Nullable CharSequence s) {
    Preconditions.checkNotNull(p);
    if (s == null) {
      return false;
    }
    return p.matcher(s).matches();
  }

  private Strings() {}
}
