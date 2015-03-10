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

import java.util.regex.Pattern;

import javax.swing.JLabel;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link NamedComponentMatcherTemplate#quoted(Object)}.
 * 
 * @author Alex Ruiz
 */
public class NamedComponentMatcherTemplate_quoted_Test extends NamedComponentMatcherTemplate_TestCase {
  @Before
  public final void setUp() {
    matcher = new Matcher(JLabel.class);
  }

  @Test
  public void should_Not_Quote_Any() {
    Object anyValue = NamedComponentMatcherTemplate.anyValue();
    assertThat(matcher.quoted(anyValue)).isSameAs(anyValue);
  }

  @Test
  public void should_Quote_Pattern_As_String() {
    assertThat(matcher.quoted(Pattern.compile("hello"))).isEqualTo("'hello'");
  }

  @Test
  public void should_Quote_If_Not_Any() {
    assertThat(matcher.quoted("hello")).isEqualTo("'hello'");
  }
}
