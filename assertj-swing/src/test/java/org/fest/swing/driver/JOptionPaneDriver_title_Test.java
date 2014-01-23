/*
 * Created on Mar 24, 2010
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
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.swing.JOptionPaneLauncher.pack;

import javax.swing.JOptionPane;

import org.junit.Test;

/**
 * Tests for {@link JOptionPaneDriver#title(JOptionPane)}.
 * 
 * @author Alex Ruiz
 */
public class JOptionPaneDriver_title_Test extends JOptionPaneDriver_TestCase {
  @Test
  public void should_return_title() {
    JOptionPane optionPane = informationMessage();
    pack(optionPane, title());
    assertThat(driver.title(optionPane)).isEqualTo(title());
  }
}
