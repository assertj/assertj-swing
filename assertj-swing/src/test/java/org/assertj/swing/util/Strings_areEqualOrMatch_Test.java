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
 * Tests for {@link Strings#areEqualOrMatch(String, String)}.
 * 
 * @author Alex Ruiz
 * @author Uli Schrempp
 */
public class Strings_areEqualOrMatch_Test {
  @Test
  public void should_Return_True_If_String_And_Pattern_Are_Equal() {
    assertThat(Strings.areEqualOrMatch("hello", "hello")).isTrue();
  }

  @Test
  public void should_Return_True_If_String_Matches_Pattern() {
    assertThat(Strings.areEqualOrMatch("hell.", "hello")).isTrue();
  }

  @Test
  public void should_Return_False_If_String_Does_Not_Match_Pattern() {
    assertThat(Strings.areEqualOrMatch("hi", "hello")).isFalse();
  }

  @Test
  public void should_Return_False_If_String_Is_Null() {
    assertThat(Strings.areEqualOrMatch("hell.", null)).isFalse();
  }

  @Test
  public void should_Return_False_If_Pattern_Is_Null() {
    assertThat(Strings.areEqualOrMatch(null, "Hello")).isFalse();
  }

  @Test
  public void should_Return_True_If_String_And_Pattern_Are_Null() {
    assertThat(Strings.areEqualOrMatch(null, null)).isTrue();
  }

  @Test
  public void should_Return_True_If_Pattern_Is_Invalid_Regex_But_Is_Contained_In_String() {
    assertThat(Strings.areEqualOrMatch("\\\\server\\share\\myfolder", "\\\\server\\share\\myfolder\\mysubfolder"))
        .isTrue();
  }

  @Test
  public void should_Return_True_If_Pattern_Is_Invalid_Regex_And_Doesnt_Match_String() {
    assertThat(Strings.areEqualOrMatch("\\myfolder", "this does not match")).isFalse();
  }
}
