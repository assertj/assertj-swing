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
import static org.assertj.core.util.Arrays.array;

import org.junit.Test;

/**
 * Tests for {@link JListDriver#selectItems(javax.swing.JList, int[])}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver_selectItemsByIndex_Test extends JListDriver_TestCase {
  @Test
  public void should_Select_Items() {
    showWindow();
    driver.selectItems(list, new int[] { 1, 2 });
    assertThat(selectedValues()).isEqualTo(array("two", "three"));
  }

  @Test
  public void should_Select_Item() {
    showWindow();
    driver.selectItems(list, new int[] { 1 });
    assertThat(selectedValues()).isEqualTo(array("two"));
  }

  @Test
  public void should_Select_Items_Even_If_Already_Selected() {
    select(1, 2);
    showWindow();
    driver.selectItems(list, new int[] { 1, 2 });
    assertThat(selectedValues()).isEqualTo(array("two", "three"));
  }

  @Test
  public void should_Throw_Error_If_JList_Is_Disabled() {
    disableList();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.selectItems(list, new int[] { 1, 2 });
  }

  @Test
  public void should_Throw_Error_If_JList_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.selectItems(list, new int[] { 1, 2 });
  }
}
