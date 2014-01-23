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
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.builder.JLabels.label;

import javax.swing.JLabel;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JLabelMatcher#matches(java.awt.Component)}.
 *
 * @author Alex Ruiz
 */
public class JLabelMatcher_matches_byTextAndShowing_Test extends SequentialEDTSafeTestCase {
  @Test
  public void should_return_true_if_JLabel_is_showing_and_text_is_equal_to_expected() {
    MyWindow window = MyWindow.createAndShow();
    JLabelMatcher matcher = JLabelMatcher.withText("Hello").andShowing();
    assertThat(matcher.matches(window.label)).isTrue();
  }

  @Test
  public void should_return_false_if_JLabel_is_not_showing_and_text_is_equal_to_expected() {
    JLabelMatcher matcher = JLabelMatcher.withText("Hello").andShowing();
    JLabel label = label().withText("Hello").createNew();
    assertThat(matcher.matches(label)).isFalse();
  }

  @Test
  public void should_return_false_if_JLabel_is_showing_and_text_is_not_equal_to_expected() {
    MyWindow window = MyWindow.createAndShow();
    JLabelMatcher matcher = JLabelMatcher.withText("Bye").andShowing();
    assertThat(matcher.matches(window.label)).isFalse();
  }

  @Test
  public void should_return_false_if_JLabel_is_not_showing_and_text_is_not_equal_to_expected() {
    JLabelMatcher matcher = JLabelMatcher.withText("Hello").andShowing();
    JLabel label = label().withText("Bye").createNew();
    assertThat(matcher.matches(label)).isFalse();
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createAndShow() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return display(new MyWindow());
        }
      });
    }

    final JLabel label = new JLabel("Hello");

    private MyWindow() {
      super(JLabelMatcher_matches_byTextAndShowing_Test.class);
      addComponents(label);
    }
  }
}
