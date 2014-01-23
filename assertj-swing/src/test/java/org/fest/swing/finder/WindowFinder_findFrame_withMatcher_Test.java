/*
 * Created on Aug 27, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.finder;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.query.ComponentShowingQuery.isShowing;

import javax.annotation.Nonnull;
import javax.swing.JFrame;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.test.swing.WindowLauncher.WindowToLaunch;
import org.junit.Test;

/**
 * Tests for {@link WindowFinder#findFrame(org.fest.swing.core.GenericTypeMatcher)}.
 *
 * @author Alex Ruiz
 */
public class WindowFinder_findFrame_withMatcher_Test extends WindowFinder_TestCase {
  private MyMatcher matcher;

  @Override
  void extraSetUp() {
    matcher = new MyMatcher();
  }

  @Test
  public void should_find_Frame() {
    clickLaunchFrameButton();
    FrameFixture found = WindowFinder.findFrame(matcher).using(robot);
    assertThat(found.target()).isInstanceOf(WindowToLaunch.class);
  }

  @Test
  public void should_find_Frame_before_given_timeout_expires() {
    clickLaunchFrameButton();
    FrameFixture found = WindowFinder.findFrame(matcher).withTimeout(500, MILLISECONDS).using(robot);
    assertThat(found.target()).isInstanceOf(WindowToLaunch.class);
  }

  @Test
  public void should_find_Frame_before_given_timeout_in_ms_expires() {
    clickLaunchFrameButton();
    FrameFixture found = WindowFinder.findFrame(matcher).withTimeout(500).using(robot);
    assertThat(found.target()).isInstanceOf(WindowToLaunch.class);
  }

  @Test(expected = WaitTimedOutError.class)
  public void should_fail_if_Frame_not_found() {
    WindowFinder.findFrame(matcher).using(robot);
  }

  private static class MyMatcher extends GenericTypeMatcher<JFrame> {
    private MyMatcher() {
      super(JFrame.class);
    }

    @Override
    protected boolean isMatching(@Nonnull JFrame frame) {
      return "frame".equals(frame.getName()) && isShowing(frame);
    }
  }
}
