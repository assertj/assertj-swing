/*
 * Created on Dec 4, 2009
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

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JProgressBarIncrementValueAsyncTask.with;
import static org.fest.swing.driver.JProgressBarValueQuery.valueOf;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.timing.Timeout.timeout;

import javax.swing.JProgressBar;

import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.timing.Timeout;
import org.junit.Test;

/**
 * Tests for {@link JProgressBarDriver#waitUntilValueIs(JProgressBar, int, Timeout)}.
 *
 * @author Alex Ruiz
 */
public class JProgressBarDriver_waitUntilValueIs_withTimeout_Test extends JProgressBarDriver_TestCase {
  @Test
  public void should_throw_error_if_expected_value_is_less_than_minimum() {
    try {
      driver.waitUntilValueIs(progressBar, -1, timeout(2, SECONDS));
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("Value <-1> should be between <[0, 100]>");
    }
  }

  @Test
  public void should_throw_error_if_expected_value_is_greater_than_maximum() {
    try {
      driver.waitUntilValueIs(progressBar, 200, timeout(2, SECONDS));
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("Value <200> should be between <[0, 100]>");
    }
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_timeout_is_null() {
    driver.waitUntilValueIs(progressBar, 10, null);
  }

  @Test
  public void should_wait_until_value_is_equal_to_expected() {
    updateValueTo(10);
    JProgressBarIncrementValueAsyncTask task = with(progressBar).increment(20).every(1, SECONDS).createTask(robot);
    try {
      task.runAsynchronously();
      driver.waitUntilValueIs(progressBar, 70, timeout(5, SECONDS));
      assertThat(valueOf(progressBar)).isEqualTo(70);
    } finally {
      task.cancelIfNotFinished();
    }
  }

  @Test
  public void should_time_out_if_expected_value_never_reached() {
    try {
      driver.waitUntilValueIs(progressBar, 100, timeout(1, SECONDS));
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      assertThat(e.getMessage()).contains("Timed out waiting for value").contains("to be equal to 100");
    }
  }
}
