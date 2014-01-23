/*
 * Created on Aug 5, 2009
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
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link NameMatcher#matches(java.awt.Component)}.
 * 
 * <p>
 * The {@link NameMatcher} is created through {@link NameMatcher#NameMatcher(String)}.
 * </p>
 * 
 * @author Alex Ruiz
 */
public class NameMatcher_matches_byName_Test extends NameMatcher_TestCase {
  @Test
  public void should_return_true_if_name_matches_and_Component_is_showing() {
    window.display();
    NameMatcher matcher = new NameMatcher(LABEL_TEXT);
    assertThat(matcher.matches(window.button)).isTrue();
  }

  @Test
  public void should_return_true_if_name_matches_and_Component_is_not_showing() {
    NameMatcher matcher = new NameMatcher(LABEL_TEXT);
    assertThat(matcher.matches(window.button)).isTrue();
  }

  @Test
  public void should_return_false_if_name_does_not_match() {
    NameMatcher matcher = new NameMatcher("Hello");
    assertThat(matcher.matches(window.button)).isFalse();
  }
}
