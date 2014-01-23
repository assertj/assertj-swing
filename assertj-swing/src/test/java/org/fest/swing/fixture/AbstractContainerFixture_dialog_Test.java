/*
 * Created on May 20, 2009
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.timing.Timeout.timeout;
import static org.fest.test.ExpectedException.none;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import javax.annotation.Nonnull;
import javax.swing.JDialog;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.WindowLauncher;
import org.fest.swing.test.swing.WindowLauncher.DialogToLaunch;
import org.fest.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests lookups of {@code Dialog}s in {@link AbstractContainerFixture}.
 *
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_dialog_Test extends RobotBasedTestCase {
  @Rule
  public ExpectedException thrown = none();

  private ContainerFixture fixture;
  private WindowLauncher window;

  @Override
  protected void onSetUp() {
    window = WindowLauncher.createNew(getClass());
    fixture = new ContainerFixture(robot, window);
  }

  @Test
  public void should_find_visible_Dialog_by_name() {
    launchDialogNow();
    DialogFixture dialog = fixture.dialog("dialog");
    assertThat(dialog.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void should_fail_if_visible_Dialog_not_found_by_name() {
    thrown.expect(WaitTimedOutError.class);
    thrown.expectMessageToContain("Timed out waiting for dialog to be found");
    fixture.dialog("dialog");
  }

  @Test
  public void should_find_visible_Dialog_by_name_with_timeout() {
    launchDialogAfterWaitingFor(200);
    DialogFixture dialog = fixture.dialog("dialog", timeout(300));
    assertThat(dialog.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void should_fail_if_visible_Dialog_not_found_by_name_with_timeout() {
    thrown.expect(WaitTimedOutError.class);
    thrown.expectMessageToContain("Timed out waiting for dialog to be found");
    fixture.dialog("dialog", timeout(100));
  }

  @Test
  public void should_find_visible_Dialog_by_type() {
    launchDialogNow();
    DialogFixture dialog = fixture.dialog();
    assertThat(dialog.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void should_fail_if_visible_Dialog_not_found_by_type() {
    thrown.expect(WaitTimedOutError.class);
    thrown.expectMessageToContain("Timed out waiting for dialog to be found");
    fixture.dialog();
  }

  @Test
  public void should_find_visible_Dialog_by_type_with_timeout() {
    launchDialogAfterWaitingFor(200);
    DialogFixture dialog = fixture.dialog(timeout(300));
    assertThat(dialog.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void should_fail_if_visible_Dialog_not_found_by_type_with_timeout() {
    thrown.expect(WaitTimedOutError.class);
    thrown.expectMessageToContain("Timed out waiting for dialog to be found");
    fixture.dialog(timeout(100));
  }

  @Test
  public void should_find_visible_Dialog_by_Matcher() {
    launchDialogNow();
    DialogFixture dialog = fixture.dialog(new DialogByTitleMatcher());
    assertThat(dialog.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void should_fail_if_visible_Dialog_not_found_by_Matcher() {
    thrown.expect(WaitTimedOutError.class);
    thrown.expectMessageToContain("Timed out waiting for dialog to be found");
    fixture.dialog(new DialogByTitleMatcher());
  }

  @Test
  public void should_find_visible_Dialog_by_Matcher_with_timeout() {
    launchDialogAfterWaitingFor(200);
    DialogFixture dialog = fixture.dialog(new DialogByTitleMatcher(), timeout(300));
    assertThat(dialog.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void should_fail_if_visible_Dialog_not_found_by_Matcher_with_timeout() {
    thrown.expect(WaitTimedOutError.class);
    thrown.expectMessageToContain("Timed out waiting for dialog to be found");
    fixture.dialog(new DialogByTitleMatcher(), timeout(100));
  }

  private void launchDialogNow() {
    launchDialogAfterWaitingFor(0);
  }

  private void launchDialogAfterWaitingFor(int delay) {
    robot.showWindow(window);
    window.dialogLaunchDelay(delay);
    fixture.button("launchDialog").click();
  }

  private static class DialogByTitleMatcher extends GenericTypeMatcher<JDialog> {
    private static final String TITLE = "Launched Dialog";

    DialogByTitleMatcher() {
      super(JDialog.class);
    }

    @Override
    protected boolean isMatching(@Nonnull JDialog dialog) {
      return TITLE.equals(dialog.getTitle());
    }

    @Override
    public @Nonnull String toString() {
      return concat("dialog with title ", quote(TITLE));
    }
  }
}
