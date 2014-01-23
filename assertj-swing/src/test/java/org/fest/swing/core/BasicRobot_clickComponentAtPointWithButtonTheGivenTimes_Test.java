/*
 * Created on Jul 25, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.awt.AWT.locationOnScreenOf;
import static org.fest.swing.awt.AWT.visibleCenterOf;
import static org.fest.swing.core.ClickingDataProvider.clickingData;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Point;
import java.util.Collection;

import javax.swing.JTextField;

import org.fest.swing.test.recorder.ClickRecorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link BasicRobot#click(java.awt.Component, java.awt.Point, MouseButton, int)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class BasicRobot_clickComponentAtPointWithButtonTheGivenTimes_Test extends BasicRobot_TestCase {
  private final MouseButton button;
  private final int times;

  @Parameters
  public static Collection<Object[]> buttons() {
    return newArrayList(clickingData());
  }

  public BasicRobot_clickComponentAtPointWithButtonTheGivenTimes_Test(MouseButton button, int times) {
    this.button = button;
    this.times = times;
  }

  @Test
  public void should_click_at_given_point_with_given_mouse_button_and_given_number_of_times() {
    JTextField textField = window().textField();
    ClickRecorder recorder = ClickRecorder.attachTo(textField);
    Point screenLocation = checkNotNull(locationOnScreenOf(textField));
    Point visibleCenter = visibleCenterOf(textField);
    screenLocation.translate(visibleCenter.x, visibleCenter.y);
    robot().click(screenLocation, button, times);
    assertThat(recorder).clicked(button).timesClicked(times).clickedAt(visibleCenter);
  }
}
