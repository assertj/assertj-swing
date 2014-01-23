/*
 * Created on Nov 18, 2009
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
import static org.fest.swing.test.builder.JOptionPanes.optionPane;
import static org.fest.swing.test.swing.JOptionPaneLauncher.launch;

import javax.swing.JOptionPane;

import org.fest.swing.test.core.RobotBasedTestCase;
import org.junit.Test;

/**
 * Tests for {@link JOptionPaneFixture#JOptionPaneFixture(org.fest.swing.core.Robot)}.
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
  public void should_lookup_showing_JOptionPane() {
    JOptionPaneFixture fixture = new JOptionPaneFixture(robot);
    assertThat(fixture.robot()).isSameAs(robot);
    assertThat(fixture.target()).isSameAs(target);
  }
}
