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

import static java.awt.event.KeyEvent.CHAR_UNDEFINED;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_S;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.task.ComponentRequestFocusAndWaitForFocusGainTask.giveFocusAndWaitTillIsFocused;
import static org.assertj.swing.timing.Pause.pause;

import java.util.Collection;

import javax.swing.text.JTextComponent;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Base test case for implementations of {@link InputEventGenerator#pressKey(int, char)} and
 * {@link InputEventGenerator#releaseKey(int)}.
 * 
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public abstract class InputEventGenerator_pressKey_TestCase extends InputEventGenerator_TestCase {
  private final int keyToPress;
  private final String expectedText;

  @Parameters
  public static Collection<Object[]> keys() {
    return newArrayList(new Object[][] { { VK_A, "a" }, { VK_S, "s" }, { VK_D, "d" } });
  }

  public InputEventGenerator_pressKey_TestCase(int keyToPress, String expectedText) {
    this.keyToPress = keyToPress;
    this.expectedText = expectedText;

  }

  @Test
  public void should_Type_Key() {
    giveFocusAndWaitTillIsFocused(window.textBox);
    eventGenerator.pressKey(keyToPress, CHAR_UNDEFINED);
    eventGenerator.releaseKey(keyToPress);
    pause(DELAY);
    String text = textOf(window.textBox);
    assertThat(text).isEqualTo(expectedText);
  }

  @RunsInEDT
  private static String textOf(final JTextComponent textComponent) {
    return execute(new GuiQuery<String>() {
      @Override
      protected String executeInEDT() {
        return textComponent.getText();
      }
    });
  }
}
