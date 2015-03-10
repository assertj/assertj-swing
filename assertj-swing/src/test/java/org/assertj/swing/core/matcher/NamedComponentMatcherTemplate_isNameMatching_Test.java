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
package org.assertj.swing.core.matcher;

import static org.assertj.core.api.Assertions.assertThat;

import javax.swing.JLabel;

import org.junit.Test;

/**
 * Tests for {@link NamedComponentMatcherTemplate#isNameMatching(String)}.
 * 
 * @author Alex Ruiz
 */
public class NamedComponentMatcherTemplate_isNameMatching_Test extends NamedComponentMatcherTemplate_TestCase {
  @Test
  public void should_Always_Match_If_Name_Is_Any() {
    matcher = new Matcher(JLabel.class, NamedComponentMatcherTemplate.anyValue());
    assertThat(matcher.isNameMatching("hello")).isTrue();
  }

  @Test
  public void should_Match_If_Name_Is_Equal_To_Expected() {
    matcher = new Matcher(JLabel.class, "hello");
    assertThat(matcher.isNameMatching("hello")).isTrue();
  }
}
