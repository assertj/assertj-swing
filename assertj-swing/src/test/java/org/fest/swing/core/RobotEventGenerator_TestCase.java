/*
 * Created on Dec 29, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.core;

import static org.fest.swing.util.TestRobotFactories.newRobotFactoryMock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Robot;
import java.lang.reflect.Method;

import org.fest.swing.util.RobotFactory;
import org.junit.Before;

/**
 * Base test case for {@link RobotEventGenerator}.
 *
 * @author Alex Ruiz
 */
public abstract class RobotEventGenerator_TestCase {
  Robot robot;
  RobotEventGenerator eventGenerator;

  @Before
  public final void setUp() throws Exception {
    RobotFactory robotFactory = newRobotFactoryMock();
    robot = mock(Robot.class);
    when(robotFactory.newRobotInPrimaryScreen()).thenReturn(robot);
    eventGenerator = new RobotEventGenerator(robotFactory, new Settings());
  }

  final Method methodFromAWTRobot(String name, Class<?>... parameterTypes) throws Exception {
    return Robot.class.getDeclaredMethod(name, parameterTypes);
  }
}
