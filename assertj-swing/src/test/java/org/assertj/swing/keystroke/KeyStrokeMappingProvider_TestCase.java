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
package org.assertj.swing.keystroke;

import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_DELETE;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.core.util.Strings.quote;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.timing.Pause.pause;
import static org.assertj.swing.util.Maps.newHashMap;

import java.awt.Dimension;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.driver.JTextComponentDriver;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.recorder.KeyRecorder;
import org.assertj.swing.test.swing.TestWindow;
import org.assertj.swing.timing.Condition;
import org.junit.Test;

/**
 * Test case for implementations of {@link KeyStrokeMappingProvider}.
 *
 * @author Alex Ruiz
 */
public abstract class KeyStrokeMappingProvider_TestCase extends RobotBasedTestCase {
  private static final Map<Character, Integer> BASIC_CHARS_AND_KEYS_MAP = newHashMap();

  static {
    BASIC_CHARS_AND_KEYS_MAP.put((char) 8, VK_BACK_SPACE);
    BASIC_CHARS_AND_KEYS_MAP.put((char) 10, VK_ENTER);
    BASIC_CHARS_AND_KEYS_MAP.put((char) 127, VK_DELETE);
    BASIC_CHARS_AND_KEYS_MAP.put((char) 27, VK_ESCAPE);
    BASIC_CHARS_AND_KEYS_MAP.put((char) 13, VK_ENTER);
  }

  private JTextComponent textArea;
  private JTextComponentDriver driver;

  final char character;
  final KeyStroke keyStroke;

  public KeyStrokeMappingProvider_TestCase(char character, KeyStroke keyStroke) {
    this.character = character;
    this.keyStroke = keyStroke;
  }

  @Override
  protected final void onSetUp() {
    MyWindow window = MyWindow.createNew(getClass());
    textArea = window.textArea;
    robot.showWindow(window);
    driver = new JTextComponentDriver(robot);
  }

  @Test
  public void should_Provide_Key_Strokes_For_Keyboard() {
    if (basicCharacterVerified()) {
      return;
    }
    pressKeyStrokeAndVerify(character);
  }

  private void pressKeyStrokeAndVerify(char expectedChar) {
    pressInTextArea();
    final String expectedText = valueOf(expectedChar);
    pause(new Condition(concat("text in JTextArea to be ", quote(expectedText))) {
      @Override
      public boolean test() {
        return expectedText.equals(textArea.getText());
      }
    }, 500);
  }

  private boolean basicCharacterVerified() {
    if (!BASIC_CHARS_AND_KEYS_MAP.containsKey(character)) {
      return false;
    }
    int key = BASIC_CHARS_AND_KEYS_MAP.get(character);
    pressKeyStrokeAndVerify(key);
    return true;
  }

  private void pressKeyStrokeAndVerify(final int expectedKey) {
    assertThat(keyStroke.getModifiers()).as("no modifiers should be specified").isEqualTo(0);
    final KeyRecorder recorder = KeyRecorder.attachTo(textArea);
    pressInTextArea();
    pause(new Condition(concat("key with code ", expectedKey, " is pressed")) {
      @Override
      public boolean test() {
        return recorder.keysWerePressed(expectedKey);
      }
    }, 2000);
  }

  private void pressInTextArea() {
    driver.pressAndReleaseKey(textArea, keyStroke.getKeyCode(), new int[] { keyStroke.getModifiers() });
  }

  static Collection<Object[]> keyStrokesFrom(Collection<KeyStrokeMapping> mappings) {
    List<Object[]> keyStrokes = newArrayList();
    for (KeyStrokeMapping mapping : mappings) {
      keyStrokes.add(new Object[] { mapping.character(), mapping.keyStroke() });
    }
    return keyStrokes;
  }

  static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
    }

    final JTextArea textArea = new JTextArea(3, 8);

    private MyWindow(Class<?> testClass) {
      super(testClass);
      add(textArea);
      setPreferredSize(new Dimension(200, 200));
    }
  }
}
