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

import static java.awt.event.KeyEvent.VK_A;
import static org.assertj.swing.timing.Pause.pause;
import static org.assertj.swing.timing.Timeout.timeout;

import org.assertj.swing.test.recorder.KeyRecorder;
import org.assertj.swing.timing.Condition;
import org.junit.Test;

/**
 * Tests for {@link BasicRobot#pressKey(int)} and {@link BasicRobot#releaseKey(int)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_pressKeyAndReleaseKey_Test extends BasicRobot_TestCase {
  @Test
  public void should_Press_Given_Key() {
    giveFocusToTextField();
    final KeyRecorder recorder = KeyRecorder.attachTo(window().textField());
    robot().pressKey(VK_A);
    pause(new Condition("until key VK_A is pressed") {
      @Override
      public boolean test() {
        return recorder.keysWerePressed(VK_A) && recorder.noKeysReleased();
      }
    }, timeout(500));
  }

  @Test
  public void should_Release_Given_Key() {
    giveFocusToTextField();
    final KeyRecorder recorder = KeyRecorder.attachTo(window().textField());
    robot().pressKey(VK_A);
    robot().releaseKey(VK_A);
    pause(new Condition("until key is released") {
      @Override
      public boolean test() {
        return recorder.keysWereReleased(VK_A);
      }
    }, timeout(500));
  }

}
