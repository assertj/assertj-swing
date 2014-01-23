/*
 * Created on Mar 26, 2008
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
package org.fest.swing.keystroke;

import static org.fest.util.Objects.HASH_CODE_PRIME;
import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.swing.KeyStroke;

/**
 * A mapping between a character and a {@code javax.swing.KeyStroke}.
 *
 * @author Yvonne Wang
 */
public class KeyStrokeMapping {
  private final char character;
  private final KeyStroke keyStroke;

  /**
   * Creates a new {@link KeyStrokeMapping}.
   *
   * @param character the character corresponding to the intended {@code KeyStroke}.
   * @param keyCode the numeric key code for the intended {@code KeyStroke}.
   * @param modifiers the set of modifiers for the intended {@code KeyStroke}.
   * @return the created {@code KeyStrokeMapping}.
   */
  public static @Nonnull KeyStrokeMapping mapping(char character, int keyCode, int modifiers) {
    return new KeyStrokeMapping(character, keyCode, modifiers);
  }

  /**
   * Creates a new {@link KeyStrokeMapping}.
   *
   * @param character the character corresponding to the intended {@code KeyStroke}.
   * @param keyCode the numeric key code for the intended {@code KeyStroke}.
   * @param modifiers the set of modifiers for the intended {@code KeyStroke}.
   */
  public KeyStrokeMapping(char character, int keyCode, int modifiers) {
    this(character, checkNotNull(KeyStroke.getKeyStroke(keyCode, modifiers)));
  }

  /**
   * Creates a new {@link KeyStrokeMapping}.
   *
   * @param character the character corresponding to the given {@code KeyStroke}.
   * @param keyStroke the {@code KeyStroke} corresponding to the given character.
   */
  public KeyStrokeMapping(char character, @Nonnull KeyStroke keyStroke) {
    this.character = character;
    this.keyStroke = keyStroke;
  }

  /**
   * @return the character corresponding to this mapping's {@code KeyStroke}.
   */
  public char character() {
    return character;
  }

  /**
   * @return the {@code KeyStroke} corresponding to this mapping's character.
   */
  public @Nonnull KeyStroke keyStroke() {
    return keyStroke;
  }

  /** @see Object#equals(Object) */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (!(o instanceof KeyStrokeMapping)) {
      return false;
    }
    KeyStrokeMapping other = (KeyStrokeMapping) o;
    return character == other.character && keyStroke.getKeyCode() == other.keyStroke.getKeyCode()
        && keyStroke.getModifiers() == other.keyStroke.getModifiers();
  }

  @Override
  public int hashCode() {
    int prime = HASH_CODE_PRIME;
    int result = 1;
    result = prime * result + character;
    if (keyStroke != null) {
      result = prime * result + keyStroke.getKeyCode();
      result = prime * result + keyStroke.getModifiers();
    }
    return result;
  }

  @Override
  public String toString() {
    String format = "%s[character='%s', keyStroke=%s]";
    return String.format(format, getClass().getSimpleName(), String.valueOf(character), keyStroke.toString());
  }
}
