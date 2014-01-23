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
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.test.recorder.ClickRecorder.attachTo;

import java.awt.Point;

import org.fest.swing.test.recorder.ClickRecorder;
import org.junit.Test;

/**
 * Tests for {@link BasicRobot#click(java.awt.Component, java.awt.Point)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_clickComponentAtPoint_Test extends BasicRobot_TestCase {
  @Test
  public void should_click_Component_once_with_left_button_at_given_point() {
    Point p = new Point(10, 10);
    ClickRecorder recorder = attachTo(window().textField());
    robot().click(window().textField(), p);
    assertThat(recorder).clicked(LEFT_BUTTON).timesClicked(1).clickedAt(p);
  }
}
