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

import org.assertj.swing.exception.LocationUnavailableException;
import org.junit.Test;

/**
 * Tests for {@link JComboBoxDriver#selectItem(javax.swing.JComboBox, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxDriver_selectItemByText_Test extends JComboBoxDriver_TestCase {
  @Test
  public void should_Select_Item() {
    clearSelection();
    showWindow();
    driver.selectItem(comboBox, "third");
    assertThatSelectedItemIs("third");
  }

  @Test
  public void should_Select_Matching_Item() {
    clearSelection();
    showWindow();
    driver.selectItem(comboBox, "thi.*");
    assertThatSelectedItemIs("third");
  }

  @Test
  public void should_Not_Do_Anything_If_Item_Already_Selected() {
    showWindow();
    select(2);
    driver.selectItem(comboBox, "third");
    assertThatSelectedItemIs("third");
  }

  @Test
  public void should_Throw_Error_If_JComboBox_Is_Disabled() {
    disableComboBox();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.selectItem(comboBox, "first");
  }

  @Test
  public void should_Throw_Error_If_JComboBox_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.selectItem(comboBox, "first");
  }

  @Test(expected = LocationUnavailableException.class)
  public void should_Throw_Error_If_Item_Does_Not_Exist() {
    showWindow();
    driver.selectItem(comboBox, "hundred");
  }
}
