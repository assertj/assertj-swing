/*
 * Created on Jul 31, 2009
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

import org.junit.Test;

/**
 * Tests for {@link Strings#isDefaultToString(String)}.
 * 
 * @author Alex Ruiz
 */
public class Strings_isDefaultToString_Test {
  @Test
  public void should_return_false_if_String_is_null() {
    assertThat(Strings.isDefaultToString(null)).isFalse();
  }

  @Test
  public void should_return_false_if_String_is_empty() {
    assertThat(Strings.isDefaultToString("")).isFalse();
  }

  @Test
  public void should_return_false_if_at_symbol_is_not_followed_by_hash() {
    assertThat(Strings.isDefaultToString("abc@xyz"));
  }

  @Test
  public void should_return_false_if_there_is_no_at_symbol() {
    assertThat(Strings.isDefaultToString("abc"));
  }

  @Test
  public void should_return_true_if_default_toString() {
    class Person {
    }
    assertThat(Strings.isDefaultToString(new Person().toString())).isTrue();
  }
}
