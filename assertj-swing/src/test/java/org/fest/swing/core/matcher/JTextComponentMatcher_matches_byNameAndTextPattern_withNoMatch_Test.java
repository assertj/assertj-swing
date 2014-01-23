/*
 * Created on Jul 16, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.core.matcher;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;
import java.util.regex.Pattern;

import javax.swing.JTextField;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JTextComponentMatcher#matches(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class JTextComponentMatcher_matches_byNameAndTextPattern_withNoMatch_Test extends EDTSafeTestCase {
  private final String name;
  private final Pattern text;

  @Parameters
  public static Collection<Object[]> namesAndText() {
    return newArrayList(new Object[][] {
        { "someName", Pattern.compile("text") },
        { "name", Pattern.compile("someText") },
        { "name", Pattern.compile("text") } });
  }

  public JTextComponentMatcher_matches_byNameAndTextPattern_withNoMatch_Test(String name, Pattern text) {
    this.name = name;
    this.text = text;
  }

  @Test
  public void should_return_false_if_name_is_not_equal_to_expected_or_text_does_not_match_pattern() {
    JTextComponentMatcher matcher = JTextComponentMatcher.withName(name).andText(text);
    JTextField textField = textField().withName("someName").withText("someText").createNew();
    assertThat(matcher.matches(textField)).isFalse();
  }
}
