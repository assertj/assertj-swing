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
 * Tests for {@link JComboBoxDriver#requireItemCount(javax.swing.JComboBox, int)}.
 * 
 * @author Alex Ruiz
 */
public class JComboBoxDriver_requireItemCount_Test extends JComboBoxDriver_TestCase {
  @Test
  public void should_Fail_If_JList_Does_Not_Have_Expected_Item_Count() {
    thrown.expectAssertionError("property:'itemCount'");
    thrown.expectMessageToContain("expected:<[6]> but was:<[3]>");
    driver.requireItemCount(comboBox, 6);
  }

  @Test
  public void should_Pass_If_JList_Has_Expected_Item_Count() {
    driver.requireItemCount(comboBox, 3);
  }
}
