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
 * Tests for {@link StringTextMatcher#description()}.
 * 
 * @author Alex Ruiz
 */
public class StringTextMatcher_description_Test {
  @Test
  public void should_return_value_word_as_description_if_matcher_has_only_one_value() {
    StringTextMatcher matcher = new StringTextMatcher("one");
    assertThat(matcher.description()).isEqualTo("value");
  }

  @Test
  public void should_return_values_word_as_description_if_matcher_has_more_than_one_value() {
    StringTextMatcher matcher = new StringTextMatcher("one", "two");
    assertThat(matcher.description()).isEqualTo("values");
  }
}
