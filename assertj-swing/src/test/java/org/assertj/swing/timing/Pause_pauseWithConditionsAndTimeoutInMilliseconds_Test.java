/*
 * Created on Dec 19, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.assertj.swing.timing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.test.util.StopWatch.startNewStopWatch;

import org.assertj.swing.exception.WaitTimedOutError;
import org.assertj.swing.test.util.StopWatch;
import org.junit.Test;

/**
 * Tests for {@link Pause#pause(Condition[], long)}.
 * 
 * @author Alex Ruiz
 */
public class Pause_pauseWithConditionsAndTimeoutInMilliseconds_Test {
  @Test(expected = WaitTimedOutError.class)
  public void should_timeout_if_Conditions_are_never_satisfied() {
    Pause.pause(new Condition[] { new NeverSatisfiedCondition(), new NeverSatisfiedCondition() }, 1000);
  }

  @Test(expected = WaitTimedOutError.class)
  public void should_timeout_if_one_Condition_is_never_satisfied() {
    Pause.pause(new Condition[] { new SatisfiedCondition(10), new NeverSatisfiedCondition() }, 1000);
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_Condition_array_is_null() {
    Pause.pause((Condition[]) null, 1000);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_throw_error_if_Condition_array_is_empty() {
    Pause.pause(new Condition[0], 1000);
  }

  @Test(expected = WaitTimedOutError.class, timeout = 1100)
  public void should_timeout_if_Conditions_together_run_longer_than_timeout() {
    Pause.pause(new Condition[] { new SatisfiedCondition(1000), new SatisfiedCondition(1000) }, 1000);
  }

  @Test(expected = WaitTimedOutError.class, timeout = 1100)
  public void should_timeout_if_any_Condition_runs_longer_than_timeout() {
    Pause.pause(new Condition[] { new SatisfiedCondition(10000) }, 1000);
  }

  @Test
  public void should_wait_till_Conditions_are_satisfied() {
    int timeToWaitTillSatisfied = 1000;
    SatisfiedCondition one = new SatisfiedCondition(timeToWaitTillSatisfied);
    SatisfiedCondition two = new SatisfiedCondition(timeToWaitTillSatisfied);
    StopWatch watch = startNewStopWatch();
    Pause.pause(new Condition[] { one, two }, 2 * timeToWaitTillSatisfied + 100);
    watch.stop();
    assertThat(watch.ellapsedTime() >= timeToWaitTillSatisfied).isTrue();
    assertThat(one.satisfied).isTrue();
    assertThat(two.satisfied).isTrue();
  }

  @Test(expected = NumberFormatException.class)
  public void should_throw_error_if_any_Condition_throws_any() {
    Pause.pause(new Condition[] { new RuntimeExceptionCondition(new NumberFormatException("expected")) }, 1000);
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_any_Condition_in_array_is_null() {
    Pause.pause(new Condition[] { new NeverSatisfiedCondition(), null }, 1000);
  };
}
