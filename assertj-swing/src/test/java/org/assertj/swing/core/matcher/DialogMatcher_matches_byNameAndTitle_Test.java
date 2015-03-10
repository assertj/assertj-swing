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
import static org.assertj.swing.test.builder.JDialogs.dialog;

import javax.swing.JDialog;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.Test;

/**
 * Tests for {@link DialogMatcher#matches(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class DialogMatcher_matches_byNameAndTitle_Test extends EDTSafeTestCase {
  @Test
  public void should_Return_True_If_Name_And_Title_Are_Equal_To_Expected() {
    DialogMatcher matcher = DialogMatcher.withName("dialog").andTitle("Hello");
    JDialog dialog = dialog().withName("dialog").withTitle("Hello").createNew();
    assertThat(matcher.matches(dialog)).isTrue();
  }

  @Test
  public void should_Return_True_If_Name_Is_Equal_To_Expected_And_Title_Matches_Pattern() {
    DialogMatcher matcher = DialogMatcher.withName("dialog").andTitle("Hel.*");
    JDialog dialog = dialog().withName("dialog").withTitle("Hello").createNew();
    assertThat(matcher.matches(dialog)).isTrue();
  }
}
