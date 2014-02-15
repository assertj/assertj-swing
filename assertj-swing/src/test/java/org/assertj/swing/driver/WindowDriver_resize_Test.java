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

import java.awt.Dimension;

import org.assertj.swing.test.awt.FluentDimension;
import org.junit.Test;

/**
 * Tests for {@link WindowDriver#resize(java.awt.Container, int, int)}.
 * 
 * @author Alex Ruiz
 */
public class WindowDriver_resize_Test extends WindowDriver_TestCase {
  @Test
  public void should_resize_window() {
    showWindow();
    Dimension newSize = new FluentDimension(sizeOf(window)).addToHeight(100).addToWidth(200);
    driver.resize(window, newSize.width, newSize.height);
    assertThat(sizeOf(window)).isEqualTo(newSize);
  }

  @Test
  public void should_throw_error_if_Window_is_disabled() {
    disableWindow();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.resize(window, 10, 10);
  }

  @Test
  public void should_throw_error_if_Window_is_not_showing_on_the_screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.resize(window, 10, 10);
  }

  @Test(expected = IllegalStateException.class)
  public void should_throw_error_if_Window_is_not_resizable() {
    makeWindowNotResizable();
    showWindow();
    driver.resize(window, 10, 10);
  }
}
