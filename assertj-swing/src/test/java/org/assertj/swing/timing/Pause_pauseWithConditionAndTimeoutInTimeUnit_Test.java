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
package org.assertj.swing.timing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.test.util.StopWatch.startNewStopWatch;
import static org.assertj.swing.timing.Timeout.timeout;

import java.util.concurrent.TimeUnit;

import org.assertj.swing.exception.WaitTimedOutError;
import org.assertj.swing.test.util.StopWatch;
import org.junit.Test;

/**
 * Tests for {@link Pause#pause(Condition, Timeout)}.
 * 
 * @author Alex Ruiz
 */
public class Pause_pauseWithConditionAndTimeoutInTimeUnit_Test {
  private static final int TIMEOUT = 1000;

  @Test(expected = WaitTimedOutError.class)
  public void should_Timeout_If_Condition_Is_Never_Satisfied() {
    Pause.pause(new NeverSatisfiedCondition(), timeout(TIMEOUT));
  }

  @Test(expected = WaitTimedOutError.class, timeout = 1100)
  public void should_Timeout_If_Condition_Runs_Longer_Than_Timeout() {
    Pause.pause(new SatisfiedCondition(10000), timeout(1, TimeUnit.SECONDS));
  }

  @Test
  public void should_Wait_Till_Condition_Is_Satisfied() {
    int timeToWaitTillSatisfied = TIMEOUT - 50;
    SatisfiedCondition condition = new SatisfiedCondition(timeToWaitTillSatisfied);

    StopWatch watch = startNewStopWatch();
    Pause.pause(condition, timeout(TIMEOUT));
    watch.stop();

    assertThat(watch.ellapsedTime() >= timeToWaitTillSatisfied).isTrue();
    assertThat(condition.satisfied).isTrue();
  }

  @Test(expected = NumberFormatException.class)
  public void should_Throw_Error_If_Condition_Throws_Any() {
    Pause.pause(new RuntimeExceptionCondition(new NumberFormatException("expected")), timeout(TIMEOUT));
  }

  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Condition_Is_Null() {
    Pause.pause((Condition) null, timeout(TIMEOUT));
  }

  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Timeout_Is_Null() {
    Pause.pause(new NeverSatisfiedCondition(), null);
  }
}
