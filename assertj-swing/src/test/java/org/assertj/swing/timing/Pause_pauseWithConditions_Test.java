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

import org.assertj.swing.exception.WaitTimedOutError;
import org.assertj.swing.test.util.StopWatch;
import org.junit.Test;

/**
 * Tests for {@link Pause#pause(Condition[])}.
 * 
 * @author Alex Ruiz
 */
public class Pause_pauseWithConditions_Test {
  @Test
  public void should_Wait_Till_Conditions_Are_Satisfied() {
    int timeToWaitTillSatisfied = 1000;
    SatisfiedCondition one = new SatisfiedCondition(timeToWaitTillSatisfied);
    SatisfiedCondition two = new SatisfiedCondition(timeToWaitTillSatisfied);
    StopWatch watch = startNewStopWatch();
    Pause.pause(new Condition[] { one, two });
    watch.stop();
    assertThat(watch.ellapsedTime() >= timeToWaitTillSatisfied).isTrue();
    assertThat(one.satisfied).isTrue();
    assertThat(two.satisfied).isTrue();
  }

  @Test(expected = WaitTimedOutError.class)
  public void should_Timeout_If_Conditions_Are_Never_Satisfied() {
    Pause.pause(new Condition[] { new NeverSatisfiedCondition(), new NeverSatisfiedCondition() });
  }

  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Condition_Array_Is_Null() {
    Pause.pause((Condition[]) null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_Throw_Error_If_Condition_Array_Is_Empty() {
    Pause.pause(new Condition[0]);
  }

  @Test(expected = WaitTimedOutError.class, timeout = 30100)
  public void should_Timeout_If_Conditions_Together_Run_Longer_Than_Timeout() {
    Pause.pause(new Condition[] { new SatisfiedCondition(15000), new SatisfiedCondition(20000) });
  }

  @Test(expected = WaitTimedOutError.class, timeout = 30100)
  public void should_Timeout_If_Any_Condition_Runs_Longer_Than_Timeout() {
    Pause.pause(new Condition[] { new SatisfiedCondition(40000) });
  }

  @Test(expected = NumberFormatException.class)
  public void should_Throw_Error_If_Any_Condition_Throws_Any() {
    Pause.pause(new Condition[] { new RuntimeExceptionCondition(new NumberFormatException("expected")) });
  }

  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Any_Condition_Is_Null() {
    Pause.pause(new Condition[] { new NeverSatisfiedCondition(), null });
  }
}
