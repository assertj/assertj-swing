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
import static org.assertj.swing.test.builder.JTextFields.textField;

import javax.swing.JTextField;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.Test;

/**
 * Tests for {@link JTextComponentMatcher#matches(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTextComponentMatcher_matches_byNameAndText_Test extends EDTSafeTestCase {
  @Test
  public void should_Return_True_If_Name_And_Text_Are_Equal_To_Expected() {
    JTextComponentMatcher matcher = JTextComponentMatcher.withName("textField").andText("Hello");
    JTextField textField = textField().withName("textField").withText("Hello").createNew();
    assertThat(matcher.matches(textField)).isTrue();
  }

  @Test
  public void should_Return_True_If_Name_Is_Equal_To_Expected_And_Text_Matches_Pattern() {
    JTextComponentMatcher matcher = JTextComponentMatcher.withName("textField").andText("He.*");
    JTextField textField = textField().withName("textField").withText("Hello").createNew();
    assertThat(matcher.matches(textField)).isTrue();
  }
}
