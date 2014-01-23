/*
 * Created on Jul 19, 2009
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

import static java.awt.event.KeyEvent.VK_A;

import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#pressKey(java.awt.Component, int)} and
 * {@link ComponentDriver#releaseKey(java.awt.Component, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_pressKey_releaseKey_Test extends ComponentDriver_TestCase {
  @Test
  public void should_press_key_and_release_key() {
    showWindow();
    assertThatTextFieldIsEmpty();
    int key = VK_A;
    driver.pressKey(window.textField, key);
    // test both pressKey and releaseKey
    driver.releaseKey(window.textField, key);
    assertThatTextInTextFieldIs("a");
  }
}
