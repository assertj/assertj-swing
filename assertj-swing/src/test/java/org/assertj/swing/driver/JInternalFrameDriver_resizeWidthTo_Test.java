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
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.JInternalFrame;

import org.assertj.swing.annotation.RunsInEDT;
import org.junit.Test;

/**
 * Tests for {@link JInternalFrameDriver#resizeWidth(JInternalFrame, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JInternalFrameDriver_resizeWidthTo_Test extends JInternalFrameDriver_TestCase {
  @Test
  public void should_Resize_Width() {
    int newWidth = 600;
    showWindow();
    driver.resizeWidth(internalFrame, newWidth);
    assertThat(widthOf(internalFrame)).isEqualTo(newWidth);
  }

  @RunsInEDT
  private static int widthOf(final JInternalFrame internalFrame) {
    return execute(() -> internalFrame.getWidth());
  }
}
