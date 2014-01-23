/*
 * Created on Dec 4, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.core;

import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_H;
import static java.awt.event.KeyEvent.VK_L;
import static java.awt.event.KeyEvent.VK_O;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Lists.newArrayList;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=245">Bug 245</a>.
 *
 * @author Alex Ruiz
 */
public class Bug245_enterTextNotRaisingKeyReleaseEvent_Test extends RobotBasedTestCase {
  private JTextField textField;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    textField = window.textField;
    robot.showWindow(window);
  }

  @Test
  public void should_raise_keyReleased_event() {
    KeyReleaseListener keyReleaseListener = new KeyReleaseListener();
    textField.addKeyListener(keyReleaseListener);
    robot.focusAndWaitForFocusGain(textField);
    robot.enterText("Hello");
    assertThat(textField.getText()).isEqualTo("Hello");
    assertThat(keyReleaseListener.released()).containsOnly(VK_H, VK_SHIFT, VK_E, VK_L, VK_L, VK_O);
  }

  private static class KeyReleaseListener extends KeyAdapter {
    private final List<Integer> keyCodes = newArrayList();

    @Override
    public void keyReleased(KeyEvent e) {
      keyCodes.add(e.getKeyCode());
    }

    Integer[] released() {
      return keyCodes.toArray(new Integer[keyCodes.size()]);
    }
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JTextField textField = new JTextField(10);

    private MyWindow() {
      super(Bug245_enterTextNotRaisingKeyReleaseEvent_Test.class);
      addComponents(textField);
    }
  }
}
