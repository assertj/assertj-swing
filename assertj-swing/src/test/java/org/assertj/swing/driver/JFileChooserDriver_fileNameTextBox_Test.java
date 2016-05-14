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

import javax.swing.JTextField;

import org.junit.Test;

/**
 * Tests for {@link JFileChooserDriver#fileNameTextBox(JFileChooser)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JFileChooserDriver_fileNameTextBox_Test extends JFileChooserDriver_TestCase {
  @Test
  public void should_Find_FileName_TextBox() {
    // TODO(Alex): Test in Windows
    showWindow();
    JTextField fileNameTextBox = driver.fileNameTextBox(fileChooser);
    assertThat(fileNameTextBox).isNotNull();
  }
}
