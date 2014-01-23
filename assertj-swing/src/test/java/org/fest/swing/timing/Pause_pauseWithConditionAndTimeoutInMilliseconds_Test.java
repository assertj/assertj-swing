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

import org.fest.swing.exception.WaitTimedOutError;
import org.junit.Test;

/**
 * Tests for {@link Pause#pause(Condition, long)}.
 * 
 * @author Alex Ruiz
 */
public class Pause_pauseWithConditionAndTimeoutInMilliseconds_Test {
  @Test(expected = WaitTimedOutError.class)
  public void should_timeout_if_Condition_is_never_satisfied() {
    Pause.pause(new NeverSatisfiedCondition(), 1000);
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_condition_is_null() {
    Pause.pause((Condition) null, 1000);
  }
}
