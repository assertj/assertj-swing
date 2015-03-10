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
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.builder.JButtons.button;

import javax.swing.JButton;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.SequentialEDTSafeTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JButtonMatcher#matches(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JButtonMatcher_matches_byTextAndShowing_Test extends SequentialEDTSafeTestCase {
  @Test
  public void should_Return_True_If_JButton_Is_Showing_And_Text_Is_Equal_To_Expected() {
    MyWindow window = MyWindow.createAndShow();
    JButtonMatcher matcher = JButtonMatcher.withText("Hello").andShowing();
    assertThat(matcher.matches(window.button)).isTrue();
  }

  @Test
  public void should_Return_False_If_JButton_Is_Not_Showing_And_Text_Is_Equal_To_Expected() {
    JButtonMatcher matcher = JButtonMatcher.withText("Hello").andShowing();
    JButton button = button().withText("Hello").createNew();
    assertThat(matcher.matches(button)).isFalse();
  }

  @Test
  public void should_Return_False_If_JButton_Is_Showing_And_Text_Is_Not_Equal_To_Expected() {
    MyWindow window = MyWindow.createAndShow();
    JButtonMatcher matcher = JButtonMatcher.withText("Bye").andShowing();
    assertThat(matcher.matches(window.button)).isFalse();
  }

  @Test
  public void should_Return_False_If_JButton_Is_Not_Showing_And_Text_Is_Not_Equal_To_Expected() {
    JButtonMatcher matcher = JButtonMatcher.withText("Hello").andShowing();
    JButton button = button().withText("Bye").createNew();
    assertThat(matcher.matches(button)).isFalse();
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

    final JButton button = new JButton("Hello");

    private MyWindow() {
      super(JButtonMatcher_matches_byTextAndShowing_Test.class);
      addComponents(button);
    }
  }
}
