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
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.util.StopWatch.startNewStopWatch;
import static org.assertj.swing.timing.Pause.pause;

import org.assertj.swing.exception.WaitTimedOutError;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.assertj.swing.test.util.StopWatch;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link ComponentShownWaiter#waitTillShown(java.awt.Component, long)}.
 * 
 * @author Alex Ruiz
 */
public class ComponentShownWaiter_waitTillShownWithCustomTimeout_Test extends RobotBasedTestCase {
  @Rule
  public ExpectedException thrown = none();

  private TestWindow window;

  @Override
  protected void onSetUp() {
    window = TestWindow.createNewWindow(getClass());
  }

  @Test
  public void should_Timeout_If_Component_Never_Shown() {
    StopWatch stopWatch = startNewStopWatch();
    int timeout = 500;
    thrown.expect(WaitTimedOutError.class);
    try {
      ComponentShownWaiter.waitTillShown(window, timeout);
    } finally {
      stopWatch.stop();
      assertThat(stopWatch.ellapsedTime()).isGreaterThanOrEqualTo(timeout);
    }
  }

  @Test
  public void should_Wait_Till_Component_Is_Shown() {
    StopWatch stopWatch = startNewStopWatch();
    int timeout = 10000;
    new Thread() {
      @Override
      public void run() {
        pause(1000);
        robot.showWindow(window);
      }
    }.start();
    ComponentShownWaiter.waitTillShown(window, timeout);
    stopWatch.stop();
    assertThat(stopWatch.ellapsedTime()).isLessThan(timeout);
  }

  @Test
  public void should_Not_Wait_If_Component_Is_Already_Shown() {
    robot.showWindow(window);
    StopWatch stopWatch = startNewStopWatch();
    ComponentShownWaiter.waitTillShown(window, 10000);
    stopWatch.stop();
    assertThat(stopWatch.ellapsedTime()).isLessThan(10);
  }
}
