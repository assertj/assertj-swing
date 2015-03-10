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
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.test.builder.JDialogs.dialog;

import java.util.Collection;

import javax.swing.JDialog;

import org.assertj.swing.test.core.EDTSafeTestCase;
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
public class DialogMatcher_matches_byNameAndTitle_withNoMatch_Test extends EDTSafeTestCase {
  private final String name;
  private final String title;

  @Parameters
  public static Collection<Object[]> namesAndTitles() {
    return newArrayList(new Object[][] { { "someName", "title" }, { "name", "someTitle" }, { "name", "title" } });
  }

  public DialogMatcher_matches_byNameAndTitle_withNoMatch_Test(String name, String title) {
    this.name = name;
    this.title = title;
  }

  @Test
  public void should_Return_False_If_Name_Or_Title_Are_Not_Equal_To_Expected() {
    DialogMatcher matcher = DialogMatcher.withName(name).andTitle(title);
    JDialog dialog = dialog().withName("someName").withTitle("someTitle").createNew();
    assertThat(matcher.matches(dialog)).isFalse();
  }
}
