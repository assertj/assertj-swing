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

import org.junit.Test;

/**
 * Tests for {@link JTabbedPaneLocation#indexOf(javax.swing.JTabbedPane, String)}.
 * 
 * @author Yvonne Wang
 */
public class JTabbedPaneLocation_validateIndex_Test extends JTabbedPaneLocation_TestCase {
  @Test
  public void should_Pass_If_Index_If_Valid() {
    location.checkIndexInBounds(tabbedPane, 0);
  }

  @Test
  public void should_Fail_If_Index_Is_Negative() {
    thrown
        .expectIndexOutOfBoundsException("Index <-1> is not within the JTabbedPane bounds of <0> and <1> (inclusive)");
    location.checkIndexInBounds(tabbedPane, -1);
  }

  @Test
  public void should_Fail_If_Index_Is_Out_Of_Bounds() {
    thrown.expectIndexOutOfBoundsException("Index <2> is not within the JTabbedPane bounds of <0> and <1> (inclusive)");
    location.checkIndexInBounds(tabbedPane, 2);
  }
}
