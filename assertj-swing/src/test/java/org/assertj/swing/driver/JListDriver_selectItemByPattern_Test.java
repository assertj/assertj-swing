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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Pattern;

import org.assertj.swing.exception.LocationUnavailableException;
import org.junit.Test;

/**
 * Tests for {@link JListDriver#selectItem(javax.swing.JList, java.util.regex.Pattern)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver_selectItemByPattern_Test extends JListDriver_TestCase {
  @Test
  public void should_Throw_Error_If_A_Matching_Item_Was_Not_Found() {
    showWindow();
    thrown.expect(LocationUnavailableException.class,
        "Unable to find item matching the pattern 'ten' among the JList contents [\"one\", \"two\", \"three\"]");
    driver.selectItem(list, Pattern.compile("ten"));
  }

  @Test
  public void should_Not_Select_Item_If_Already_Selected() {
    select(1);
    showWindow();
    driver.selectItem(list, Pattern.compile("tw.*"));
    assertThat(selectedValue()).isEqualTo("two");
  }

  @Test
  public void should_Select_Item_Matching_Pattern() {
    showWindow();
    driver.selectItem(list, Pattern.compile("tw.*"));
    assertThat(selectedValue()).isEqualTo("two");
    assertThatCellReaderWasCalled();
  }

  @Test
  public void should_Throw_Error_If_JList_Is_Disabled() {
    disableList();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.selectItem(list, Pattern.compile("tw.*"));
  }

  @Test
  public void should_Throw_Error_If_JList_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.selectItem(list, Pattern.compile("tw.*"));
  }
}
