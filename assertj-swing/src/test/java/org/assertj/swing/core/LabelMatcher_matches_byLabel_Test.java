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
 * Tests for {@link LabelMatcher#matches(java.awt.Component)}.
 * <p>
 * The {@link LabelMatcher} is created through {@link LabelMatcher#LabelMatcher(String)}.
 * </p>
 * 
 * @author Alex Ruiz
 */
public class LabelMatcher_matches_byLabel_Test extends LabelMatcher_TestCase {
  @Test
  public void should_Return_True_If_Label_Matches_And_Component_Is_Showing() {
    window.display();
    LabelMatcher matcher = new LabelMatcher(LABEL_TEXT);
    assertThat(matcher.matches(window.buttonLabel)).isTrue();
  }

  @Test
  public void should_Return_True_If_Label_Matches_And_Component_Is_Not_Showing() {
    LabelMatcher matcher = new LabelMatcher(LABEL_TEXT);
    assertThat(matcher.matches(window.buttonLabel)).isTrue();
  }

  @Test
  public void should_Return_False_If_Label_Matches_But_JLabel_Is_Not_Attached_To_Any_Component() {
    LabelMatcher matcher = new LabelMatcher(LABEL_TEXT);
    assertThat(matcher.matches(window.label)).isFalse();
  }

  @Test
  public void should_Return_False_If_Label_Does_Not_Match() {
    LabelMatcher matcher = new LabelMatcher("Bye");
    assertThat(matcher.matches(window.buttonLabel)).isFalse();
  }

  @Test
  public void should_Return_False_If_Component_Is_Not_JLabel() {
    LabelMatcher matcher = new LabelMatcher("Hello");
    assertThat(matcher.matches(window.button)).isFalse();
  }
}
