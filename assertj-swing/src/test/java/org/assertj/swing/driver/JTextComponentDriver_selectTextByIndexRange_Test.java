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

import org.assertj.swing.exception.ActionFailedException;
import org.junit.Test;

/**
 * Tests for {@link JTextComponentDriver#selectText(javax.swing.text.JTextComponent, int, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTextComponentDriver_selectTextByIndexRange_Test extends JTextComponentDriver_TestCase {
  @Test
  public void should_Select_Text_Range() {
    showWindow();
    driver.selectText(textField, 8, 14);
    requireSelectedTextInTextField("a test");
  }

  @Test
  public void should_Throw_Error_If_JTextComponent_Is_Disabled() {
    disableTextField();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.selectText(textField, 8, 14);
  }

  @Test
  public void should_Throw_Error_If_JTextComponent_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.selectText(textField, 8, 14);
  }

  @Test
  public void should_Throw_Error_If_Indices_Are_Out_Of_Bounds() {
    showWindow();
    thrown.expect(ActionFailedException.class, "Unable to get location for index <20> in javax.swing.JTextField");
    driver.selectText(textField, 20, 22);
  }
}
