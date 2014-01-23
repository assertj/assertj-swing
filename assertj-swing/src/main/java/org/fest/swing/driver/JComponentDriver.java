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

import static java.awt.event.KeyEvent.VK_UNDEFINED;
import static org.fest.swing.driver.Actions.findActionKey;
import static org.fest.swing.driver.JComponentToolTipQuery.toolTipOf;
import static org.fest.swing.driver.KeyStrokes.findKeyStrokesForAction;
import static org.fest.swing.driver.TextAssert.verifyThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ActionFailedException;
import org.fest.util.InternalApi;

/**
 * <p>
 * Supports functional testing of {@code JComponent}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.fest.swing.fixture} in your tests.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@InternalApi
public class JComponentDriver extends ContainerDriver {
  private static final String TOOL_TIP_TEXT_PROPERTY = "toolTipText";

  /**
   * Creates a new {@link JComponentDriver}.
   *
   * @param robot the robot the robot to use to simulate user input.
   */
  public JComponentDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * <p>
   * Invokes {@code JComponent.scrollRectToVisible(Rectangle)} on the given {@code JComponent}.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param c the given {@code JComponent}.
   * @param r the visible {@code Rectangle}.
   */
  @RunsInCurrentThread
  protected final void scrollToVisible(@Nonnull JComponent c, @Nonnull Rectangle r) {
    // From Abbot:
    // Ideally, we'd use scrollBar commands to effect the scrolling, but that gets really complicated for no real gain
    // in function. Fortunately, Swing's Scrollable makes for a simple solution.
    // NOTE: absolutely MUST wait for idle in order for the scroll to finish, and the UI to update so that the next
    // action goes to the proper location within the scrolled component.
    c.scrollRectToVisible(r);
  }

  /**
   * <p>
   * Indicates whether the given {@code JComponent}'s visible {@link Rectangle} contains the given one.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param c the given {@code JComponent}.
   * @param r the {@code Rectangle} to verify.
   * @return {@code true} if the given {@code Rectangle} is contained in the given {@code JComponent}'s visible
   *         {@code Rectangle}.
   */
  @RunsInCurrentThread
  protected static boolean isVisible(@Nonnull JComponent c, @Nonnull Rectangle r) {
    return c.getVisibleRect().contains(r);
  }

  /**
   * <p>
   * Indicates whether the given {@code JComponent}'s visible {@link Rectangle} contains the given {@link Point}.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param c the given {@code JComponent}.
   * @param p the {@code Point} to verify.
   * @return {@code true} if the given {@code Point} is contained in the given {@code JComponent}'s visible
   *         {@code Rectangle}.
   */
  @RunsInCurrentThread
  protected final boolean isVisible(@Nonnull JComponent c, @Nonnull Point p) {
    return c.getVisibleRect().contains(p);
  }

  /**
   * Invokes an {@code javax.swing.Action} from the {@code JComponent}'s {@code javax.swing.ActionMap}.
   *
   * @param c the given {@code JComponent}.
   * @param name the name of the {@code Action} to invoke.
   * @throws ActionFailedException if an {@code Action} cannot be found under the given name.
   * @throws ActionFailedException if a {@code KeyStroke} cannot be found for the {@code Action} under the given name.
   * @throws ActionFailedException if it is not possible to type any of the found {@code KeyStroke}s.
   */
  @RunsInEDT
  protected final void invokeAction(@Nonnull JComponent c, @Nonnull String name) {
    robot.focusAndWaitForFocusGain(c);
    for (KeyStroke keyStroke : keyStrokesForAction(c, name)) {
      try {
        type(checkNotNull(keyStroke));
        robot.waitForIdle();
        return;
      } catch (IllegalArgumentException e) { /* try the next one, if any */
      }
    }
    throw actionFailure(String.format("Unable to type any key for the action with key '%s'", name));
  }

  @RunsInCurrentThread
  private static KeyStroke[] keyStrokesForAction(@Nonnull JComponent component, @Nonnull String actionName) {
    Object key = findActionKey(actionName, checkNotNull(component.getActionMap()));
    return findKeyStrokesForAction(actionName, key, checkNotNull(component.getInputMap()));
  }

  private void type(@Nonnull KeyStroke keyStroke) {
    if (keyStroke.getKeyCode() == VK_UNDEFINED) {
      robot.type(keyStroke.getKeyChar());
      return;
    }
    robot.pressAndReleaseKey(keyStroke.getKeyCode(), keyStroke.getModifiers());
  }

  /**
   * Asserts that the toolTip in the given {@code JComponent} matches the given value.
   *
   * @param c the given {@code JComponent}.
   * @param expected the expected toolTip. It can be a regular expression.
   * @throws AssertionError if the toolTip of the given {@code JComponent} does not match the given value.
   * @since 1.2
   */
  @RunsInEDT
  public void requireToolTip(@Nonnull JComponent c, @Nullable String expected) {
    verifyThat(toolTipOf(c)).as(propertyName(c, TOOL_TIP_TEXT_PROPERTY)).isEqualOrMatches(expected);
  }

  /**
   * Asserts that the toolTip in the given {@code JComponent} matches the given regular expression pattern.
   *
   * @param c the given {@code JComponent}.
   * @param pattern the regular expression pattern to match.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the toolTip of the given {@code JComponent} does not match the given value.
   * @since 1.2
   */
  @RunsInEDT
  public void requireToolTip(@Nonnull JComponent c, @Nonnull Pattern pattern) {
    verifyThat(toolTipOf(c)).as(propertyName(c, TOOL_TIP_TEXT_PROPERTY)).matches(pattern);
  }

  /**
   * Returns the client property stored in the given {@code JComponent}, under the given key.
   *
   * @param c the given {@code JComponent}.
   * @param key the key to use to retrieve the client property.
   * @return the value of the client property stored under the given key, or {@code null} if the property was not found.
   * @throws NullPointerException if the given key is {@code null}.
   * @since 1.2
   */
  @RunsInEDT
  public @Nullable Object clientProperty(@Nonnull JComponent c, @Nonnull Object key) {
    return clientPropertyIn(c, checkNotNull(key));
  }

  private static @Nullable Object clientPropertyIn(final @Nonnull JComponent c, final @Nonnull Object key) {
    return execute(new GuiQuery<Object>() {
      @Override
      protected @Nullable Object executeInEDT() {
        return c.getClientProperty(key);
      }
    });
  }
}
