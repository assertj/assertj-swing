/*
 * Created on Jul 30, 2009
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

import static org.fest.assertions.Assertions.assertThat;

import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Tests for {@link PatternTextMatcher#isMatching(String)}.
 * 
 * @author Alex Ruiz
 */
public class PatternTextMatcher_isMatching_Test {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_any_pattern_in_array_is_null() {
    Pattern[] patterns = { null, Pattern.compile("hello"), null };
    PatternTextMatcher matcher = new PatternTextMatcher(patterns);
    matcher.isMatching("hello");
  }

  @Test
  public void should_return_true_if_text_matches_any_pattern() {
    PatternTextMatcher matcher = new PatternTextMatcher(Pattern.compile("hello"));
    assertThat(matcher.isMatching("hello")).isTrue();
  }

  @Test
  public void should_return_false_if_text_does_not_match_any_pattern() {
    PatternTextMatcher matcher = new PatternTextMatcher(Pattern.compile("bye"), Pattern.compile("hello"));
    assertThat(matcher.isMatching("world")).isFalse();
  }
}
