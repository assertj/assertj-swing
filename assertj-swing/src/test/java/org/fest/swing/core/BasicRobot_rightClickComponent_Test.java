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
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.test.recorder.ClickRecorder.attachTo;

import javax.swing.JTextField;

import org.fest.swing.test.recorder.ClickRecorder;
import org.junit.Test;

/**
 * Tests for {@link BasicRobot#rightClick(java.awt.Component)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_rightClickComponent_Test extends BasicRobot_TestCase {
  @Test
  public void should_rightClick_Component() {
    JTextField textField = window().textField();
    ClickRecorder recorder = attachTo(textField);
    robot().rightClick(textField);
    assertThat(recorder).clicked(RIGHT_BUTTON).timesClicked(1);
  }
}
