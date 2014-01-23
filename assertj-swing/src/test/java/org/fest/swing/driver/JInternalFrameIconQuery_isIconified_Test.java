/*
 * Created on Aug 9, 2008
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
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestMdiWindow;
import org.junit.Test;

/**
 * Tests for {@link JInternalFrameIconQuery#isIconified(JInternalFrame)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JInternalFrameIconQuery_isIconified_Test extends RobotBasedTestCase {
  private JInternalFrame internalFrame;

  @Override
  protected void onSetUp() {
    TestMdiWindow window = TestMdiWindow.createNewWindow(getClass());
    internalFrame = window.internalFrame();
  }

  @Test
  public void should_return_true_if_JInternalFrame_is_iconified() {
    iconify();
    assertThat(isIconified(internalFrame)).isTrue();
  }

  @RunsInEDT
  private void iconify() {
    setIconAndMaximum(internalFrame, true, false);
    robot.waitForIdle();
  }

  @Test
  public void should_return_false_if_JInternalFrame_is_normalized() {
    normalize();
    assertThat(isIconified(internalFrame)).isFalse();
  }

  @RunsInEDT
  private void normalize() {
    setIconAndMaximum(internalFrame, false, false);
    robot.waitForIdle();
  }

  @Test
  public void should_return_false_if_JInternalFrame_is_maximized() {
    maximize();
    assertThat(isIconified(internalFrame)).isFalse();
  }

  @RunsInEDT
  private void maximize() {
    setIconAndMaximum(internalFrame, false, true);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static boolean isIconified(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return JInternalFrameIconQuery.isIconified(internalFrame);
      }
    });
  }

  @RunsInEDT
  private static void setIconAndMaximum(final JInternalFrame internalFrame, final boolean icon, final boolean maximum) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws PropertyVetoException {
        internalFrame.setIcon(icon);
        internalFrame.setMaximum(maximum);
      }
    });
  }
}
