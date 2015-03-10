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
import static org.assertj.swing.driver.JInternalFrameIconQuery.isIconified;

import javax.swing.JInternalFrame;

import org.junit.Test;

/**
 * Tests for {@link JInternalFrameDriver#iconify(JInternalFrame)} and
 * {@link JInternalFrameDriver#deiconify(JInternalFrame)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JInternalFrameDriver_iconify_deiconify_Test extends JInternalFrameDriver_TestCase {
  @Test
  public void should_Iconify_And_Deiconify_JInternalFrame() {
    showWindow();
    driver.iconify(internalFrame);
    assertThat(isIconified(internalFrame)).isTrue();
    driver.deiconify(internalFrame);
    assertThat(isIconified(internalFrame)).isFalse();
  }

  @Test
  public void should_Iconify_And_Deiconify_Disabled_JInternalFrame() {
    disableInternalFrame();
    driver.iconify(internalFrame);
    assertThat(isIconified(internalFrame)).isTrue();
    driver.deiconify(internalFrame);
    assertThat(isIconified(internalFrame)).isFalse();
  }
}
