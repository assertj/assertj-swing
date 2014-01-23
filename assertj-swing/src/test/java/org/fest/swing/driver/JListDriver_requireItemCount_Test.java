/*
 * Created on Nov 20, 2009
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
 * Tests for {@link JListDriver#requireItemCount(javax.swing.JList, int)}.
 *
 * @author Alex Ruiz
 */
public class JListDriver_requireItemCount_Test extends JListDriver_TestCase {
  @Test
  public void should_fail_if_JList_does_not_have_expected_item_count() {
    try {
      driver.requireItemCount(list, 6);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'itemCount'")
                                .contains("expected:<[6]> but was:<[3]>");
    }
  }

  @Test
  public void should_pass_if_JList_has_expected_item_count() {
    driver.requireItemCount(list, 3);
  }
}
