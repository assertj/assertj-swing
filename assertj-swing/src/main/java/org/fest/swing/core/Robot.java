/*
 * Created on Mar 1, 2008
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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.InputEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JPopupMenu;

import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.hierarchy.ComponentHierarchy;
import org.fest.swing.lock.ScreenLock;

/**
 * Simulates user input on an AWT or Swing {@code Component}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public interface Robot {
  /**
   * @return the {@code ComponentHierarchy} being used by this robot.
   */
  @Nonnull ComponentHierarchy hierarchy();

  /**
   * @return the {@code ComponentFinder} being used by this robot.
   */
  @Nonnull ComponentFinder finder();

  /**
   * @return the {@code ComponentPrinter} being used by this robot.
   */
  @Nonnull ComponentPrinter printer();

  /**
   * Safely display an AWT or Swing {@code Window} with proper EDT synchronization. This method blocks until the
   * {@code Window} is showing and ready for input.
   * 
   * @param w the window to display.
   */
  void showWindow(@Nonnull Window w);

  /**
   * Safely display an AWT or Swing {@code Window} with proper EDT synchronization. This method blocks until the
   * {@code Window} is showing and ready for input.
   * 
   * @param w the window to display.
   * @param size the size of the window to display.
   */
  void showWindow(@Nonnull Window w, @Nonnull Dimension size);

  /**
   * <p>
   * Safely display an AWT or Swing {@code Window} with proper EDT synchronization. This method blocks until the
   * {@code Window} is showing. This method will return even when the window is a modal dialog, since the show method is
   * called on the event dispatch thread (EDT.) The {@code Window} will be packed if the pack flag is set, and set to
   * the given size if it is non-{@code null}.
   * </p>
   * 
   * <p>
   * Modal dialogs may be shown with this method without blocking.
   * </p>
   * 
   * @param w the window to display.
   * @param size the size of the window to display. A {@code null} value indicates that the window does not need to be
   *          resized.
   * @param pack flag that indicates if the window should be packed or not. By packed we mean calling {@code w.pack()}.
   */
  void showWindow(@Nonnull Window w, @Nullable Dimension size, boolean pack);

  /**
   * Simulates a user closing the given AWT or Swing {@code Window}.
   * 
   * @param w the {@code Window} to close.
   */
  void close(@Nonnull Window w);

  /**
   * Gives input focus to the given AWT or Swing {@code Component}. Note that the {@code Component} may not yet have
   * focus when this method returns.
   * 
   * @param c the {@code Component} to give focus to.
   */
  void focus(@Nonnull Component c);

  /**
   * Gives input focus to the given AWT or Swing {@code Component} and waits until the {@code Component} has focus.
   * 
   * @param c the {@code Component} to give focus to.
   */
  void focusAndWaitForFocusGain(@Nonnull Component c);

  /**
   * Cleans up any used resources (keyboard, mouse, open windows and {@link ScreenLock}) used by this {@code Robot}.
   */
  void cleanUp();

  /**
   * <p>
   * Cleans up any used resources (keyboard, mouse and {@link ScreenLock}) used by this {@code Robot}. This method
   * <strong>does not</strong> dispose any open windows.
   * </p>
   * 
   * <p>
   * <strong>Note:</strong> The preferred method to use to clean up resources is {@link #cleanUp()}. Using
   * {@link #cleanUpWithoutDisposingWindows()} may leave many windows open after each test. Use it on very
   * special cases. Please read <a href="http://code.google.com/p/fest/issues/detail?id=138" target="_blank">bug 138</a>
   * for more details.
   * </p>
   */
  void cleanUpWithoutDisposingWindows();

  /**
   * Simulates a user clicking once the given AWT or Swing {@code Component} using the left mouse button.
   * 
   * @param c the {@code Component} to click on.
   * @throws ActionFailedException if the {@code Component} to click is out of the boundaries of the screen.
   */
  void click(@Nonnull Component c);

  /**
   * Simulates a user right-clicking the given AWT or Swing {@code Component}.
   * 
   * @param c the {@code Component} to click on.
   * @throws ActionFailedException if the {@code Component} to click is out of the boundaries of the screen.
   */
  void rightClick(@Nonnull Component c);

  /**
   * Simulates a user clicking once the given AWT or Swing {@code Component} using the given mouse button.
   * 
   * @param c the {@code Component} to click on.
   * @param button the mouse button to use.
   * @throws ActionFailedException if the {@code Component} to click is out of the boundaries of the screen.
   */
  void click(@Nonnull Component c, @Nonnull MouseButton button);

  /**
   * Simulates a user double-clicking the given AWT or Swing {@code Component}.
   * 
   * @param c the {@code Component} to click on.
   * @throws ActionFailedException if the {@code Component} to click is out of the boundaries of the screen.
   */
  void doubleClick(@Nonnull Component c);

  /**
   * Simulates a user clicking the given mouse button, the given times on the given AWT or Swing {@code Component}.
   * 
   * @param c the {@code Component} to click on.
   * @param button the mouse button to click.
   * @param times the number of times to click the given mouse button.
   * @throws ActionFailedException if the {@code Component} to click is out of the boundaries of the screen.
   */
  void click(@Nonnull Component c, @Nonnull MouseButton button, int times);

  /**
   * Simulates a user clicking at the given position on the given AWT or Swing {@code Component}.
   * 
   * @param c the {@code Component} to click on.
   * @param where the given coordinates, relative to the given {@code Component}.
   * @throws ActionFailedException if the {@code Component} to click is out of the boundaries of the screen.
   */
  void click(@Nonnull Component c, @Nonnull Point where);

  /**
   * Simulates a user clicking the given mouse button, the given times at the given position on the given AWT or Swing
   * {@code Component}.
   * 
   * @param c the {@code Component} to click on.
   * @param where the given coordinates, relative to the given {@code Component}.
   * @param button the mouse button to click.
   * @param times the number of times to click the given mouse button.
   * @throws ActionFailedException if the {@code Component} to click is out of the boundaries of the screen.
   */
  void click(@Nonnull Component c, @Nonnull Point where, @Nonnull MouseButton button, int times);

  /**
   * Simulates a user clicking the given mouse button, the given times at the given absolute coordinates.
   * 
   * @param where the coordinates where to click.
   * @param button the mouse button to click.
   * @param times the number of times to click the given mouse button.
   */
  void click(@Nonnull Point where, @Nonnull MouseButton button, int times);

  /**
   * Simulates a user pressing a mouse button.
   * 
   * @param button the mouse button to press.
   */
  void pressMouse(@Nonnull MouseButton button);

  /**
   * Simulates a user pressing the left mouse button on the given AWT or Swing {@code Component}.
   * 
   * @param c the {@code Component} to click on.
   * @param where the given coordinates, relative to the given {@code Component}.
   */
  void pressMouse(@Nonnull Component c, @Nonnull Point where);

  /**
   * Simulates a user pressing the given mouse button on the given AWT or Swing {@code Component}.
   * 
   * @param c the {@code Component} to click on.
   * @param where the given coordinates, relative to the given {@code Component}.
   * @param button the mouse button to press.
   */
  void pressMouse(@Nonnull Component c, @Nonnull Point where, @Nonnull MouseButton button);

  /**
   * Simulates a user pressing the given mouse button on the given coordinates.
   * 
   * @param where the position where to press the given mouse button.
   * @param button the mouse button to press.
   * @since 1.1
   */
  void pressMouse(@Nonnull Point where, @Nonnull MouseButton button);

  /**
   * Simulates a user moving the mouse pointer to the center of the given AWT or Swing {@code Component}.
   * 
   * @param c the given {@code Component}.
   */
  void moveMouse(@Nonnull Component c);

  /**
   * Simulates a user moving the mouse pointer to the given coordinates relative to the given AWT or Swing
   * {@code Component}.
   * 
   * @param c the given {@code Component}.
   * @param p the given coordinates, relative to the given {@code Component}.
   * @throws ActionFailedException if the given {@code Component} is not showing and ready for input.
   */
  void moveMouse(@Nonnull Component c, @Nonnull Point p);

  /**
   * Simulates a user moving the mouse pointer to the given coordinates relative to the given AWT or Swing
   * {@code Component}.
   * 
   * @param c the given {@code Component}.
   * @param x X coordinate, relative to the given {@code Component}.
   * @param y Y coordinate, relative to the given {@code Component}.
   * @throws ActionFailedException if the given {@code Component} is not showing and ready for input.
   */
  void moveMouse(@Nonnull Component c, int x, int y);

  /**
   * Simulates a user moving the mouse pointer to the given coordinates.
   * 
   * @param p the given coordinates.
   * @since 1.1
   */
  void moveMouse(@Nonnull Point p);

  /**
   * Simulates a user moving the mouse pointer to the given coordinates.
   * 
   * @param x X coordinate.
   * @param y Y coordinate.
   * @since 1.1
   */
  void moveMouse(int x, int y);

  /**
   * Releases the given mouse button.
   * 
   * @param button the mouse button to release.
   */
  void releaseMouse(@Nonnull MouseButton button);

  /**
   * Releases any mouse button(s) used by the robot.
   */
  void releaseMouseButtons();

  /**
   * Moves the mouse pointer over to the given AWT or Swing {@code Component} and rotates the scroll wheel on
   * wheel-equipped mice.
   * 
   * @param c the given {@code Component}.
   * @param amount number of "notches" to move the mouse wheel. Negative values indicate movement up/away from the user,
   *          while positive values indicate movement down/towards the user.
   */
  void rotateMouseWheel(@Nonnull Component c, int amount);

  /**
   * Rotates the scroll wheel on wheel-equipped mice.
   * 
   * @param amount number of "notches" to move the mouse wheel. Negative values indicate movement up/away from the user,
   *          while positive values indicate movement down/towards the user.
   */
  void rotateMouseWheel(int amount);

  /**
   * Makes the mouse pointer show small quick jumpy movements on the given AWT or Swing {@code Component}.
   * 
   * @param c the given {@code Component}.
   */
  void jitter(@Nonnull Component c);

  /**
   * Makes the mouse pointer show small quick jumpy movements on the given AWT or Swing {@code Component} at the given
   * point.
   * 
   * @param c the given {@code Component}.
   * @param where the given point.
   */
  void jitter(@Nonnull Component c, @Nonnull Point where);

  /**
   * Simulates a user entering the given text. Note that this method the key strokes to the AWT or Swing
   * {@code Component} that has input focus.
   * 
   * @param text the text to enter.
   */
  void enterText(@Nonnull String text);

  /**
   * Types the given character. Note that this method sends the key strokes to the AWT or Swing {@code Component} that
   * has input focus.
   * 
   * @param character the character to type.
   */
  void type(char character);

  /**
   * Type the given key code with the given modifiers. Modifiers is a mask from the available
   * {@code java.awt.event.InputEvent} masks.
   * 
   * @param keyCode the code of the key to press.
   * @param modifiers the given modifiers.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   */
  void pressAndReleaseKey(int keyCode, @Nonnull int... modifiers);

  /**
   * Simulates a user pressing and releasing the given keys. This method does not affect the current focus.
   * 
   * @param keyCodes one or more codes of the keys to press.
   * @see java.awt.event.KeyEvent
   * @throws IllegalArgumentException if any of the given codes is not a valid key code.
   */
  void pressAndReleaseKeys(@Nonnull int... keyCodes);

  /**
   * Simulates a user pressing given key. This method does not affect the current focus.
   * 
   * @param keyCode the code of the key to press.
   * @see java.awt.event.KeyEvent
   * @throws IllegalArgumentException if the given code is not a valid key code.
   */
  void pressKey(int keyCode);

  /**
   * Simulates a user releasing the given key. This method does not affect the current focus.
   * 
   * @param keyCode the code of the key to release.
   * @see java.awt.event.KeyEvent
   * @throws IllegalArgumentException if the given code is not a valid key code.
   */
  void releaseKey(int keyCode);

  /**
   * Presses the appropriate modifiers corresponding to the given mask. Use mask values from
   * {@code java.awt.event.InputEvent}.
   * 
   * @param modifierMask the given mask.
   * @see InputEvent
   */
  void pressModifiers(int modifierMask);

  /**
   * Releases the appropriate modifiers corresponding to the given mask. Use mask values from
   * {@code java.awt.event.InputEvent}.
   * 
   * @param modifierMask the given mask.
   * @see InputEvent
   */
  void releaseModifiers(int modifierMask);

  /**
   * Wait for an idle AWT event queue. Note that this is different from the implementation of
   * {@code java.awt.Robot.waitForIdle()}, which may have events on the queue when it returns. Do <strong>NOT</strong>
   * use this method if there are animations or other continual refreshes happening, since in that case it may never
   * return.
   * 
   * @throws IllegalThreadStateException if this method is called from the event dispatch thread (EDT.)
   */
  void waitForIdle();

  /**
   * Indicates whether the robot is currently in a dragging operation.
   * 
   * @return {@code true} if the robot is currently in a dragging operation, {@code false} otherwise.
   */
  boolean isDragging();

  /**
   * Indicates whether the given AWT or Swing {@code Component} is ready for input.
   * 
   * @param c the given {@code Component}.
   * @return {@code true} if the given {@code Component} is ready for input, {@code false} otherwise.
   * @throws ActionFailedException if the given {@code Component} does not have a {@code Window} ancestor.
   */
  boolean isReadyForInput(@Nonnull Component c);

  /**
   * Shows a {@code JPopupMenu}.
   * 
   * @param invoker the {@code Component} to invoke the {@code JPopupMenu} from.
   * @return the displayed {@code JPopupMenu}.
   * @throws ComponentLookupException if a {@code JPopupMenu} cannot be found.
   */
  @Nonnull JPopupMenu showPopupMenu(@Nonnull Component invoker);

  /**
   * Shows a {@code JPopupMenu} at the given coordinates.
   * 
   * @param invoker the {@code Component} to invoke the {@code JPopupMenu} from.
   * @param location the given coordinates for the {@code JPopupMenu}.
   * @return the displayed {@code JPopupMenu}.
   * @throws ComponentLookupException if a {@code JPopupMenu} cannot be found.
   */
  @Nonnull JPopupMenu showPopupMenu(@Nonnull Component invoker, @Nonnull Point location);

  /**
   * @return the currently active {@code JPopupMenu} or {@code null}, if no pop-up is currently showing.
   */
  @Nullable JPopupMenu findActivePopupMenu();

  /**
   * Ensures that there is no {@code JOptionPane} showing, and potentially blocking GUI tests.
   * 
   * @throws AssertionError if there is one or more {@code JOptionPane}s showing on the screen.
   */
  void requireNoJOptionPaneIsShowing();

  /**
   * @return the configuration settings for this {@code Robot}.
   */
  @Nonnull Settings settings();

  /**
   * Indicates whether this {@code Robot} is active. Being "active" means that {@link #cleanUp()} has not been called
   * yet.
   * 
   * @return {@code true} if this {@code Robot} is active, {@code false} otherwise.
   */
  boolean isActive();
}