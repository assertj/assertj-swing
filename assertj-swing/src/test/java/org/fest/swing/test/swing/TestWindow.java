/*
 * Created on Sep 11, 2007
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
package org.fest.swing.test.swing;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.task.FrameShowTask.packAndShow;
import static org.fest.swing.test.task.FrameShowTask.waitForShowing;
import static org.fest.swing.test.task.WindowDestroyTask.hideAndDispose;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.annotation.Nonnull;
import javax.swing.JFrame;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;

/**
 * The base {@code Window} for all GUI tests.
 *
 * @author Alex Ruiz
 */
public class TestWindow extends JFrame {
  static final Point DEFAULT_WINDOW_LOCATION = new Point(100, 100);

  /**
   * Creates a new {@link TestWindow} and displays it on the screen. This method is executed in the event dispatch
   * thread (EDT.)
   *
   * @param testClass the class of the test where the window to create will be used. The simple name of the given class
   *          will be used as the title of the created window.
   * @return the created window.
   */
  @RunsInEDT
  public static @Nonnull TestWindow createAndShowNewWindow(final @Nonnull Class<?> testClass) {
    TestWindow result = execute(new GuiQuery<TestWindow>() {
      @Override
      protected TestWindow executeInEDT() {
        TestWindow window = createInCurrentThread(testClass);
        TestWindow.display(window);
        return window;
      }
    });
    result = checkNotNull(result);
    waitForShowing(result);
    return result;
  }

  /**
   * Creates a new {@link TestWindow}. This method is executed in the event dispatch thread (EDT.)
   *
   * @param testClass the class of the test where the window to create will be used. The simple name of the given class
   *          will be used as the title of the created window.
   * @return the created window.
   */
  @RunsInEDT
  public static @Nonnull TestWindow createNewWindow(final @Nonnull Class<?> testClass) {
    TestWindow result = execute(new GuiQuery<TestWindow>() {
      @Override
      protected TestWindow executeInEDT() {
        return createInCurrentThread(testClass);
      }
    });
    return checkNotNull(result);
  }

  private static @Nonnull
  TestWindow createInCurrentThread(@Nonnull Class<?> testClass) {
    return new TestWindow(testClass);
  }

  /**
   * <p>
   * Creates a new {@link TestWindow}.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param testClass the class of the test where the window to create will be used. The simple name of the given class
   *          will be used as the title of the created window.
   */
  @RunsInCurrentThread
  protected TestWindow(@Nonnull Class<?> testClass) {
    setTitle(testClass.getSimpleName());
    setLayout(new FlowLayout());
    chooseLookAndFeel();
  }

  /**
   * <p>
   * Adds the given GUI components to this window.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param components the components to add.
   */
  @RunsInCurrentThread
  public void addComponents(@Nonnull Component... components) {
    for (Component c : components) {
      add(c);
    }
  }

  /**
   * Displays this window on the screen. This method is executed in the event dispatch thread (EDT.)
   */
  @RunsInEDT
  public void display() {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        display(TestWindow.this);
      }
    });
    waitForShowing(TestWindow.this);
  }

  /**
   * <p>
   * Displays the given window on the screen.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param w the window to display on the screen.
   * @return the displayed window.
   */
  @RunsInCurrentThread
  protected static @Nonnull <T extends TestWindow> T display(@Nonnull T w) {
    w.setLocation(DEFAULT_WINDOW_LOCATION);
    packAndShow(w);
    return w;
  }

  /**
   * Displays this window on the screen using the given dimension as its preferred size. This method is executed in the
   * event dispatch thread (EDT.)
   *
   * @param preferredSize the preferred size to set to this window before displaying it on the screen.
   */
  @RunsInEDT
  public void display(final @Nonnull Dimension preferredSize) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        display(TestWindow.this, preferredSize);
      }
    });
  }

  /**
   * <p>
   * Displays the given window on the screen using the given dimension as its preferred size.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param window the window to display on the screen.
   * @param preferredSize the preferred size to set to the given window before displaying it on the screen.
   */
  @RunsInCurrentThread
  protected static void display(@Nonnull TestWindow window, @Nonnull Dimension preferredSize) {
    window.setLocation(DEFAULT_WINDOW_LOCATION);
    packAndShow(window, preferredSize);
  }

  /**
   * <p>
   * Chooses the look and feel.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   */
  @RunsInCurrentThread
  protected void chooseLookAndFeel() {}

  /**
   * Hides and disposes this window. This method is executed in the event dispatch thread (EDT.)
   */
  @RunsInEDT
  public void destroy() {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        destroy(TestWindow.this);
      }
    });
  }

  /**
   * <p>
   * Hides and disposes the given window.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param window the window to destroy.
   */
  @RunsInCurrentThread
  protected static void destroy(@Nonnull TestWindow window) {
    hideAndDispose(window);
  }
}
