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
import static org.assertj.swing.driver.JInternalFrameAction.MAXIMIZE;
import static org.assertj.swing.driver.JInternalFrameIconQuery.isIconified;
import static org.assertj.swing.driver.JInternalFrameSetMaximumTask.setMaximum;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.exception.UnexpectedException;
import org.junit.Test;

/**
 * Tests for
 * {@link JInternalFrameDriver#failIfVetoed(javax.swing.JInternalFrame, JInternalFrameAction, UnexpectedException)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JInternalFrameDriver_normalize_Test extends JInternalFrameDriver_TestCase {
  @Test
  public void should_Normalize_JInternalFrame() {
    showWindow();
    maximize();
    driver.normalize(internalFrame);
    assertThatIsNormalized();
  }

  @RunsInEDT
  private void maximize() {
    setMaximum(internalFrame, MAXIMIZE);
    robot.waitForIdle();
  }

  @Test
  public void should_Normalize_Iconified_JInternalFrame() {
    showWindow();
    iconify();
    driver.normalize(internalFrame);
    assertThatIsNormalized();
  }

  @RunsInEDT
  private void assertThatIsNormalized() {
    boolean normalized = execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return !isIconified(internalFrame) && !internalFrame.isMaximum();
      }
    });
    assertThat(normalized).isTrue();
  }
}
