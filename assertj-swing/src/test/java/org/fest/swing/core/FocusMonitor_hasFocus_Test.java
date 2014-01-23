/*
 * Created on Apr 1, 2008
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.task.ComponentRequestFocusAndWaitForFocusGainTask.giveFocusAndWaitTillIsFocused;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link FocusMonitor#hasFocus()}.
 *
 * @author Alex Ruiz
 */
public class FocusMonitor_hasFocus_Test extends SequentialEDTSafeTestCase {
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    window.display();
  }

  @Override
  protected void onTearDown() {
    window.destroy();
  }

  @Test
  public void should_return_true_if_Component_gains_focus() {
    FocusMonitor monitor = FocusMonitor.attachTo(window.textBox);
    giveFocusAndWaitTillIsFocused(window.textBox);
    assertThat(monitor.hasFocus()).isTrue();
  }

  @Test
  public void should_return_false_if_Component_loses_focus() {
    FocusMonitor monitor = FocusMonitor.attachTo(window.button);
    giveFocusAndWaitTillIsFocused(window.textBox);
    assertThat(monitor.hasFocus()).isFalse();
  }

  @Test
  public void should_return_false_if_Component_is_not_focus_owner() {
    giveFocusAndWaitTillIsFocused(window.textBox);
    FocusMonitor monitor = FocusMonitor.attachTo(window.button);
    assertThat(monitor.hasFocus()).isFalse();
  }

  private static class MyWindow extends TestWindow {
    final JButton button = new JButton("Click Me");
    final JTextField textBox = new JTextField(20);

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(FocusMonitor_hasFocus_Test.class);
      addComponents(button, textBox);
    }
  }
}
