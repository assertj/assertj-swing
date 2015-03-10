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
import static org.assertj.swing.test.builder.JOptionPanes.optionPane;
import static org.assertj.swing.test.swing.JOptionPaneLauncher.launch;

import javax.swing.JOptionPane;

import org.assertj.swing.test.core.RobotBasedTestCase;
import org.junit.Test;

/**
 * Tests for {@link JOptionPaneFixture#JOptionPaneFixture(org.assertj.swing.core.Robot)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JOptionPaneFixture_constructor_withRobot_Test extends RobotBasedTestCase {
  private JOptionPane target;

  @Override
  protected void onSetUp() {
    target = optionPane().withMessage("Hello").createNew();
    launch(target);
  }

  @Test
  public void should_Lookup_Showing_JOptionPane() {
    JOptionPaneFixture fixture = new JOptionPaneFixture(robot);
    assertThat(fixture.robot()).isSameAs(robot);
    assertThat(fixture.target()).isSameAs(target);
  }
}
