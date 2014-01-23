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

import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.JOptionPaneLauncherWindow;
import org.fest.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests lookups of {@code JOptionPane}s in {@link AbstractContainerFixture}.
 *
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_optionPane_Test extends RobotBasedTestCase {
  @Rule
  public ExpectedException thrown = none();

  private ContainerFixture launcher;
  private JOptionPaneLauncherWindow window;

  @Override
  protected final void onSetUp() {
    window = JOptionPaneLauncherWindow.createNew(getClass());
    launcher = new ContainerFixture(robot, window);
  }

  @Test
  public void should_find_visible_JOptionPane_by_type() {
    robot.showWindow(window);
    launchOptionPane(0);
    JOptionPaneFixture optionPane = launcher.optionPane();
    assertThat(optionPane.target()).isNotNull();
  }

  @Test
  public void should_find_visible_JOptionPane_by_type_with_timeout() {
    robot.showWindow(window);
    launchOptionPane(200);
    JOptionPaneFixture optionPane = launcher.optionPane(timeout(300));
    assertThat(optionPane.target()).isNotNull();
  }

  @Test
  public void should_fail_if_visible_JOptionPane_not_found_by_type_with_timeout() {
    thrown.expect(WaitTimedOutError.class);
    thrown.expectMessageToContain("Timed out waiting for option pane to be found");
    launcher.optionPane(timeout(100));
  }

  private void launchOptionPane(int delay) {
    window.launchDelay(delay);
    launcher.button("message").click();
  }
}
