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

import static org.fest.assertions.Assertions.assertThat;

import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Tests for {@link Patterns#format(Pattern[])}.
 * 
 * @author Alex Ruiz
 */
public class Patterns_format_Test {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_pattern_array_is_null() {
    Pattern[] patterns = null;
    Patterns.format(patterns);
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_pattern_in_array_is_null() {
    Pattern[] patterns = { Pattern.compile("hello"), null };
    Patterns.format(patterns);
  }

  @Test
  public void should_format_pattern_array() {
    Pattern[] patterns = { Pattern.compile("hello"), Pattern.compile("world") };
    String formatted = Patterns.format(patterns);
    assertThat(formatted).isEqualTo("['hello', 'world']");
  }
}
