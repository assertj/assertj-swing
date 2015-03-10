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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.query.ComponentHasFocusQuery.hasFocus;
import static org.assertj.swing.test.swing.ComponentHasFocusCondition.untilFocused;
import static org.assertj.swing.timing.Pause.pause;

import javax.swing.JButton;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.SequentialEDTSafeTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link ComponentRequestFocusTask#giveFocusTo(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentRequestFocusTask_giveFocusTo_Test extends SequentialEDTSafeTestCase {
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
  public void should_Call_RequestFocusInWindow() {
    final JButton toReceiveFocus = window.buttonTwo;
    assertThat(hasFocus(toReceiveFocus)).isFalse();
    ComponentRequestFocusTask.giveFocusTo(toReceiveFocus);
    pause(untilFocused(toReceiveFocus));
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

    final JButton buttonOne = new JButton("One");
    final JButton buttonTwo = new JButton("Two");

    private MyWindow() {
      super(ComponentRequestFocusTask_giveFocusTo_Test.class);
      addComponents(buttonOne, buttonTwo);
    }
  }
}
