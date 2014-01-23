/*
 * Created on Jul 17, 2009
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
 * Tests for {@link JTextComponentDriver#requireEditable(javax.swing.text.JTextComponent)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTextComponentDriver_requireEditable_Test extends JTextComponentDriver_TestCase {
  @Test
  public void should_pass_if_JTextComponent_is_editable() {
    makeTextFieldEditable();
    driver.requireEditable(textField);
  }

  @Test
  public void should_fail_if_JTextComponent_is_not_editable() {
    makeTextFieldNotEditable();
    try {
      driver.requireEditable(textField);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'editable'").contains("expected:<[tru]e> but was:<[fals]e>");
    }
  }
}
