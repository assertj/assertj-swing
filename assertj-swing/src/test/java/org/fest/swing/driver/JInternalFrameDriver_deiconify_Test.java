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
import static org.fest.swing.driver.JInternalFrameAction.DEICONIFY;
import static org.fest.swing.driver.JInternalFrameIconQuery.isIconified;
import static org.fest.swing.driver.JInternalFrameSetIconTask.setIcon;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import javax.swing.JInternalFrame;

import org.fest.swing.annotation.RunsInEDT;
import org.junit.Test;

/**
 * Tests for {@link JInternalFrameDriver#deiconify(JInternalFrame)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JInternalFrameDriver_deiconify_Test extends JInternalFrameDriver_TestCase {
  @Test
  public void should_not_deiconify_already_iconified_JInternalFrame() {
    showWindow();
    deiconify();
    driver.deiconify(internalFrame);
    assertThat(isIconified(internalFrame)).isFalse();
  }

  @RunsInEDT
  private void deiconify() {
    setIcon(internalFrame, DEICONIFY);
    robot.waitForIdle();
  }

  @Test
  public void should_throw_error_if_JInternalFrame_is_not_showing_on_the_screen() {
    try {
      driver.deiconify(internalFrame);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }
}
