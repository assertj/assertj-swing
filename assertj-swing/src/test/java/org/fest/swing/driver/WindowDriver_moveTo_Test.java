/*
 * Created on Apr 5, 2008
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
import static org.fest.swing.query.ComponentLocationOnScreenQuery.locationOnScreen;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import java.awt.Point;

import org.junit.Test;

/**
 * Tests for {@link WindowDriver#moveTo(java.awt.Window, java.awt.Point)}.
 * 
 * @author Alex Ruiz
 */
public class WindowDriver_moveTo_Test extends WindowDriver_TestCase {
  @Test
  public void should_move_Window() {
    showWindow();
    Point newPosition = new Point(200, 200);
    driver.moveTo(window, newPosition);
    assertThat(locationOnScreen(window)).isEqualTo(newPosition);
  }

  @Test
  public void should_throw_error_if_Window_is_disabled() {
    disableWindow();
    try {
      driver.moveTo(window, new Point(100, 100));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_Window_is_not_showing_on_the_screen() {
    try {
      driver.moveTo(window, new Point(100, 100));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }
}
