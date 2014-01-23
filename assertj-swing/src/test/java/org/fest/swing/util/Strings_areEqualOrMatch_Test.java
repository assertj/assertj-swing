/*
 * Created on Jan 14, 2008
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

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link Strings#areEqualOrMatch(String, String)}.
 * 
 * @author Alex Ruiz
 * @author Uli Schrempp
 */
public class Strings_areEqualOrMatch_Test {
  @Test
  public void should_return_true_if_String_and_pattern_are_equal() {
    assertThat(Strings.areEqualOrMatch("hello", "hello")).isTrue();
  }

  @Test
  public void should_return_true_if_String_matches_pattern() {
    assertThat(Strings.areEqualOrMatch("hell.", "hello")).isTrue();
  }

  @Test
  public void should_return_false_if_String_does_not_match_Pattern() {
    assertThat(Strings.areEqualOrMatch("hi", "hello")).isFalse();
  }

  @Test
  public void should_return_false_if_String_is_null() {
    assertThat(Strings.areEqualOrMatch("hell.", null)).isFalse();
  }

  @Test
  public void should_return_false_if_pattern_is_null() {
    assertThat(Strings.areEqualOrMatch(null, "Hello")).isFalse();
  }

  @Test
  public void should_return_true_if_String_and_pattern_are_null() {
    assertThat(Strings.areEqualOrMatch(null, null)).isTrue();
  }

  @Test
  public void should_return_true_if_pattern_is_invalid_regex_but_is_contained_in_string() {
    assertThat(Strings.areEqualOrMatch("\\\\server\\share\\myfolder", "\\\\server\\share\\myfolder\\mysubfolder"))
    .isTrue();
  }

  @Test
  public void should_return_true_if_pattern_is_invalid_regex_and_doesnt_match_string() {
    assertThat(Strings.areEqualOrMatch("\\myfolder", "this does not match")).isFalse();
  }
}
