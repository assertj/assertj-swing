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

import static org.assertj.core.util.Lists.newArrayList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JSliderDriver#slide(javax.swing.JSlider, int)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JSliderDriver_slide_withInvalidInputAndState_Test extends JSliderDriver_TestCase {
  @Parameters
  public static Collection<Object[]> allOrientations() {
    return newArrayList(orientations());
  }

  public JSliderDriver_slide_withInvalidInputAndState_Test(int orientation) {
    super(orientation);
  }

  @Test
  public void should_Throw_Error_If_Value_Is_Less_Than_Minimum() {
    showWindow();
    thrown.expectIllegalArgumentException("Value <-1> is not within the JSlider bounds of <0> and <30>");
    driver.slide(slider, -1);
  }

  @Test
  public void should_Throw_Error_If_Value_Is_Greater_Than_Maximum() {
    showWindow();
    thrown.expectIllegalArgumentException("Value <31> is not within the JSlider bounds of <0> and <30>");
    driver.slide(slider, 31);
  }

  @Test
  public void should_Throw_Error_If_JSlider_Is_Disabled() {
    disableSlider();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.slide(slider, 6);
  }

  @Test
  public void should_Throw_Error_If_JSlider_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.slide(slider, 6);
  }
}
