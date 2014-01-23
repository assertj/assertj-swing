/*
 * Created on Sep 5, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentLocationOnScreenQuery.locationOnScreen;
import static org.fest.swing.query.ComponentShowingQuery.isShowing;
import static org.fest.swing.test.task.ComponentRequestFocusAndWaitForFocusGainTask.giveFocusAndWaitTillIsFocused;
import static org.fest.swing.test.task.ComponentSetPopupMenuTask.createAndSetPopupMenu;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Point;

import javax.annotation.Nonnull;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.After;
import org.junit.Before;

/**
 * Base case for tests for {@link BasicRobot}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class BasicRobot_TestCase extends EDTSafeTestCase {
  private BasicRobot robot;
  private MyWindow window;

  @Before
  public final void setUp() {
    robot = (BasicRobot) BasicRobot.robotWithCurrentAwtHierarchy();
    MyWindow w = MyWindow.createAndShow(checkNotNull(getClass()));
    beforeShowingWindow();
    robot.showWindow(w); // implicitly test 'showWindow(Window)'
    assertThat(isShowing(w)).isTrue();
    assertThat(locationOnScreen(w)).isEqualTo(new Point(100, 100));
    window = w;
  }

  void beforeShowingWindow() {}

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
    giveFocusAndWaitTillIsFocused(window().textField());
  }

  @RunsInEDT
  final JPopupMenu addPopupMenuToTextField() {
    return createAndSetPopupMenu(window().textField(), "Luke", "Leia");
  }

  static class MyWindow extends TestWindow {
    private final JTextField textField = new JTextField(10);

    @RunsInEDT
    static @Nonnull MyWindow createAndShow(final @Nonnull Class<?> testClass) {
      MyWindow result = execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return display(new MyWindow(testClass));
        }
      });
      return checkNotNull(result);
    }

    private MyWindow(@Nonnull Class<?> testClass) {
      super(testClass);
      addComponents(textField);
    }

    @Nonnull JTextField textField() {
      return checkNotNull(textField);
    }
  }

  @Nonnull BasicRobot robot() {
    return checkNotNull(robot);
  }

  @Nonnull MyWindow window() {
    return checkNotNull(window);
  }
}
