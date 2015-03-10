/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link StringTextMatcher#isMatching(String)}.
 * 
 * @author Alex Ruiz
 */
public class StringTextMatcher_isMatching_Test {
  @Test
  public void should_Return_True_If_Text_Is_Equal_To_Any_Value() {
    StringTextMatcher matcher = new StringTextMatcher("hello", "world");
    assertThat(matcher.isMatching("world")).isTrue();
  }

  @Test
  public void should_Return_True_If_Text_Matches_Any_Value_As_Pattern() {
    StringTextMatcher matcher = new StringTextMatcher("hell.*", "world");
    assertThat(matcher.isMatching("hello")).isTrue();
  }

  @Test
  public void should_Return_False_If_Text_Does_Not_Match_Any_Value() {
    StringTextMatcher matcher = new StringTextMatcher("hell.*", "world");
    assertThat(matcher.isMatching("bye")).isFalse();
  }
}
