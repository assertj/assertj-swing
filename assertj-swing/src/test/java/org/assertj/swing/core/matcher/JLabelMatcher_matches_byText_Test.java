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
import static org.assertj.swing.test.builder.JLabels.label;

import javax.swing.JLabel;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.Test;

/**
 * Tests for {@link JLabelMatcher#matches(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class JLabelMatcher_matches_byText_Test extends EDTSafeTestCase {
  @Test
  public void should_Return_True_If_Text_Is_Equal_To_Expected() {
    JLabelMatcher matcher = JLabelMatcher.withText("Hello");
    JLabel label = label().withText("Hello").createNew();
    assertThat(matcher.matches(label)).isTrue();
  }

  @Test
  public void should_Return_True_If_Text_Matches_Pattern() {
    JLabelMatcher matcher = JLabelMatcher.withText("He.*");
    JLabel label = label().withText("Hello").createNew();
    assertThat(matcher.matches(label)).isTrue();
  }

  @Test
  public void should_Return_False_If_Text_Is_Not_Equal_To_Expected() {
    JLabelMatcher matcher = JLabelMatcher.withText("Hello");
    JLabel label = label().withText("Bye").createNew();
    assertThat(matcher.matches(label)).isFalse();
  }
}
