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
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.core.TestRobots.newRobotMock;
import static org.mockito.Mockito.when;

import org.assertj.swing.core.Robot;
import org.assertj.swing.core.Settings;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#settings()}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_settings_Test {
  private Robot robot;
  private ComponentDriver driver;

  @Before
  public void setUp() {
    robot = newRobotMock();
    driver = new ComponentDriver(robot);
  }

  @Test
  public void should_Return_Settings_From_Robot() {
    Settings settings = new Settings();
    when(robot.settings()).thenReturn(settings);
    assertThat(driver.settings()).isSameAs(settings);
  }
}
