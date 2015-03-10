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

import org.junit.Test;

/**
 * Tests for {@link JScrollBarDriver#scrollBlockUp(javax.swing.JScrollBar)}.
 * 
 * @author Alex Ruiz
 */
public class JScrollBarDriver_scrollBlockUp_Test extends JScrollBarDriver_TestCase {
  @Test
  public void should_Scroll_Block_Up() {
    showWindow();
    driver.scrollBlockUp(scrollBar);
    assertThatScrollBarValueIs(20);
  }

  @Test
  public void should_Throw_Error_If_JScrollBar_Is_Disabled() {
    disableScrollBar();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.scrollBlockUp(scrollBar);
  }

  @Test
  public void should_Throw_Error_If_JScrollBar_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.scrollBlockUp(scrollBar);
  }
}
