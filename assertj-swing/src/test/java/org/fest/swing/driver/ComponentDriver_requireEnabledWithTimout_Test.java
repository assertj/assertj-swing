/*
 * Created on Jul 19, 2009
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.util.StopWatch.startNewStopWatch;
import static org.fest.swing.timing.Timeout.timeout;

import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.test.util.StopWatch;
import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#requireEnabled(java.awt.Component, org.fest.swing.timing.Timeout)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_requireEnabledWithTimout_Test extends ComponentDriver_TestCase {
  @Test
  public void should_pass_if_Component_is_enabled_before_timeout() {
    driver.requireEnabled(window.button, timeout(100));
  }

  @Test
  public void should_fail_if_Component_is_not_enabled_before_timeout() {
    disableButton();
    int timeout = 1000;
    StopWatch stopWatch = startNewStopWatch();
    try {
      driver.requireEnabled(window.button, timeout(timeout));
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      assertThat(e.getMessage()).contains("Timed out waiting for").contains(window.button.getClass().getName())
      .contains("to be enabled");
    }
    stopWatch.stop();
    assertThatWaited(stopWatch, timeout);
  }
}
