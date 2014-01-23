/*
 * Created on Aug 8, 2008
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
import static org.fest.swing.driver.JInternalFrameAction.MAXIMIZE;
import static org.fest.swing.driver.JInternalFrameAction.NORMALIZE;
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JInternalFrame;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestMdiWindow;
import org.junit.Test;

/**
 * Tests for {@link JInternalFrameSetMaximumTask#setMaximum(JInternalFrame, JInternalFrameAction)}.
 * 
 * @author Yvonne Wang
 */
public class JInternalFrameSetMaximumTask_setMaximum_Test extends RobotBasedTestCase {
  private JInternalFrame internalFrame;

  @Override
  protected void onSetUp() {
    TestMdiWindow window = TestMdiWindow.createNewWindow(getClass());
    internalFrame = window.internalFrame();
  }

  @Test
  public void should_maximize_and_normalize_JInternalFrame() {
    assertThat(isMaximum(internalFrame)).isFalse();
    maximize();
    assertThat(isMaximum(internalFrame)).isTrue();
    normalize();
    assertThat(isMaximum(internalFrame)).isFalse();
  }

  private void maximize() {
    JInternalFrameSetMaximumTask.setMaximum(internalFrame, MAXIMIZE);
    robot.waitForIdle();
  }

  private void normalize() {
    JInternalFrameSetMaximumTask.setMaximum(internalFrame, NORMALIZE);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static boolean isMaximum(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return internalFrame.isMaximum() && !internalFrame.isIcon();
      }
    });
  }
}
