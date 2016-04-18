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
 * Tests for {@link JListDriver#requireSelectedItems(javax.swing.JList, java.util.regex.Pattern...)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver_requireSelectedItemsAsPattern_Test extends JListDriver_TestCase {
  @Test
  public void should_Fail_If_There_Is_No_Selection() {
    clearSelection();
    thrown.expect(AssertionError.class);
    thrown.expectMessageToContain("property:'selectedIndices'", "expected:<[[\"one\", \"two\"]]> but was:<[[]]>");
    driver.requireSelectedItems(list, Pattern.compile("one"), Pattern.compile("two"));
  }

  @Test
  public void should_Fail_If_Selection_Is_Not_Equal_To_Expected() {
    select(2);
    thrown.expect(AssertionError.class);
    thrown.expectMessageToContain("property:'selectedIndices'", "expected:<[\"[on]e\"]> but was:<[\"[thre]e\"]>");
    driver.requireSelectedItems(list, Pattern.compile("one"));
  }

  @Test
  public void should_Pass_If_Selection_Is_Equal_To_Expected() {
    select(0, 1);
    driver.requireSelectedItems(list, Pattern.compile("two"), Pattern.compile("one"));
    assertThatCellReaderWasCalled();
  }

  @Test
  public void should_Pass_If_Selection_Matches_Pattern() {
    select(1, 2);
    driver.requireSelectedItems(list, Pattern.compile("t.*"));
    assertThatCellReaderWasCalled();
  }

  @Test
  public void should_Pass_If_Selection_Matches_Patterns() {
    select(0, 1);
    driver.requireSelectedItems(list, Pattern.compile("tw.*"), Pattern.compile("o.*"));
    assertThatCellReaderWasCalled();
  }
}
