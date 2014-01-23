/*
 * Created on Feb 26, 2008
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.AbstractButtonTextQuery.textOf;
import static org.fest.util.Strings.isNullOrEmpty;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import org.junit.Test;

/**
 * Tests for {@link JFileChooserDriver#approveButton(javax.swing.JFileChooser)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JFileChooserDriver_approveButton_Test extends JFileChooserDriver_TestCase {
  @Test
  public void should_find_Approve_button() {
    showWindow();
    JButton approveButton = driver.approveButton(fileChooser);
    assertThat(approveButton).isNotNull();
    assertThat(textOf(approveButton)).isEqualTo(approveButtonText(fileChooser));
  }

  private static String approveButtonText(final JFileChooser fileChooser) {
    String text = fileChooser.getApproveButtonText();
    if (!isNullOrEmpty(text)) {
      return text;
    }
    return fileChooser.getUI().getApproveButtonText(fileChooser);
  }
}
