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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.junit.Test;

/**
 * Tests for {@link AbstractButtonDriver#requireSelected(javax.swing.AbstractButton)}.
 *
 * @author Alex Ruiz
 */
public class AbstractButtonDriver_requireSelected_Test extends AbstractButtonDriver_TestCase {
  @Test
  public void should_pass_if_button_is_selected() {
    selectCheckBox();
    driver.requireSelected(checkBox);
  }

  @Test
  public void should_fail_if_button_is_not_selected() {
    unselectCheckBox();
    try {
      driver.requireSelected(checkBox);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selected'")
                                .contains("expected:<[tru]e> but was:<[fals]e>");
    }
  }
}
