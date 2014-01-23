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

import javax.swing.JDialog;

import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestDialog;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link DialogMatcher#matches(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class DialogMatcher_matches_byTitleAndShowing_Test extends SequentialEDTSafeTestCase {
  @Test
  public void should_return_true_if_Dialog_is_showing_and_title_is_equal_to_expected() {
    String title = "Hello";
    JDialog dialog = dialog().withTitle(title).createAndShow();
    DialogMatcher matcher = DialogMatcher.withTitle(title).andShowing();
    assertThat(matcher.matches(dialog)).isTrue();
  }

  @Test
  public void should_return_false_if_Dialog_is_not_showing_and_title_is_equal_to_expected() {
    String title = "Hello";
    DialogMatcher matcher = DialogMatcher.withTitle(title).andShowing();
    JDialog dialog = dialog().withTitle(title).createNew();
    assertThat(matcher.matches(dialog)).isFalse();
  }

  @Test
  public void should_return_false_if_Dialog_is_showing_and_title_is_not_equal_to_expected() {
    TestWindow window = TestWindow.createAndShowNewWindow(DialogMatcher.class);
    TestDialog dialog = TestDialog.createAndShowNewDialog(window);
    DialogMatcher matcher = DialogMatcher.withTitle("Hello").andShowing();
    assertThat(matcher.matches(dialog)).isFalse();
  }

  @Test
  public void should_return_false_if_Dialog_is_not_showing_and_title_is_not_equal_to_expected() {
    DialogMatcher matcher = DialogMatcher.withTitle("Hello").andShowing();
    JDialog dialog = dialog().withTitle("Bye").createNew();
    assertThat(matcher.matches(dialog)).isFalse();
  }
}
