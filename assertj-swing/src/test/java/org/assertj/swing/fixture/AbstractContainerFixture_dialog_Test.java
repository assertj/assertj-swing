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
package org.assertj.swing.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.core.util.Strings.quote;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.timing.Timeout.timeout;

import javax.annotation.Nonnull;
import javax.swing.JDialog;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.exception.WaitTimedOutError;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.WindowLauncher;
import org.assertj.swing.test.swing.WindowLauncher.DialogToLaunch;
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
  public void should_Find_Visible_Dialog_By_Name() {
    launchDialogNow();
    DialogFixture dialog = fixture.dialog("dialog");
    assertThat(dialog.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void should_Fail_If_Visible_Dialog_Not_Found_By_Name() {
    thrown.expect(WaitTimedOutError.class, "Timed out waiting for dialog to be found");
    fixture.dialog("dialog");
  }

  @Test
  public void should_Find_Visible_Dialog_By_Name_That_Needs_A_Couple_Of_Seconds_To_Appear() {
    launchDialogAfterWaitingFor(20000);
    DialogFixture dialog = fixture.dialog("dialog");
    assertThat(dialog.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void should_Find_Visible_Dialog_By_Name_With_Timeout() {
    launchDialogAfterWaitingFor(200);
    DialogFixture dialog = fixture.dialog("dialog", timeout(300));
    assertThat(dialog.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void should_Fail_If_Visible_Dialog_Not_Found_By_Name_With_Timeout() {
    thrown.expect(WaitTimedOutError.class, "Timed out waiting for dialog to be found");
    fixture.dialog("dialog", timeout(100));
  }

  @Test
  public void should_Find_Visible_Dialog_By_Type() {
    launchDialogNow();
    DialogFixture dialog = fixture.dialog();
    assertThat(dialog.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void should_Fail_If_Visible_Dialog_Not_Found_By_Type() {
    thrown.expect(WaitTimedOutError.class, "Timed out waiting for dialog to be found");
    fixture.dialog();
  }

  @Test
  public void should_Find_Visible_Dialog_By_Type_With_Timeout() {
    launchDialogAfterWaitingFor(200);
    DialogFixture dialog = fixture.dialog(timeout(300));
    assertThat(dialog.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void should_Fail_If_Visible_Dialog_Not_Found_By_Type_With_Timeout() {
    thrown.expect(WaitTimedOutError.class, "Timed out waiting for dialog to be found");
    fixture.dialog(timeout(100));
  }

  @Test
  public void should_Find_Visible_Dialog_By_Matcher() {
    launchDialogNow();
    DialogFixture dialog = fixture.dialog(new DialogByTitleMatcher());
    assertThat(dialog.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void should_Fail_If_Visible_Dialog_Not_Found_By_Matcher() {
    thrown.expect(WaitTimedOutError.class, "Timed out waiting for dialog to be found");
    fixture.dialog(new DialogByTitleMatcher());
  }

  @Test
  public void should_Find_Visible_Dialog_By_Matcher_With_Timeout() {
    launchDialogAfterWaitingFor(200);
    DialogFixture dialog = fixture.dialog(new DialogByTitleMatcher(), timeout(300));
    assertThat(dialog.target()).isInstanceOf(DialogToLaunch.class);
  }

  @Test
  public void should_Fail_If_Visible_Dialog_Not_Found_By_Matcher_With_Timeout() {
    thrown.expect(WaitTimedOutError.class, "Timed out waiting for dialog to be found");
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
