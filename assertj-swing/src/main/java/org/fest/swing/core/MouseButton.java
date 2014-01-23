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

import static java.awt.event.InputEvent.BUTTON1_MASK;
import static java.awt.event.InputEvent.BUTTON2_MASK;
import static java.awt.event.InputEvent.BUTTON3_MASK;

import java.awt.event.InputEvent;

import javax.annotation.Nonnull;

/**
 * Mouse buttons. Provides better type safety that the {@code int} values in AWT.
 * 
 * @author Alex Ruiz
 */
public enum MouseButton {
  LEFT_BUTTON(BUTTON1_MASK), MIDDLE_BUTTON(BUTTON2_MASK), RIGHT_BUTTON(BUTTON3_MASK);

  /**
   * The mouse button modifier.
   * 
   * @see InputEvent
   */
  public final int mask;

  private MouseButton(int mask) {
    this.mask = mask;
  }

  /**
   * Returns the mouse button whose mask matches the given mask. Valid values are the constants {@code BUTTON1_MASK},
   * {@code BUTTON2_MASK}, and {@code BUTTON3_MASK} in {@code java.awt.event.InputEvent}.
   * 
   * @param mask the mask of the button we are looking for.
   * @return the found button.
   * @throws IllegalArgumentException if the given mask is not a valid one.
   */
  public @Nonnull static MouseButton lookup(int mask) {
    for (MouseButton button : values()) {
      if (button.mask == mask) {
        return button;
      }
    }
    throw new IllegalArgumentException(String.format("%d is not a valid button mas", mask));
  }
}
