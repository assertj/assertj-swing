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
import static org.fest.swing.test.builder.JDialogs.dialog;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;
import java.util.regex.Pattern;

import javax.swing.JDialog;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link DialogMatcher#matches(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class DialogMatcher_matches_byNameAndTitlePattern_withNoMatch_Test extends EDTSafeTestCase {
  private final String name;
  private final Pattern title;

  @Parameters
  public static Collection<Object[]> namesAndTitles() {
    return newArrayList(new Object[][] {
        { "someName", Pattern.compile("title") },
        { "name", Pattern.compile("someTitle") },
        { "name", Pattern.compile("title") }
      });
  }

  public DialogMatcher_matches_byNameAndTitlePattern_withNoMatch_Test(String name, Pattern title) {
    this.name = name;
    this.title = title;
  }

  @Test
  public void should_return_false_if_name_is_not_equal_to_expected_or_title_does_not_match_pattern() {
    DialogMatcher matcher = DialogMatcher.withName(name).andTitle(title);
    JDialog dialog = dialog().withName("someName").withTitle("someTitle").createNew();
    assertThat(matcher.matches(dialog)).isFalse();
  }
}
