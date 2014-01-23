/*
 * Created on Nov 21, 2008
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.util.StopWatch.startNewStopWatch;
import static org.fest.swing.timing.Pause.pause;

import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.test.util.StopWatch;
import org.junit.Test;

/**
 * Tests for {@link ComponentShownWaiter#waitTillShown(java.awt.Component, long)}.
 * 
 * @author Alex Ruiz
 */
public class ComponentShownWaiter_waitTillShownWithCustomTimeout_Test extends RobotBasedTestCase {
  private TestWindow window;

  @Override
  protected void onSetUp() {
    window = TestWindow.createNewWindow(getClass());
  }

  @Test
  public void should_timeout_if_Component_never_shown() {
    StopWatch stopWatch = startNewStopWatch();
    int timeout = 500;
    try {
      ComponentShownWaiter.waitTillShown(window, timeout);
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      stopWatch.stop();
    }
    assertThat(stopWatch.ellapsedTime()).isGreaterThanOrEqualTo(timeout);
  }

  @Test
  public void should_wait_till_Component_is_shown() {
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
  public void should_not_wait_if_Component_is_already_shown() {
    robot.showWindow(window);
    StopWatch stopWatch = startNewStopWatch();
    ComponentShownWaiter.waitTillShown(window, 10000);
    stopWatch.stop();
    assertThat(stopWatch.ellapsedTime()).isLessThan(10);
  }
}
