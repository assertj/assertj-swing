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
 * Tests for {@link Strings#isDefaultToString(String)}.
 * 
 * @author Alex Ruiz
 */
public class Strings_isDefaultToString_Test {
  @Test
  public void should_Return_False_If_String_Is_Null() {
    assertThat(Strings.isDefaultToString(null)).isFalse();
  }

  @Test
  public void should_Return_False_If_String_Is_Empty() {
    assertThat(Strings.isDefaultToString("")).isFalse();
  }

  @Test
  public void should_Return_False_If_At_Symbol_Is_Not_Followed_By_Hash() {
    assertThat(Strings.isDefaultToString("abc@xyz"));
  }

  @Test
  public void should_Return_False_If_There_Is_No_At_Symbol() {
    assertThat(Strings.isDefaultToString("abc"));
  }

  @Test
  public void should_Return_True_If_Default_ToString() {
    class Person {
    }
    assertThat(Strings.isDefaultToString(new Person().toString())).isTrue();
  }
}
