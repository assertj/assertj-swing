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
 * Tests for {@link JTextComponentDriver#selectAll(javax.swing.text.JTextComponent)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTextComponentDriver_selectAll_Test extends JTextComponentDriver_TestCase {
  @Test
  public void should_Select_All_Text() {
    showWindow();
    setTextFieldText("Hello");
    driver.selectAll(textField);
    requireSelectedTextInTextField("Hello");
  }

  @Test
  public void should_Throw_Error_If_JTextComponent_Is_Disabled() {
    disableTextField();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.selectAll(textField);
  }

  @Test
  public void should_Throw_Error_If_JTextComponent_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.selectAll(textField);
  }
}
