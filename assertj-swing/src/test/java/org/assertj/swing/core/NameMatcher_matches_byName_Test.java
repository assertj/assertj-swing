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
package org.assertj.swing.core;

import static org.assertj.core.api.Assertions.assertThat;

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
  public void should_Return_True_If_Name_Matches_And_Component_Is_Showing() {
    window.display();
    NameMatcher matcher = new NameMatcher(LABEL_TEXT);
    assertThat(matcher.matches(window.button)).isTrue();
  }

  @Test
  public void should_Return_True_If_Name_Matches_And_Component_Is_Not_Showing() {
    NameMatcher matcher = new NameMatcher(LABEL_TEXT);
    assertThat(matcher.matches(window.button)).isTrue();
  }

  @Test
  public void should_Return_False_If_Name_Does_Not_Match() {
    NameMatcher matcher = new NameMatcher("Hello");
    assertThat(matcher.matches(window.button)).isFalse();
  }
}
