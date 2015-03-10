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

import static org.assertj.swing.test.util.StopWatch.startNewStopWatch;

import java.util.concurrent.CountDownLatch;

import org.assertj.swing.test.util.StopWatch;
import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#focusAndWaitForFocusGain(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_focusAndWaitForFocusGain_Test extends ComponentDriver_TestCase {
  @Test
  public void should_Give_Focus_To_Component_And_Wait_Till_It_Is_Focused() {
    showWindow();
    assertThatButtonDoesNotHaveFocus();
    window.button.waitToRequestFocus();
    final CountDownLatch done = new CountDownLatch(1);
    StopWatch stopWatch = startNewStopWatch();
    new Thread() {
      @Override
      public void run() {
        driver.focusAndWaitForFocusGain(window.button);
        done.countDown();
      }
    }.start();
    try {
      done.await();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    stopWatch.stop();
    assertThatButtonHasFocus();
    assertThatWaited(stopWatch, TIME_TO_WAIT_FOR_FOCUS_GAIN);
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Disabled() {
    disableButton();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.focusAndWaitForFocusGain(window.button);
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.focusAndWaitForFocusGain(window.button);
  }
}
