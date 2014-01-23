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

import java.util.concurrent.TimeUnit;

import org.fest.swing.test.util.StopWatch;
import org.junit.Test;

/**
 * Tests for {@link Pause#pause(long, java.util.concurrent.TimeUnit)}.
 * 
 * @author Alex Ruiz
 */
public class Pause_pauseWithTimeoutInTimeUnit_Test {
  @Test
  public void should_pause_for_the_given_amount_of_time() {
    StopWatch watch = startNewStopWatch();
    long delay = 2000;
    Pause.pause(2, TimeUnit.SECONDS);
    watch.stop();
    assertThat(watch.ellapsedTime() >= delay).isTrue();
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_unit_is_null() {
    Pause.pause(2, null);
  }
}
