/*
 * Created on Sep 21, 2007
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

import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.MouseButton.MIDDLE_BUTTON;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Strings.concat;

import javax.annotation.Nonnull;

/**
 * <p>
 * Information about a mouse button to click.
 * </p>
 * 
 * <p>
 * Examples:
 * </p>
 * 
 * <p>
 * Specify that the right button should be clicked once:
 * <pre>
 * // import static org.fest.swing.fixture.MouseClickInfo.*;
 * MouseClickInfo i = rightButton();
 * </pre>
 * </p>
 * 
 * <p>
 * Specify that the left button should be clicked two times (similar to double-click):
 * <pre>
 * // import static org.fest.swing.fixture.MouseClickInfo.*;
 * MouseClickInfo i = leftButton().times(2);
 * </pre>
 * </p>
 * 
 * @author Alex Ruiz
 */
public final class MouseClickInfo {
  private final MouseButton button;
  private int times;

  /**
   * Specifies that the left button should be clicked once.
   * 
   * @return the created click info.
   */
  public static @Nonnull MouseClickInfo leftButton() {
    return button(LEFT_BUTTON);
  }

  /**
   * Specifies that the middle button should be clicked once.
   * 
   * @return the created click info.
   */
  public static @Nonnull MouseClickInfo middleButton() {
    return button(MIDDLE_BUTTON);
  }

  /**
   * Specifies that the right button should be clicked once.
   * 
   * @return the created click info.
   */
  public static @Nonnull MouseClickInfo rightButton() {
    return button(RIGHT_BUTTON);
  }

  /**
   * Specifies that the given button should be clicked once.
   * 
   * @param button the mouse button to click.
   * @return the created click info.
   * @throws NullPointerException if {@code button} is {@code null}.
   */
  public static @Nonnull MouseClickInfo button(@Nonnull MouseButton button) {
    return new MouseClickInfo(button, 1);
  }

  private MouseClickInfo(@Nonnull MouseButton button, int times) {
    this.button = checkNotNull(button);
    this.times = times;
  }

  /**
   * @return the button to click.
   */
  public @Nonnull MouseButton button() {
    return button;
  }

  /**
   * @return how many times the {@link #button() mouse button} should be clicked.
   */
  public int times() {
    return times;
  }

  /**
   * Specifies how many times the mouse button should be clicked.
   * 
   * @param newTimes the specified number of times to click the mouse button.
   * @return this object.
   */
  public MouseClickInfo times(int newTimes) {
    times = newTimes;
    return this;
  }

  @Override
  public @Nonnull String toString() {
    return concat(String.format("%s[button=%s, times=%d]", getClass().getName(), button.toString(), times));
  }
}
