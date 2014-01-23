/*
 * Created on Jun 25, 2009
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

import static org.fest.util.Preconditions.checkNotNull;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.fest.util.Arrays;

/**
 * Utility methods for regular expression patterns.
 * 
 * @author Alex Ruiz
 */
public final class Patterns {
  /**
   * <p>
   * Formats the given array of regular expression patterns.
   * </p>
   * 
   * <p>
   * For example, the array
   * 
   * <pre>
   * Pattern[] patterns = { Pattern.compile(&quot;hello&quot;), Pattern.compile(&quot;world&quot;) };
   * </pre>
   * 
   * will be formatted as
   * 
   * <pre>
   * ['hello', 'world']
   * </pre>
   * 
   * </p>
   * 
   * @param patterns the array of patterns to format.
   * @return the {@code String} containing the formatted array.
   * @throws NullPointerException if the given array of patterns is {@code null}.
   * @throws NullPointerException if any of the patterns in the given array is {@code null}.
   */
  public static @Nonnull String format(@Nonnull Pattern[] patterns) {
    checkNotNull(patterns);
    int patternCount = patterns.length;
    String[] patternsAsText = new String[patternCount];
    for (int i = 0; i < patternCount; i++) {
      Pattern p = checkNotNull(patterns[i]);
      patternsAsText[i] = p.pattern();
    }
    return checkNotNull(Arrays.format(patternsAsText));
  }

  private Patterns() {}
}
