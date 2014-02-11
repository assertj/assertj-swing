/*
 * Created on Jun 21, 2009
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
package org.assertj.swing.driver;

import static org.assertj.swing.test.ExpectedException.none;

import java.util.regex.Pattern;

import org.assertj.swing.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link TextAssert#matches(java.util.regex.Pattern)}.
 * 
 * @author Alex Ruiz
 */
public class TextAssert_matches_Test {
  @Rule
  public ExpectedException thrown = none();

  @Test
  public void should_fail_if_actual_does_not_match_regex_pattern() {
    thrown.expect(AssertionError.class);
    thrown.expectMessage("Expecting:\n \"hello\"\nto match pattern:\n \"bye\"");
    new TextAssert("hello").matches(Pattern.compile("bye"));
  }

  @Test
  public void should_fail_showing_description_if_actual_does_not_match_regex_pattern() {
    thrown.expect(AssertionError.class);
    thrown.expectMessage("[A Test] \nExpecting:\n \"hello\"\nto match pattern:\n \"bye\"");
    new TextAssert("hello").as("A Test").matches(Pattern.compile("bye"));
  }

  @Test
  public void should_pass_if_actual_matches_regex_pattern() {
    new TextAssert("Hello").matches(Pattern.compile("Hel.*"));
  }
}
