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
import static org.assertj.swing.query.ComponentLocationOnScreenQuery.locationOnScreen;
import static org.assertj.swing.query.ComponentShowingQuery.isShowing;
import static org.assertj.swing.test.task.ComponentRequestFocusAndWaitForFocusGainTask.giveFocusAndWaitTillIsFocused;
import static org.assertj.swing.test.task.ComponentSetPopupMenuTask.createAndSetPopupMenu;

import java.awt.Point;

import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.EDTSafeTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.After;
import org.junit.Before;

public class BasicRobot_WithoutScreenLock_TestCase extends EDTSafeTestCase {
  BasicRobot robot;
  MyWindow window;

  @Before
  public final void setUp() {
    robot = (BasicRobot) BasicRobot.robotWithNewAwtHierarchyWithoutScreenLock();
    window = MyWindow.createAndShow(getClass());

    beforeShowingWindow();

    robot.showWindow(window);

    assertThat(isShowing(window)).isTrue();
    assertThat(locationOnScreen(window)).isEqualTo(new Point(100, 100));

  }

  void beforeShowingWindow() {
  }

  @After
  public final void tearDown() {
    try {
      window.destroy();
    } finally {
      robot.cleanUp();
    }
  }

  @RunsInEDT
  final void giveFocusToTextField() {
    giveFocusAndWaitTillIsFocused(window.textField);
  }

  @RunsInEDT
  final JPopupMenu addPopupMenuToTextField() {
    return createAndSetPopupMenu(window.textField, "Luke", "Leia");
  }

  static class MyWindow extends TestWindow {
    final JTextField textField = new JTextField(10);

    @RunsInEDT
    static MyWindow createAndShow(final Class<?> testClass) {
      return execute(() -> display(new MyWindow(testClass)));
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(textField);
    }
  }
}
