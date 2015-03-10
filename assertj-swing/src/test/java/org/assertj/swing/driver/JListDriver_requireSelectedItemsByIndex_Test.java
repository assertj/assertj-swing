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
 * Tests for {@link JListDriver#requireSelectedItems(javax.swing.JList, int...)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver_requireSelectedItemsByIndex_Test extends JListDriver_TestCase {
  @Test
  public void should_Fail_If_There_Is_No_Selection() {
    clearSelection();
    thrown.expect(AssertionError.class);
    thrown.expectMessageToContain("property:'selectedIndices'", "expected:<[[0, 1]]> but was:<[[]]>");
    driver.requireSelectedItems(list, 0, 1);
  }

  @Test
  public void should_Fail_If_Selection_Is_Not_Equal_To_Expected() {
    select(2);
    thrown.expect(AssertionError.class);
    thrown.expectMessageToContain("property:'selectedIndices'", "expected:<[[0]]> but was:<[[2]]>");
    driver.requireSelectedItems(list, 0);
  }

  @Test
  public void should_Pass_If_Selection_Is_Equal_To_Expected() {
    select(0, 1);
    driver.requireSelectedItems(list, 0, 1);
  }
}
