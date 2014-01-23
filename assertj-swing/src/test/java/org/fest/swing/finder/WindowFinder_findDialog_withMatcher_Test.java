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
import javax.swing.JDialog;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.test.swing.WindowLauncher.DialogToLaunch;
import org.junit.Test;

/**
 * Tests for {@link WindowFinder#findDialog(org.fest.swing.core.GenericTypeMatcher)}.
 *
 * @author Alex Ruiz
 */
public class WindowFinder_findDialog_withMatcher_Test extends WindowFinder_TestCase {
  private MyMatcher matcher;

  @Override
  void extraSetUp() {
    matcher = new MyMatcher();
  }

  @Test
  public void should_find_Dialog() {
    clickLaunchDialogButton();
    DialogFixture found = WindowFinder.findDialog(matcher).using(robot);
    assertThat(found.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void should_find_Dialog_before_given_timeout_expires() {
    clickLaunchDialogButton();
    DialogFixture found = WindowFinder.findDialog(matcher).withTimeout(500, MILLISECONDS).using(robot);
    assertThat(found.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void should_find_Dialog_before_given_timeout_in_ms_expires() {
    clickLaunchDialogButton();
    DialogFixture found = WindowFinder.findDialog(matcher).withTimeout(500).using(robot);
    assertThat(found.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test(expected = WaitTimedOutError.class)
  public void should_fail_if_Dialog_not_found() {
    WindowFinder.findDialog(matcher).using(robot);
  }

  private static class MyMatcher extends GenericTypeMatcher<JDialog> {
    private MyMatcher() {
      super(JDialog.class);
    }

    @Override
    protected boolean isMatching(@Nonnull JDialog dialog) {
      return "dialog".equals(dialog.getName()) && isShowing(dialog);
    }
  }
}
