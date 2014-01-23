/*
 * Created on Feb 25, 2008
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

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import javax.swing.JSlider;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JSliderDriver#slideToMaximum(javax.swing.JSlider)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JSliderDriver_slideToMaximum_Test extends JSliderDriver_TestCase {
  @Parameters
  public static Collection<Object[]> allOrientations() {
    return newArrayList(orientations());
  }

  public JSliderDriver_slideToMaximum_Test(int orientation) {
    super(orientation);
  }

  @Test
  public void should_slide_to_maximum() {
    showWindow();
    driver.slideToMaximum(slider);
    assertThatSliderValueIs(maximumOf(slider));
  }

  @RunsInEDT
  private static int maximumOf(final JSlider slider) {
    return execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return slider.getMaximum();
      }
    });
  }

  @Test
  public void should_throw_error_if_JSlider_is_disabled() {
    disableSlider();
    try {
      driver.slideToMaximum(slider);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JSlider_is_not_showing_on_the_screen() {
    try {
      driver.slideToMaximum(slider);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }
}
