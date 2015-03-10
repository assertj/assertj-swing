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
package org.assertj.swing.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.util.TestRobotFactories.newRobotFactoryMock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.AWTException;
import java.awt.Robot;

import org.assertj.swing.exception.UnexpectedException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.util.RobotFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link RobotEventGenerator#RobotEventGenerator(org.assertj.swing.util.RobotFactory, Settings)}.
 * 
 * @author Alex Ruiz
 */
public class RobotEventGenerator_constructorWithRobotFactory_Test {
  private RobotFactory robotFactory;

  @Rule
  public ExpectedException thrown = none();

  @Before
  public void setUp() {
    robotFactory = newRobotFactoryMock();
  }

  @Test
  public void should_Use_RobotFactory_To_Create_AWTRobot() throws AWTException {
    Robot robot = mock(Robot.class);
    when(robotFactory.newRobotInPrimaryScreen()).thenReturn(robot);
    RobotEventGenerator eventGenerator = new RobotEventGenerator(robotFactory, new Settings());
    assertThat(eventGenerator.robot()).isSameAs(robot);
  }

  @Test
  public void should_Rethrow_Any_Error_From_RobotFactory() throws AWTException {
    AWTException toThrow = new AWTException("Thrown on purpose");
    when(robotFactory.newRobotInPrimaryScreen()).thenThrow(toThrow);
    thrown.expect(UnexpectedException.class);
    try {
      new RobotEventGenerator(robotFactory, new Settings());
    } catch (UnexpectedException e) {
      assertThat(e.getCause()).isSameAs(toThrow);
      throw e;
    }
  }
}
