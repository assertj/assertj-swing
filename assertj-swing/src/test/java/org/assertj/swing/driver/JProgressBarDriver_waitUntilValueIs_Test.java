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

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.driver.JProgressBarIncrementValueAsyncTask.with;
import static org.assertj.swing.driver.JProgressBarValueQuery.valueOf;

import javax.swing.JProgressBar;

import org.assertj.swing.exception.WaitTimedOutError;
import org.junit.Test;

/**
 * Tests for {@link JProgressBarDriver#waitUntilValueIs(JProgressBar, int)}.
 * 
 * @author Alex Ruiz
 */
public class JProgressBarDriver_waitUntilValueIs_Test extends JProgressBarDriver_TestCase {
  @Test
  public void should_Throw_Error_If_Expected_Value_Is_Less_Than_Minimum() {
    thrown.expectIllegalArgumentException("Value <-1> should be between <[0, 100]>");
    driver.waitUntilValueIs(progressBar, -1);
  }

  @Test
  public void should_Throw_Error_If_Expected_Value_Is_Greater_Than_Maximum() {
    thrown.expectIllegalArgumentException("Value <200> should be between <[0, 100]>");
    driver.waitUntilValueIs(progressBar, 200);
  }

  @Test
  public void should_Wait_Until_Value_Is_Equal_To_Expected() {
    updateValueTo(10);
    JProgressBarIncrementValueAsyncTask task = with(progressBar).increment(20).every(1, SECONDS).createTask(robot);
    try {
      task.runAsynchronously();
      driver.waitUntilValueIs(progressBar, 70);
      assertThat(valueOf(progressBar)).isEqualTo(70);
    } finally {
      task.cancelIfNotFinished();
    }
  }

  @Test
  public void should_Time_Out_If_Expected_Value_Never_Reached() {
    thrown.expect(WaitTimedOutError.class, "Timed out waiting for value");
    thrown.expectMessageToContain("to be equal to 100");
    driver.waitUntilValueIs(progressBar, 100);
  }
}
