/*
 * Created on Feb 24, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.data.ZeroAndNegativeProvider.zeroAndNegative;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Strings.concat;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JScrollBarDriver#scrollUnitUp(javax.swing.JScrollBar, int)}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class JScrollBarDriver_scrollUnitUpWithTimes_withInvalidTimes_Test extends JScrollBarDriver_TestCase {
  private final int times;

  @Parameters
  public static Collection<Object[]> times() {
    return newArrayList(zeroAndNegative());
  }

  public JScrollBarDriver_scrollUnitUpWithTimes_withInvalidTimes_Test(int times) {
    this.times = times;
  }

  @Test
  public void should_throw_error_if_times_is_zero_or_negative() {
    try {
      driver.scrollUnitUp(scrollBar, times);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      String message = concat("The number of times to scroll up one unit should be greater than zero, but was <",
          times, ">");
      assertThat(expected.getMessage()).isEqualTo(message);
    }
  }
}
