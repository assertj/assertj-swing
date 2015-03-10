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
package org.assertj.swing.core;

import static org.assertj.swing.core.MouseButton.LEFT_BUTTON;

import java.awt.Point;

import org.assertj.swing.test.recorder.ClickRecorder;
import org.junit.Test;

/**
 * Tests for {@link BasicRobot#click(java.awt.Component, java.awt.Point)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_clickComponentAtPoint_Test extends BasicRobot_ClickTestCase {
  @Test
  public void should_Click_Component_Once_With_Left_Button_At_Given_Point() {
    Point p = new Point(10, 10);
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window().textField());
    robot().click(window().textField(), p);
    recorder.clicked(LEFT_BUTTON).timesClicked(1).clickedAt(p);
  }
}
