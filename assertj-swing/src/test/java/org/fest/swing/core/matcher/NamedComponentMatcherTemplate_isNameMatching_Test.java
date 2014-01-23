/*
 * Created on May 1, 2009
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
package org.fest.swing.core.matcher;

import static org.fest.assertions.Assertions.assertThat;

import javax.swing.JLabel;

import org.junit.Test;

/**
 * Tests for {@link NamedComponentMatcherTemplate#isNameMatching(String)}.
 *
 * @author Alex Ruiz
 */
public class NamedComponentMatcherTemplate_isNameMatching_Test extends NamedComponentMatcherTemplate_TestCase {
  @Test
  public void should_always_match_if_name_is_any() {
    matcher = new Matcher(JLabel.class, NamedComponentMatcherTemplate.anyValue());
    assertThat(matcher.isNameMatching("hello")).isTrue();
  }

  @Test
  public void should_match_if_name_is_equal_to_expected() {
    matcher = new Matcher(JLabel.class, "hello");
    assertThat(matcher.isNameMatching("hello")).isTrue();
  }
}
