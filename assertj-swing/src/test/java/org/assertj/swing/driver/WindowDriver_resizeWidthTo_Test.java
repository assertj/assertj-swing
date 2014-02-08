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
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.query.ComponentSizeQuery.sizeOf;
import static org.assertj.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.assertj.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.assertj.swing.test.core.CommonAssertions.failWhenExpectingException;

import java.awt.Dimension;

import org.assertj.swing.test.awt.FluentDimension;
import org.junit.Test;

/**
 * Tests for {@link WindowDriver#resizeWidthTo(java.awt.Window, int)}.
 * 
 * @author Alex Ruiz
 */
public class WindowDriver_resizeWidthTo_Test extends WindowDriver_TestCase {
  @Test
  public void should_resize_width() {
    showWindow();
    Dimension newSize = new FluentDimension(sizeOf(window)).addToWidth(200);
    driver.resizeWidthTo(window, newSize.width);
    assertThat(sizeOf(window)).isEqualTo(newSize);
  }

  @Test
  public void should_throw_error_if_Window_is_disabled() {
    disableWindow();
    try {
      driver.resizeWidthTo(window, 10);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_Window_is_not_showing_on_the_screen() {
    try {
      driver.resizeWidthTo(window, 10);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  @Test(expected = IllegalStateException.class)
  public void should_throw_error_if_Window_is_not_resizable() {
    makeWindowNotResizable();
    showWindow();
    driver.resizeWidthTo(window, 10);
  }
}
