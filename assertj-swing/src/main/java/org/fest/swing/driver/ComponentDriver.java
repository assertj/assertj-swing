/*
 * Created on Jan 26, 2008
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.driver.ComponentEnabledCondition.untilIsEnabled;
import static org.fest.swing.driver.ComponentPerformDefaultAccessibleActionTask.performDefaultAccessibleAction;
import static org.fest.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.query.ComponentEnabledQuery.isEnabled;
import static org.fest.swing.query.ComponentHasFocusQuery.hasFocus;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.query.ComponentVisibleQuery.isVisible;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.ComponentDragAndDrop;
import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.core.Settings;
import org.fest.swing.edt.GuiLazyLoadingDescription;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.format.ComponentFormatter;
import org.fest.swing.format.Formatting;
import org.fest.swing.timing.Timeout;
import org.fest.swing.util.TimeoutWatch;
import org.fest.util.InternalApi;

/**
 * <p>
 * Supports functional testing of AWT or Swing {@code Component}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.fest.swing.fixture} in your tests.
 * </p>
 *
 * @author Alex Ruiz
 */
@InternalApi
public class ComponentDriver {
  private static final String ENABLED_PROPERTY = "enabled";
  private static final String SIZE_PROPERTY = "size";
  private static final String VISIBLE_PROPERTY = "visible";

  protected final Robot robot;

  private final ComponentDragAndDrop dragAndDrop;

  /**
   * Creates a new {@link ComponentDriver}.
   *
   * @param robot the robot to use to simulate user input.
   */
  public ComponentDriver(@Nonnull Robot robot) {
    this.robot = robot;
    this.dragAndDrop = new ComponentDragAndDrop(robot);
  }

  /**
   * Simulates a user clicking once the given AWT or Swing {@code Component} using the left mouse button.
   *
   * @param c the {@code Component} to click on.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @RunsInEDT
  public void click(@Nonnull Component c) {
    checkInEdtEnabledAndShowing(c);
    robot.click(c);
  }

  /**
   * Simulates a user clicking once the given AWT or Swing {@code Component} using the given mouse button.
   *
   * @param c the {@code Component} to click on.
   * @param button the mouse button to use.
   * @throws NullPointerException if the given {@code MouseButton} is {@code null}.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @RunsInEDT
  public void click(@Nonnull Component c, @Nonnull MouseButton button) {
    click(c, button, 1);
  }

  /**
   * Simulates a user clicking the given mouse button, the given times on the given AWT or Swing {@code Component}.
   *
   * @param c the {@code Component} to click on.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @throws NullPointerException if the given {@code MouseClickInfo} is {@code null}.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @RunsInEDT
  public void click(@Nonnull Component c, @Nonnull MouseClickInfo mouseClickInfo) {
    checkNotNull(mouseClickInfo);
    click(c, mouseClickInfo.button(), mouseClickInfo.times());
  }

  /**
   * Simulates a user double-clicking the given AWT or Swing {@code Component}.
   *
   * @param c the {@code Component} to click on.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @RunsInEDT
  public void doubleClick(@Nonnull Component c) {
    click(c, LEFT_BUTTON, 2);
  }

  /**
   * Simulates a user right-clicking the given AWT or Swing {@code Component}.
   *
   * @param c the {@code Component} to click on.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @RunsInEDT
  public void rightClick(@Nonnull Component c) {
    click(c, RIGHT_BUTTON);
  }

  /**
   * Simulates a user clicking the given mouse button, the given times on the given AWT or Swing {@code Component}.
   *
   * @param c the {@code Component} to click on.
   * @param button the mouse button to click.
   * @param times the number of times to click the given mouse button.
   * @throws NullPointerException if the given {@code MouseButton} is {@code null}.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @RunsInEDT
  public void click(@Nonnull Component c, @Nonnull MouseButton button, int times) {
    checkNotNull(button);
    checkInEdtEnabledAndShowing(c);
    robot.click(c, button, times);
  }

  /**
   * Simulates a user clicking at the given position on the given AWT or Swing {@code Component}.
   *
   * @param c the {@code Component} to click on.
   * @param where the position where to click.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @RunsInEDT
  public void click(@Nonnull Component c, @Nonnull Point where) {
    checkInEdtEnabledAndShowing(c);
    robot.click(c, where);
  }

  protected @Nonnull Settings settings() {
    return robot.settings();
  }

  /**
   * Asserts that the size of the AWT or Swing {@code Component} is equal to given one.
   *
   * @param c the target {@code Component}.
   * @param size the given size to match.
   * @throws AssertionError if the size of the {@code Component} is not equal to the given size.
   */
  @RunsInEDT
  public void requireSize(@Nonnull Component c, @Nonnull Dimension size) {
    assertThat(sizeOf(c)).as(propertyName(c, SIZE_PROPERTY)).isEqualTo(size);
  }

  /**
   * Asserts that the AWT or Swing {@code Component} is visible.
   *
   * @param c the target {@code Component}.
   * @throws AssertionError if the {@code Component} is not visible.
   */
  @RunsInEDT
  public void requireVisible(@Nonnull Component c) {
    assertThat(isVisible(c)).as(visibleProperty(c)).isTrue();
  }

  /**
   * Asserts that the AWT or Swing {@code Component} is not visible.
   *
   * @param c the target {@code Component}.
   * @throws AssertionError if the {@code Component} is visible.
   */
  @RunsInEDT
  public void requireNotVisible(@Nonnull Component c) {
    assertThat(isVisible(c)).as(visibleProperty(c)).isFalse();
  }

  @RunsInEDT
  private static @Nonnull Description visibleProperty(@Nonnull Component c) {
    return propertyName(c, VISIBLE_PROPERTY);
  }

  /**
   * Asserts that the AWT or Swing {@code Component} has input focus.
   *
   * @param c the target {@code Component}.
   * @throws AssertionError if the {@code Component} does not have input focus.
   */
  @RunsInEDT
  public void requireFocused(@Nonnull Component c) {
    assertThat(hasFocus(c)).as(requiredFocusedErrorMessage(c)).isTrue();
  }

  private static @Nonnull Description requiredFocusedErrorMessage(final Component c) {
    return new GuiLazyLoadingDescription() {
      @Override
      protected @Nonnull String loadDescription() {
        return String.format("Expected component %s to have input focus", format(c));
      }
    };
  }

  /**
   * Asserts that the AWT or Swing {@code Component} is enabled.
   *
   * @param c the target {@code Component}.
   * @throws AssertionError if the {@code Component} is disabled.
   */
  @RunsInEDT
  public void requireEnabled(@Nonnull Component c) {
    assertThat(isEnabled(c)).as(enabledProperty(c)).isTrue();
  }

  /**
   * Asserts that the AWT or Swing {@code Component} is enabled.
   *
   * @param c the target {@code Component}.
   * @param timeout the time this fixture will wait for the {@code Component} to be enabled.
   * @throws WaitTimedOutError if the {@code Component} is never enabled.
   */
  @RunsInEDT
  public void requireEnabled(@Nonnull Component c, @Nonnull Timeout timeout) {
    pause(untilIsEnabled(c), timeout);
  }

  /**
   * Asserts that the AWT or Swing {@code Component} is disabled.
   *
   * @param c the target {@code Component}.
   * @throws AssertionError if the {@code Component} is enabled.
   */
  @RunsInEDT
  public void requireDisabled(@Nonnull Component c) {
    assertThat(isEnabled(c)).as(enabledProperty(c)).isFalse();
  }

  @RunsInEDT
  private static @Nonnull Description enabledProperty(@Nonnull Component c) {
    return propertyName(c, ENABLED_PROPERTY);
  }

  /**
   * Simulates a user pressing and releasing the given keys on the AWT or Swing {@code Component}.
   *
   * @param c the target {@code Component}.
   * @param keyCodes one or more codes of the keys to press.
   * @throws NullPointerException if the given array of codes is {@code null}.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   */
  @RunsInEDT
  public void pressAndReleaseKeys(@Nonnull Component c, @Nonnull int... keyCodes) {
    checkNotNull(keyCodes);
    checkInEdtEnabledAndShowing(c);
    focusAndWaitForFocusGain(c);
    robot.pressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing and releasing the given key on the AWT or Swing {@code Component}. Modifiers is a mask
   * from the available AWT {@code InputEvent} masks.
   *
   * @param c the target {@code Component}.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @throws NullPointerException if the given {@code KeyPressInfo} is {@code null}.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   * @see java.awt.event.KeyEvent
   * @see java.awt.event.InputEvent
   */
  @RunsInEDT
  public void pressAndReleaseKey(@Nonnull Component c, @Nonnull KeyPressInfo keyPressInfo) {
    checkNotNull(keyPressInfo);
    pressAndReleaseKey(c, keyPressInfo.keyCode(), keyPressInfo.modifiers());
  }

  /**
   * Simulates a user pressing and releasing the given key on the AWT or Swing {@code Component}. Modifiers is a mask
   * from the available AWT {@code InputEvent} masks.
   *
   * @param c the target {@code Component}.
   * @param keyCode the code of the key to press.
   * @param modifiers the given modifiers.
   * @throws IllegalArgumentException if the given code is not a valid key code. *
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   * @see java.awt.event.KeyEvent
   * @see java.awt.event.InputEvent
   */
  @RunsInEDT
  public void pressAndReleaseKey(@Nonnull Component c, int keyCode, @Nonnull int[] modifiers) {
    focusAndWaitForFocusGain(c);
    robot.pressAndReleaseKey(keyCode, modifiers);
  }

  /**
   * Simulates a user pressing given key on the AWT or Swing {@code Component}.
   *
   * @param c the target {@code Component}.
   * @param keyCode the code of the key to press.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  @RunsInEDT
  public void pressKey(@Nonnull Component c, int keyCode) {
    focusAndWaitForFocusGain(c);
    robot.pressKey(keyCode);
  }

  /**
   * Simulates a user releasing the given key on the AWT or Swing {@code Component}.
   *
   * @param c the target {@code Component}.
   * @param keyCode the code of the key to release.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  @RunsInEDT
  public void releaseKey(@Nonnull Component c, int keyCode) {
    focusAndWaitForFocusGain(c);
    robot.releaseKey(keyCode);
  }

  /**
   * Gives input focus to the given AWT or Swing {@code Component} and waits until the {@code Component} has focus.
   *
   * @param c the {@code Component} to give focus to.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @RunsInEDT
  public void focusAndWaitForFocusGain(@Nonnull Component c) {
    checkInEdtEnabledAndShowing(c);
    robot.focusAndWaitForFocusGain(c);
  }

  /**
   * Gives input focus to the given AWT or Swing {@code Component}. Note that the {@code Component} may not yet have focus when this
   * method returns.
   *
   * @param c the {@code Component} to give focus to.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @RunsInEDT
  public void focus(@Nonnull Component c) {
    checkInEdtEnabledAndShowing(c);
    robot.focus(c);
  }

  /**
   * Performs a drag action at the given point.
   *
   * @param c the target {@code Component}.
   * @param where the point where to start the drag action.
   */
  @RunsInEDT
  protected final void drag(@Nonnull Component c, @Nonnull Point where) {
    dragAndDrop.drag(c, where);
  }

  /**
   * <p>
   * Ends a drag operation, releasing the mouse button over the given target location.
   * </p>
   * <p>
   * This method is tuned for native drag/drop operations, so if you get odd behavior, you might try using a simple
   * {@link Robot#moveMouse(Component, int, int)} and {@link Robot#releaseMouseButtons()}.
   *
   * @param c the target {@code Component}.
   * @param where the point where the drag operation ends.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  @RunsInEDT
  protected final void drop(@Nonnull Component c, @Nonnull Point where) {
    dragAndDrop.drop(c, where);
  }

  /**
   * Move the mouse appropriately to get from the source to the destination. Enter/exit events will be generated where
   * appropriate.
   *
   * @param c the target {@code Component}.
   * @param where the point to drag over.
   */
  protected final void dragOver(@Nonnull Component c, @Nonnull Point where) {
    dragAndDrop.dragOver(c, where);
  }

  /**
   * <p>
   * Performs the {@code AccessibleAction} in the given AWT or Swing {@code Component}'s event queue.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param c the given {@code Component}.
   * @throws ActionFailedException if something goes wrong.
   */
  @RunsInCurrentThread
  protected final void performAccessibleActionOf(@Nonnull Component c) {
    performDefaultAccessibleAction(c);
    robot.waitForIdle();
  }

  /**
   * <p>
   * Wait the given number of milliseconds for the AWT or Swing {@code Component} to be showing and ready. Returns
   * {@code false} if the operation times out.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param c the given {@code Component}.
   * @param timeout the time in milliseconds to wait for the {@code Component} to be showing and ready.
   * @return {@code true} if the {@code Component} is showing and ready, {@code false} otherwise.
   */
  @RunsInCurrentThread
  protected final boolean waitForShowing(@Nonnull Component c, long timeout) {
    // TODO test
    if (robot.isReadyForInput(c)) {
      return true;
    }
    TimeoutWatch watch = startWatchWithTimeoutOf(timeout);
    while (!robot.isReadyForInput(c)) {
      if (c instanceof JPopupMenu) {
        // move the mouse over the parent menu item to ensure the sub-menu shows
        Component invoker = ((JPopupMenu) c).getInvoker();
        if (invoker instanceof JMenu) {
          robot.jitter(invoker);
        }
      }
      if (watch.isTimeOut()) {
        return false;
      }
      pause();
    }
    return true;
  }

  /**
   * Shows a pop-up menu using the given AWT or Swing {@code Component} as the invoker of the pop-up menu.
   *
   * @param c the invoker of the {@code JPopupMenu}.
   * @return the displayed pop-up menu.
   * @throws IllegalStateException if the given {@code Component} is disabled.
   * @throws IllegalStateException if the given {@code Component} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  @RunsInEDT
  public @Nonnull JPopupMenu invokePopupMenu(@Nonnull Component c) {
    checkInEdtEnabledAndShowing(c);
    return robot.showPopupMenu(c);
  }

  /**
   * Shows a pop-up menu at the given point using the given AWT or Swing {@code Component} as the invoker of the pop-up
   * menu.
   *
   * @param c the invoker of the {@code JPopupMenu}.
   * @param p the given point where to show the pop-up menu.
   * @return the displayed pop-up menu.
   * @throws NullPointerException if the given point is {@code null}.
   * @throws IllegalStateException if the given {@code Component} is disabled.
   * @throws IllegalStateException if the given {@code Component} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  @RunsInEDT
  public @Nonnull JPopupMenu invokePopupMenu(@Nonnull Component c, @Nonnull Point p) {
    checkNotNull(p);
    checkInEdtEnabledAndShowing(c);
    return robot.showPopupMenu(c, p);
  }

  /**
   * Verifies that the given AWT or Swing {@code Component} is enabled and showing on the screen. This method is
   * executed in the event dispatch thread (EDT.)
   *
   * @param c the {@code Component} to check.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @RunsInEDT
  protected static void checkInEdtEnabledAndShowing(final @Nonnull Component c) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        checkEnabledAndShowing(c);
      }
    });
  }

  /**
   * Formats the name of a property of the given AWT or Swing {@code Component} by concatenating the value obtained from
   * {@link Formatting#format(Component)} with the given property name.
   *
   * @param c the given {@code Component}.
   * @param propertyName the name of the property.
   * @return the description of a property belonging to a {@code Component}.
   * @see ComponentFormatter
   * @see Formatting#format(Component)
   */
  @RunsInEDT
  public static @Nonnull Description propertyName(final @Nonnull Component c, final @Nonnull String propertyName) {
    return new GuiLazyLoadingDescription() {
      @Override
      protected @Nonnull String loadDescription() {
        return String.format("%s - property:'%s'", format(c), propertyName);
      }
    };
  }

  /**
   * Simulates a user moving the mouse pointer to the given coordinates relative to the given
   * AWT or Swing {@code Component}. This method will <b>not</b> throw any exceptions if the it was not possible to move the
   * mouse pointer.
   *
   * @param c the given {@code Component}.
   * @param p coordinates relative to the given {@code Component}.
   */
  @RunsInEDT
  protected final void moveMouseIgnoringAnyError(@Nonnull Component c, @Nonnull Point p) {
    moveMouseIgnoringAnyError(c, p.x, p.y);
  }

  /**
   * Simulates a user moving the mouse pointer to the given coordinates relative to the given AWT or Swing
   * {@code Component}. This method will <b>not</b> throw any exceptions if the it was not possible to move the mouse
   * pointer.
   *
   * @param c the given {@code Component}.
   * @param x horizontal coordinate relative to the given {@code Component}.
   * @param y vertical coordinate relative to the given {@code Component}.
   */
  @RunsInEDT
  protected final void moveMouseIgnoringAnyError(@Nonnull Component c, int x, int y) {
    try {
      robot.moveMouse(c, x, y);
    } catch (RuntimeException ignored) {
    }
  }

  /**
   * Returns the font of the given AWT or Swing {@code Component}.
   *
   * @param c the given {@code Component}.
   * @return the font of the given {@code Component}.
   */
  @RunsInEDT
  public @Nonnull Font fontOf(final @Nonnull Component c) {
    Font result = execute(new GuiQuery<Font>() {
      @Override
      protected @Nullable Font executeInEDT() {
        return c.getFont();
      }
    });
    return checkNotNull(result);
  }

  /**
   * Returns the background color of the given AWT or Swing {@code Component}.
   *
   * @param c the given {@code Component}.
   * @return the background color of the given {@code Component}.
   */
  @RunsInEDT
  public @Nonnull Color backgroundOf(final @Nonnull Component c) {
    Color result = execute(new GuiQuery<Color>() {
      @Override
      protected @Nullable
      Color executeInEDT() {
        return c.getBackground();
      }
    });
    return checkNotNull(result);
  }

  /**
   * Returns the foreground color of the given AWT or Swing {@code Component}.
   *
   * @param c the given {@code Component}.
   * @return the foreground color of the given {@code Component}.
   */
  @RunsInEDT
  public @Nonnull Color foregroundOf(final @Nonnull Component c) {
    Color result = execute(new GuiQuery<Color>() {
      @Override
      protected @Nullable Color executeInEDT() {
        return c.getForeground();
      }
    });
    return checkNotNull(result);
  }
}
