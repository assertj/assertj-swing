/*
 * Created on Jul 26, 2009
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
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.swing.JButton;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link BasicRobot#focusAndWaitForFocusGain(Component)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_focusAndWaitForFocusGain_Test extends BasicRobot_TestCase {
  private JButton button;

  @RunsInEDT
  @Override
  void beforeShowingWindow() {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        button = new JButton("Click Me");
        window().add(button);
      }
    });
  }

  @Test
  public void should_give_focus_and_wait_until_Component_has_focus() {
    giveFocusAndVerifyThatHasFocus(button);
    giveFocusAndVerifyThatHasFocus(window().textField());
  }

  private void giveFocusAndVerifyThatHasFocus(@Nonnull Component c) {
    robot().focusAndWaitForFocusGain(c);
    assertThat(c.hasFocus()).isTrue();
  }
}
