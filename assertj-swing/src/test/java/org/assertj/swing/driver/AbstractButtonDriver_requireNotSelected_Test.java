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
 * Tests for {@link AbstractButtonDriver#requireNotSelected(javax.swing.AbstractButton)}.
 * 
 * @author Alex Ruiz
 */
public class AbstractButtonDriver_requireNotSelected_Test extends AbstractButtonDriver_TestCase {
  @Test
  public void should_Pass_If_Button_Is_Not_Selected() {
    unselectCheckBox();
    driver.requireNotSelected(checkBox);
  }

  @Test
  public void should_Fail_If_Button_Is_Selected() {
    selectCheckBox();
    thrown.expectAssertionError("property:'selected'");
    thrown.expectMessageToContain("expected:<[fals]e> but was:<[tru]e>");
    driver.requireNotSelected(checkBox);
  }
}
