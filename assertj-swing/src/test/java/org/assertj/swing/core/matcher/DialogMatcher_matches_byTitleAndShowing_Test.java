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

import org.assertj.swing.test.core.SequentialEDTSafeTestCase;
import org.assertj.swing.test.swing.TestDialog;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link DialogMatcher#matches(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class DialogMatcher_matches_byTitleAndShowing_Test extends SequentialEDTSafeTestCase {
  @Test
  public void should_Return_True_If_Dialog_Is_Showing_And_Title_Is_Equal_To_Expected() {
    String title = "Hello";
    JDialog dialog = dialog().withTitle(title).createAndShow();
    DialogMatcher matcher = DialogMatcher.withTitle(title).andShowing();
    assertThat(matcher.matches(dialog)).isTrue();
  }

  @Test
  public void should_Return_False_If_Dialog_Is_Not_Showing_And_Title_Is_Equal_To_Expected() {
    String title = "Hello";
    DialogMatcher matcher = DialogMatcher.withTitle(title).andShowing();
    JDialog dialog = dialog().withTitle(title).createNew();
    assertThat(matcher.matches(dialog)).isFalse();
  }

  @Test
  public void should_Return_False_If_Dialog_Is_Showing_And_Title_Is_Not_Equal_To_Expected() {
    TestWindow window = TestWindow.createAndShowNewWindow(DialogMatcher.class);
    TestDialog dialog = TestDialog.createAndShowNewDialog(window);
    DialogMatcher matcher = DialogMatcher.withTitle("Hello").andShowing();
    assertThat(matcher.matches(dialog)).isFalse();
  }

  @Test
  public void should_Return_False_If_Dialog_Is_Not_Showing_And_Title_Is_Not_Equal_To_Expected() {
    DialogMatcher matcher = DialogMatcher.withTitle("Hello").andShowing();
    JDialog dialog = dialog().withTitle("Bye").createNew();
    assertThat(matcher.matches(dialog)).isFalse();
  }
}
