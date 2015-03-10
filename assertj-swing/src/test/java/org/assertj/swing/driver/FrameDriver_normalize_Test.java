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

import static java.awt.Frame.MAXIMIZED_BOTH;
import static java.awt.Frame.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link FrameDriver#normalize(java.awt.Frame)}.
 * 
 * @author Alex Ruiz
 */
public class FrameDriver_normalize_Test extends FrameDriver_TestCase {
  @Test
  public void should_Normalize_Frame() {
    showWindow();
    window.setExtendedState(MAXIMIZED_BOTH);
    robot.waitForIdle();
    assertThat(frameState()).isEqualTo(MAXIMIZED_BOTH);
    driver.normalize(window);
    assertThat(frameState()).isEqualTo(NORMAL);
  }

  @Test
  public void should_Throw_Error_If_Frame_Is_Disabled() {
    disableWindow();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.normalize(window);
  }

  @Test
  public void should_Throw_Error_If_Frame_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.normalize(window);
  }
}
