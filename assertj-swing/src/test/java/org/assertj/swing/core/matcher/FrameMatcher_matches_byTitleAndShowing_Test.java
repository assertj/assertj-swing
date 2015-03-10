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
import static org.assertj.swing.test.builder.JFrames.frame;

import javax.swing.JFrame;

import org.assertj.swing.lock.ScreenLock;
import org.assertj.swing.test.core.EDTSafeTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link FrameMatcher#matches(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FrameMatcher_matches_byTitleAndShowing_Test extends EDTSafeTestCase {
  @Test
  public void should_Return_True_If_Frame_Is_Showing_And_Title_Is_Equal_To_Expected() {
    ScreenLock.instance().acquire(this);
    Class<FrameMatcher> testType = FrameMatcher.class;
    TestWindow frame = TestWindow.createAndShowNewWindow(testType);
    try {
      FrameMatcher matcher = FrameMatcher.withTitle(testType.getSimpleName()).andShowing();
      assertThat(matcher.matches(frame)).isTrue();
    } finally {
      try {
        frame.destroy();
      } catch (RuntimeException e) {
      }
      ScreenLock.instance().release(this);
    }
  }

  @Test
  public void should_Return_False_If_Frame_Is_Not_Showing_And_Title_Is_Equal_To_Expected() {
    String title = "Hello";
    FrameMatcher matcher = FrameMatcher.withTitle(title).andShowing();
    JFrame frame = frame().withTitle(title).createNew();
    assertThat(matcher.matches(frame)).isFalse();
  }

  @Test
  public void should_Return_False_If_Frame_Is_Showing_And_Title_Is_Not_Equal_To_Expected() {
    ScreenLock.instance().acquire(this);
    TestWindow frame = TestWindow.createAndShowNewWindow(FrameMatcher.class);
    try {
      FrameMatcher matcher = FrameMatcher.withTitle("Hello").andShowing();
      assertThat(matcher.matches(frame)).isFalse();
    } finally {
      try {
        frame.destroy();
      } catch (RuntimeException e) {
      }
      ScreenLock.instance().release(this);
    }
  }

  @Test
  public void should_Return_False_If_Frame_Is_Not_Showing_And_Title_Is_Not_Equal_To_Expected() {
    FrameMatcher matcher = FrameMatcher.withTitle("Hello").andShowing();
    JFrame frame = frame().withTitle("Bye").createNew();
    assertThat(matcher.matches(frame)).isFalse();
  }
}
