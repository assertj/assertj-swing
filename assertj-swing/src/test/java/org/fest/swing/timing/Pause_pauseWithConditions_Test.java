/*
 * Created on Jul 31, 2009
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
package org.fest.swing.timing;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.util.StopWatch.startNewStopWatch;

import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.test.util.StopWatch;
import org.junit.Test;

/**
 * Tests for {@link Pause#pause(Condition[])}.
 * 
 * @author Alex Ruiz
 */
public class Pause_pauseWithConditions_Test {
  @Test
  public void should_wait_till_Conditions_are_satisfied() {
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
  public void should_timeout_if_Conditions_are_never_satisfied() {
    Pause.pause(new Condition[] { new NeverSatisfiedCondition(), new NeverSatisfiedCondition() });
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_Condition_array_is_null() {
    Pause.pause((Condition[]) null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_throw_error_if_Condition_array_is_empty() {
    Pause.pause(new Condition[0]);
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_any_Condition_is_null() {
    Pause.pause(new Condition[] { new NeverSatisfiedCondition(), null });
  }
}
