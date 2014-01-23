/*
 * Created on Apr 5, 2008
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

import org.junit.Test;

/**
 * Tests for {@link JTabbedPaneLocation#indexOf(javax.swing.JTabbedPane, String)}.
 * 
 * @author Yvonne Wang
 */
public class JTabbedPaneLocation_validateIndex_Test extends JTabbedPaneLocation_TestCase {
  @Test
  public void should_pass_if_index_if_valid() {
    location.checkIndexInBounds(tabbedPane, 0);
  }

  @Test
  public void should_fail_if_index_is_negative() {
    try {
      location.checkIndexInBounds(tabbedPane, -1);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException e) {
      assertThat(e.getMessage())
      .isEqualTo("Index <-1> is not within the JTabbedPane bounds of <0> and <1> (inclusive)");
    }
  }

  @Test
  public void should_fail_if_index_is_out_of_bounds() {
    try {
      location.checkIndexInBounds(tabbedPane, 2);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException e) {
      assertThat(e.getMessage()).isEqualTo("Index <2> is not within the JTabbedPane bounds of <0> and <1> (inclusive)");
    }
  }
}
