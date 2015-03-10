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
import javax.swing.JFileChooser;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.exception.WaitTimedOutError;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.JFileChooserLauncherWindow;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests lookups of {@code JFileChooser}s in {@link AbstractContainerFixture}.
 * 
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_fileChooser_Test extends RobotBasedTestCase {
  @Rule
  public ExpectedException thrown = none();

  private ContainerFixture fixture;
  private JFileChooserLauncherWindow window;

  @Override
  protected final void onSetUp() {
    window = JFileChooserLauncherWindow.createNew(getClass());
    fixture = new ContainerFixture(robot, window);
  }

  @Test
  public void should_Find_Visible_JFileChooser_By_Name() {
    robot.showWindow(window);
    launchFileChooserNow();
    JFileChooserFixture fileChooser = fixture.fileChooser("fileChooser");
    assertThat(fileChooser.target()).isSameAs(window.fileChooser());
  }

  @Test
  public void should_Fail_If_Visible_JFileChooser_Not_Found_By_Name() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "name='myFileChooser', type=javax.swing.JFileChooser, requireShowing=true");
    fixture.fileChooser("myFileChooser");
  }

  @Test
  public void should_Find_Visible_JFileChooser_By_Name_With_Timeout() {
    robot.showWindow(window);
    launchFileChooser(200);
    JFileChooserFixture fileChooser = fixture.fileChooser("fileChooser", timeout(300));
    assertThat(fileChooser.target()).isSameAs(window.fileChooser());
  }

  @Test
  public void should_Fail_If_Visible_JFileChooser_Not_Found_By_Name_With_Timeout() {
    thrown.expect(WaitTimedOutError.class, "Timed out waiting for file chooser to be found");
    fixture.fileChooser("fileChooser", timeout(300));
  }

  @Test
  public void should_Find_Visible_JFileChooser_By_Type() {
    robot.showWindow(window);
    launchFileChooserNow();
    JFileChooserFixture fileChooser = fixture.fileChooser();
    assertThat(fileChooser.target()).isSameAs(window.fileChooser());
  }

  @Test
  public void should_Fail_If_Visible_JFileChooser_Not_Found_By_Type() {
    thrown.expect(WaitTimedOutError.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "type=javax.swing.JFileChooser, requireShowing=true");
    fixture.fileChooser();
  }

  @Test
  public void should_Find_Visible_JFileChooser_By_Type_With_Timeout() {
    robot.showWindow(window);
    launchFileChooser(200);
    JFileChooserFixture fileChooser = fixture.fileChooser(timeout(300));
    assertThat(fileChooser.target()).isSameAs(window.fileChooser());
  }

  @Test
  public void should_Fail_If_Visible_JFileChooser_Not_Found_By_Type_With_Timeout() {
    thrown.expect(WaitTimedOutError.class, "Timed out waiting for file chooser to be found");
    fixture.fileChooser(timeout(100));
  }

  @Test
  public void should_Find_Visible_JFileChooser_By_Matcher() {
    robot.showWindow(window);
    launchFileChooserNow();
    JFileChooserFixture fileChooser = fixture.fileChooser(new JFileChooserByTitleMatcher());
    assertThat(fileChooser.target()).isSameAs(window.fileChooser());
  }

  @Test
  public void should_Fail_If_Visible_JFileChooser_Not_Found_By_Matcher() {
    thrown.expect(WaitTimedOutError.class, "Timed out waiting for file chooser to be found");
    fixture.fileChooser(new JFileChooserByTitleMatcher());
  }

  @Test
  public void should_Find_Visible_JFileChooser_By_Matcher_With_Timeout() {
    robot.showWindow(window);
    launchFileChooser(200);
    JFileChooserFixture fileChooser = fixture.fileChooser(new JFileChooserByTitleMatcher(), timeout(300));
    assertThat(fileChooser.target()).isSameAs(window.fileChooser());
  }

  @Test
  public void should_Fail_If_Visible_JFileChooser_Not_Found_By_Matcher_With_Timeout() {
    thrown.expect(WaitTimedOutError.class, "Timed out waiting for file chooser to be found");
    fixture.fileChooser(new JFileChooserByTitleMatcher(), timeout(300));
  }

  private void launchFileChooserNow() {
    launchFileChooser(0);
  }

  private void launchFileChooser(int delay) {
    window.launchDelay(delay);
    fixture.button("browse").click();
  }

  private static class JFileChooserByTitleMatcher extends GenericTypeMatcher<JFileChooser> {
    private static final String TITLE = "Launched JFileChooser";

    private JFileChooserByTitleMatcher() {
      super(JFileChooser.class);
    }

    @Override
    protected boolean isMatching(@Nonnull JFileChooser fileChooser) {
      return TITLE.equals(fileChooser.getDialogTitle());
    }

    @Override
    public @Nonnull String toString() {
      return concat("file chooser with title ", quote(TITLE));
    }
  }
}
