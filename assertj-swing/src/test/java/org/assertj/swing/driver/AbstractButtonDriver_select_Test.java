/*
 * Created on Aug 9, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.driver.AbstractButtonSelectedQuery.isSelected;

import org.junit.Test;

/**
 * Tests for {@link AbstractButtonDriver#select(javax.swing.AbstractButton)}.
 * 
 * @author Alex Ruiz
 */
public class AbstractButtonDriver_select_Test extends AbstractButtonDriver_TestCase {
  @Test
  public void should_not_do_anything_if_button_is_already_selected() {
    showWindow();
    selectCheckBox();
    driver.select(checkBox);
    assertThatCheckBoxIsSelected();
  }

  @Test
  public void should_select_button() {
    showWindow();
    unselectCheckBox();
    driver.select(checkBox);
    assertThatCheckBoxIsSelected();
  }

  private void assertThatCheckBoxIsSelected() {
    assertThat(isSelected(checkBox)).isTrue();
  }

  @Test
  public void should_throw_error_if_AbstractButton_disabled() {
    disableCheckBox();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.select(checkBox);
  }

  @Test
  public void should_throw_error_if_AbstractButton_is_not_showing_on_the_screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.select(checkBox);
  }
}
