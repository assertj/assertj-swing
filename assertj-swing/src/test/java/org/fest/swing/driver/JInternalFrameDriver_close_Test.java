/*
 * Created on Feb 24, 2008
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
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import javax.swing.JInternalFrame;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link JInternalFrameDriver#close(javax.swing.JInternalFrame)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JInternalFrameDriver_close_Test extends JInternalFrameDriver_TestCase {
  @Test
  public void should_close_JInternalFrame() {
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
  public void should_throw_error_if_JInternalFrame_is_not_closable() {
    makeNotCloseable();
    showWindow();
    try {
      driver.close(internalFrame);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("The JInternalFrame <").contains("> is not closable");
    }
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
