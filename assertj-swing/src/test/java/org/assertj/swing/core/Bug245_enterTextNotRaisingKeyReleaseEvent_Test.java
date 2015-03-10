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

import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_H;
import static java.awt.event.KeyEvent.VK_L;
import static java.awt.event.KeyEvent.VK_O;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JTextField;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
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
  public void should_Raise_KeyReleased_Event() {
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
