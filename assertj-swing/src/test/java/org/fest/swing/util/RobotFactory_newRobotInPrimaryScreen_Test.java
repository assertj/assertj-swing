/*
 * Created on Jun 3, 2009
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
package org.fest.swing.util;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.AWTException;
import java.awt.Robot;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link RobotFactory#newRobotInPrimaryScreen}.
 * 
 * @author Alex Ruiz
 */
public class RobotFactory_newRobotInPrimaryScreen_Test {
  private RobotFactory robotFactory;

  @Before
  public void setUp() {
    robotFactory = new RobotFactory();
  }

  @Test
  public void shouldCreateNewRobot() throws AWTException {
    Robot last = null;
    for (int i = 0; i < 6; i++) {
      Robot current = robotFactory.newRobotInPrimaryScreen();
      assertThat(current).isNotNull().isNotSameAs(last);
      last = current;
    }
  }
}
