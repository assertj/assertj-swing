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

import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.swing.test.data.ZeroAndNegativeProvider.zeroAndNegative;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JSpinnerDriver#decrement(javax.swing.JSpinner, int)}.
 * 
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class JSpinnerDriver_decrementWithTimes_withInvalidTimes_Test extends JSpinnerDriver_TestCase {
  private final int times;

  @Parameters
  public static Collection<Object[]> times() {
    return newArrayList(zeroAndNegative());
  }

  public JSpinnerDriver_decrementWithTimes_withInvalidTimes_Test(int times) {
    this.times = times;
  }

  @Test
  public void should_Throw_Error_If_Times_Is_Zero_Or_Negative() {
    thrown.expectIllegalArgumentException(concat(
        "The number of times to decrement the value should be greater than zero, but was <", times, ">"));
    driver.decrement(spinner, times);
  }
}
