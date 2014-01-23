/*
 * Created on Aug 24, 2009
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
package org.fest.swing.monitor;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.monitor.TestWindows.singletonWindowsMock;
import static org.fest.swing.util.TestRobotFactories.newRobotFactoryMock;
import static org.mockito.Mockito.when;

import java.awt.AWTException;

import org.fest.swing.util.RobotFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link WindowStatus#WindowStatus(Windows, org.fest.swing.util.RobotFactory)}.
 *
 * @author Alex Ruiz
 */
public class WindowStatus_constructor_Test {
  private RobotFactory factory;
  private Windows windows;

  @Before
  public void setUp() {
    factory = newRobotFactoryMock();
    windows = singletonWindowsMock();
  }

  @Test
  public void should_have_null_Robot_if_Robot_cannot_be_created() throws AWTException {
    when(factory.newRobotInPrimaryScreen()).thenThrow(new AWTException("Thrown on purpose"));
    WindowStatus status = new WindowStatus(windows, factory);
    assertThat(status.robot).isNull();
  }

}
