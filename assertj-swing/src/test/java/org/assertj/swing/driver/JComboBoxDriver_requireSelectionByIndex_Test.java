/*
 * Created on Feb 24, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.assertj.swing.driver;

import org.junit.Test;

/**
 * Tests for {@link JComboBoxDriver#requireSelection(javax.swing.JComboBox, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxDriver_requireSelectionByIndex_Test extends JComboBoxDriver_TestCase {

  @Test
  public void should_pass_if_JComboBox_has_expected_selection() {
    selectFirstItem();
    driver.requireSelection(comboBox, 0);
  }

  @Test
  public void should_fail_if_JComboBox_does_not_have_expected_selection() {
    selectFirstItem();
    thrown.expectAssertionError("property:'selectedIndex'");
    thrown.expectMessageToContain("expected:<[1]> but was:<[0]>");
    driver.requireSelection(comboBox, 1);
  }

  @Test
  public void should_fail_if_JComboBox_does_not_have_any_selection() {
    clearSelection();
    thrown.expectAssertionError("property:'selectedIndex'");
    thrown.expectMessageToContain("No selection");
    driver.requireSelection(comboBox, 1);
  }
}
