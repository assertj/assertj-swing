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
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import java.awt.Point;

import org.junit.Test;

/**
 * Tests for {@link WindowDriver#moveTo(java.awt.Window, java.awt.Point)} to ensure it works with {@link FrameDriver}.
 * 
 * @author Alex Ruiz
 */
public class FrameDriver_moveTo_Test extends FrameDriver_TestCase {
  @Test
  public void should_move_Frame() {
    showWindow();
    Point newLocation = windowLocationOnScreen().addToX(10).addToY(10);
    driver.moveTo(window, newLocation);
    assertThat(windowLocationOnScreen()).isEqualTo(newLocation);
  }

  @Test
  public void should_throw_error_if_Frame_is_disabled() {
    disableWindow();
    try {
      driver.moveTo(window, new Point(10, 10));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_Frame_is_not_showing_on_the_screen() {
    try {
      driver.moveTo(window, new Point(10, 10));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }
}
