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

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_B;
import static java.awt.event.KeyEvent.VK_Z;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.swing.timing.Timeout.timeout;
import static org.fest.util.Arrays.array;

import javax.swing.JTextField;

import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.recorder.KeyRecorder;
import org.fest.swing.timing.Condition;
import org.junit.Test;

/**
 * Tests for {@link BasicRobot#pressAndReleaseKeys(int...)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_pressAndReleaseKeys_Test extends BasicRobot_TestCase {
  @Test
  public void should_press_and_release_given_keys() {
    giveFocusToTextField();
    final JTextField textField = window().textField();
    final KeyRecorder recorder = KeyRecorder.attachTo(textField);
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        textField.setText("");
      }
    });
    robot().waitForIdle();
    robot().pressAndReleaseKeys(new int[] { VK_A, VK_B, VK_Z });
    pause(new Condition("until keys VK_A, VK_B and VK_Z are pressed and released") {
      @Override
      public boolean test() {
        Integer[] expectedKeys = array(VK_A, VK_B, VK_Z);
        return recorder.keysWerePressed(expectedKeys) && recorder.keysWereReleased(expectedKeys);
      }
    }, timeout(500));
  }
}
