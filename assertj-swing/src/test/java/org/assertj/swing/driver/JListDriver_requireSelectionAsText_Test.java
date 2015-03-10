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

import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Tests for {@link JListDriver#requireSelection(javax.swing.JList, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver_requireSelectionAsText_Test extends JListDriver_TestCase {
  @Test
  public void should_Pass_If_Selection_Is_Equal_To_Expected() {
    selectFirstItem();
    driver.requireSelection(list, "one");
    assertThatCellReaderWasCalled();
  }

  @Test
  public void should_Pass_If_Selection_Matches_Pattern() {
    selectFirstItem();
    driver.requireSelection(list, "on.*");
    assertThatCellReaderWasCalled();
  }

  @Test
  public void should_Fail_If_There_Is_No_Selection() {
    clearSelection();
    thrown.expectAssertionError("property:'selectedIndex'");
    thrown.expectMessageToContain("No selection");
    driver.requireSelection(list, "one");
  }

  @Test
  public void should_Fail_If_Selection_Is_Not_Equal_To_Expected() {
    select(1);
    thrown.expectAssertionError("selectedIndex", "two", Pattern.compile("one"));
    driver.requireSelection(list, "one");
  }
}
