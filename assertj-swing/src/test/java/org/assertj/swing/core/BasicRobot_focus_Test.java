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

import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.task.ComponentRequestFocusAndWaitForFocusGainTask.waitTillHasFocus;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.swing.JButton;

import org.assertj.swing.annotation.RunsInEDT;
import org.junit.Test;

/**
 * Tests for {@link BasicRobot#focus(java.awt.Component)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_focus_Test extends BasicRobot_TestCase {
  private JButton button;

  @RunsInEDT
  @Override
  void beforeShowingWindow() {
    execute(() -> {
      button = new JButton("Click Me");
      window().add(button);
    });
  }

  @Test
  public void should_Give_Focus() {
    giveFocusAndVerifyThatHasFocus(button);
    giveFocusAndVerifyThatHasFocus(window().textField());
  }

  private void giveFocusAndVerifyThatHasFocus(@Nonnull Component c) {
    robot().focus(c);
    waitTillHasFocus(c);
  }
}
