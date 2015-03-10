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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.test.swing.JOptionPaneLauncher.pack;

import javax.swing.JOptionPane;

import org.junit.Test;

/**
 * Tests for {@link JOptionPaneDriver#title(JOptionPane)}.
 * 
 * @author Alex Ruiz
 */
public class JOptionPaneDriver_title_Test extends JOptionPaneDriver_TestCase {
  @Test
  public void should_Return_Title() {
    JOptionPane optionPane = informationMessage();
    pack(optionPane, title());
    assertThat(driver.title(optionPane)).isEqualTo(title());
  }
}
