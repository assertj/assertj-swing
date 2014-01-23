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

import static org.fest.swing.util.Arrays.copyOf;

import javax.annotation.Nonnull;

import org.fest.swing.util.Platform;

/**
 * <p>
 * Information about a key (from the keyboard) to press.
 * </p>
 * 
 * <p>
 * Examples:
 * </p>
 * 
 * <p>
 * Specify that 'CTRL' + 'C' should be pressed:
 * <pre>
 * // import static org.fest.swing.fixture.KeyPressInfo.*;
 * KeyPressInfo i = key(VK_C).modifiers(CTRL_MASK);
 * </pre>
 * </p>
 * 
 * <p>
 * Specify that 'SHIFT' + 'R' should be pressed:
 * <pre>
 * // import static org.fest.swing.fixture.KeyPressInfo.*;
 * KeyPressInfo i = key(VK_R).modifiers(SHIFT_MASK);
 * </pre>
 * </p>
 * <p>
 * For platform-safe mask pressing (e.g. 'Control' in Windows or 'Command' in MacOS) use
 * {@link Platform#controlOrCommandMask()}.
 * </p>
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class KeyPressInfo {
  private static final int[] NO_MODIFIERS = {};

  private final int keyCode;
  private int[] modifiers;

  /**
   * Specifies the code of the key to press, without any modifiers (e.g. {@code java.awt.event.KeyEvent.VK_C}.)
   * 
   * @param keyCode the code of the key to press.
   * @return the created {@code KeyPressInfo}.
   */
  public static @Nonnull
  KeyPressInfo keyCode(int keyCode) {
    return new KeyPressInfo(keyCode, NO_MODIFIERS);
  }

  private KeyPressInfo(int keyCode, @Nonnull int[] modifiers) {
    this.keyCode = keyCode;
    this.modifiers = modifiers;
  }

  /**
   * @return the code of the key to press.
   */
  public int keyCode() {
    return keyCode;
  }

  /**
   * @return the modifiers to use when pressing {@link #keyCode() the specified key}.
   */
  public @Nonnull int[] modifiers() {
    return copyOf(modifiers);
  }

  /**
   * <p>
   * Specifies the modifiers to use when pressing {@link #keyCode() the specified key} (e.g.
   * {@code java.awt.Event.CTRL_MASK}.)
   * </p>
   * 
   * <p>
   * For platform-safe mask pressing (e.g. 'Control' in Windows or 'Command' in MacOS) use
   * {@link Platform#controlOrCommandMask()}.
   * </p>
   * 
   * @param newModifiers the new modifiers to use.
   * @return this object.
   * @throws NullPointerException if {@code newModifiers} is {@code null}.
   */
  public @Nonnull KeyPressInfo modifiers(@Nonnull int... newModifiers) {
    modifiers = copyOf(newModifiers);
    return this;
  }
}
