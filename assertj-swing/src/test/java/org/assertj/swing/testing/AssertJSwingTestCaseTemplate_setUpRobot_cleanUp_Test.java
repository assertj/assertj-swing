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
package org.assertj.swing.testing;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.hierarchy.ExistingHierarchy;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link AssertJSwingTestCaseTemplate#setUpRobot()} and {@link AssertJSwingTestCaseTemplate#cleanUp()}.
 * 
 * @author Alex Ruiz
 */
public class AssertJSwingTestCaseTemplate_setUpRobot_cleanUp_Test {
  private TestCase testCase;

  @Before
  public void setUp() {
    testCase = new TestCase();
  }

  @Test
  public void should_Create_Robot_On_SetUp_And_Inactivate_It_On_CleanUp() {
    testCase.setUpRobot();
    Robot robot = testCase.robot();
    assertThat(robot).isInstanceOf(BasicRobot.class);
    assertThat(robot.hierarchy()).isInstanceOf(ExistingHierarchy.class);
    assertThat(robot.isActive()).isTrue();
    testCase.cleanUp();
    assertThat(robot.isActive()).isFalse();
  }

  private static class TestCase extends AssertJSwingTestCaseTemplate {
  }
}
