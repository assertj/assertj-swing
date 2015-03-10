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
 * Tests for {@link JTextComponentDriver#requireEmpty(javax.swing.text.JTextComponent)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTextComponentDriver_requireEmpty_Test extends JTextComponentDriver_TestCase {
  @Test
  public void should_Pass_If_JTexComponent_Is_Empty() {
    clearTextField();
    driver.requireEmpty(textField);
  }

  @Test
  public void should_Pass_If_Text_In_JTexComponent_Is_Null() {
    setTextFieldText(null);
    driver.requireEmpty(textField);
  }

  @Test
  public void should_Fail_If_JTexComponent_Is_Not_Empty() {
    setTextFieldText("Hi");
    thrown.expect(AssertionError.class);
    thrown.expectMessageToContain("property:'text'", "Expecting empty but was:<\"Hi\">");
    driver.requireEmpty(textField);
  }
}
