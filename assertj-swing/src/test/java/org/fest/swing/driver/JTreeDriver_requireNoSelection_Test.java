/*
 * Created on Jul 16, 2009
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
 * Tests for {@link JTreeDriver#requireNoSelection(javax.swing.JTree)}.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_requireNoSelection_Test extends JTreeDriver_selectCell_TestCase {
  @Test
  public void should_pass_if_JTree_does_not_have_selection() {
    clearTreeSelection();
    driver.requireNoSelection(tree);
  }

  @Test
  public void should_fail_if_JTree_has_selection() {
    selectFirstChildOfRoot();
    try {
      driver.requireNoSelection(tree);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selection'").contains(
          "expected no selection but was:<[[root, branch1]]>");
    }
  }
}
