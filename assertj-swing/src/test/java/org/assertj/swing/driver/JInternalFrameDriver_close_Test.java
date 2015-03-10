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
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link JInternalFrameDriver#close(javax.swing.JInternalFrame)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JInternalFrameDriver_close_Test extends JInternalFrameDriver_TestCase {
  @Test
  public void should_Close_JInternalFrame() {
    showWindow();
    driver.close(internalFrame);
    assertThat(isClosed(internalFrame)).isTrue();
  }

  @RunsInEDT
  private static boolean isClosed(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return internalFrame.isClosed();
      }
    });
  }

  @Test
  public void should_Throw_Error_If_JInternalFrame_Is_Not_Closable() {
    makeNotCloseable();
    showWindow();
    thrown.expect(IllegalStateException.class, "The JInternalFrame <");
    thrown.expectMessageToContain("> is not closable");
    driver.close(internalFrame);
  }

  @RunsInEDT
  private void makeNotCloseable() {
    setClosable(internalFrame, false);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setClosable(final JInternalFrame internalFrame, final boolean closeable) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        internalFrame.setClosable(closeable);
      }
    });
  }
}
