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
package org.assertj.swing.monitor;

import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.lock.ScreenLock;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for {@link WindowAvailabilityMonitor}.
 * 
 * @author Alex Ruiz
 */
public class WindowAvailabilityMonitor_eventDispatched_Test extends WindowAvailabilityMonitor_TestCase {
  private MyWindow window;

  @BeforeClass
  public static void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @Override
  void onSetUp() {
    ScreenLock.instance().acquire(this);
    window = MyWindow.createNew(getClass());
  }

  @After
  public void tearDown() {
    try {
      window.destroy();
    } finally {
      ScreenLock.instance().release(this);
    }
  }

  @Test
  public void should_Mark_Source_Window_As_Ready_If_Event_Is_MouseEvent() {
    monitor.eventDispatched(mouseEvent(window));
    verify(windows).markAsReady(window);
  }

  @Test
  public void should_Mark_Source_Window_Ancestor_As_Ready_If_Event_Is_MouseEvent() {
    JTextField source = window.textField;
    monitor.eventDispatched(mouseEvent(source));
    verify(windows).markAsReady(window);
  }

  @Test
  public void should_Not_Mark_Source_Window_As_Ready_If_Event_Is_Not_MouseEvent() {
    monitor.eventDispatched(new KeyEvent(window, 8, 9238, 0, 0, 'a'));
    verifyZeroInteractions(windows);
  }

  private MouseEvent mouseEvent(Component source) {
    return new MouseEvent(source, 8, 8912, 0, 0, 0, 0, false, 0);
  }

  static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    final JTextField textField = new JTextField("Hello");

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(textField);
    }
  }
}
