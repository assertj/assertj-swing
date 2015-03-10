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
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.timing.Timeout.timeout;

import org.assertj.swing.exception.WaitTimedOutError;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.JOptionPaneLauncherWindow;
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
  public void should_Find_Visible_JOptionPane_By_Type() {
    robot.showWindow(window);
    launchOptionPane(0);
    JOptionPaneFixture optionPane = launcher.optionPane();
    assertThat(optionPane.target()).isNotNull();
  }

  @Test
  public void should_Find_Visible_JOptionPane_By_Type_With_Timeout() {
    robot.showWindow(window);
    launchOptionPane(2000);
    JOptionPaneFixture optionPane = launcher.optionPane(timeout(3000));
    assertThat(optionPane.target()).isNotNull();
  }

  @Test
  public void should_Fail_If_Visible_JOptionPane_Not_Found_By_Type_With_Timeout() {
    thrown.expect(WaitTimedOutError.class);
    thrown.expectMessageToContain("Timed out waiting for option pane to be found");
    launcher.optionPane(timeout(100));
  }

  private void launchOptionPane(int delay) {
    window.launchDelay(delay);
    launcher.button("message").click();
  }
}
