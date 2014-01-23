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

import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.CHAR_UNDEFINED;
import static org.fest.swing.util.Platform.osFamily;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.KeyStroke;

import org.fest.util.VisibleForTesting;

/**
 * A collection of {@link KeyStrokeMapping}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class KeyStrokeMap {
  private static KeyStrokeMapCollection maps = new KeyStrokeMapCollection();

  static {
    reloadFromSystemSettings();
  }

  /**
   * Reloads the key stroke mappings for the language using the current system settings.
   * 
   * @since 1.2
   */
  public static void reloadFromSystemSettings() {
    KeyStrokeMappingProviderPicker picker = new KeyStrokeMappingProviderPicker();
    maps.clear();
    addKeyStrokesFrom(picker.providerFor(osFamily(), Locale.getDefault()));
  }

  @VisibleForTesting
  static void updateKeyStrokeMapCollection(@Nonnull KeyStrokeMapCollection c) {
    maps = c;
  }

  /**
   * Adds the collection of {@link KeyStrokeMapping}s from the given {@link KeyStrokeMappingProvider} to this map.
   * 
   * @param provider the given {@code KeyStrokeMappingProvider}.
   */
  public static void addKeyStrokesFrom(@Nonnull KeyStrokeMappingProvider provider) {
    for (KeyStrokeMapping entry : provider.keyStrokeMappings()) {
      add(entry.character(), entry.keyStroke());
    }
  }

  private static void add(@Nonnull Character character, @Nonnull KeyStroke keyStroke) {
    maps.add(character, keyStroke);
  }

  /**
   * Removes all the character-{@code KeyStroke} mappings.
   */
  public static void clearKeyStrokes() {
    maps.clear();
  }

  /**
   * Indicates whether {@link KeyStrokeMap} has mappings.
   * 
   * @return {@code true} if it has mappings, {@code false} otherwise.
   */
  public static boolean hasKeyStrokes() {
    return !maps.isEmpty();
  }

  /**
   * Returns the {@code KeyStroke} corresponding to the given character, as best we can guess it, or {@code null} if we
   * don't know how to generate it.
   * 
   * @param character the given character.
   * @return the key code-based {@code KeyStroke} corresponding to the given character, or {@code null} if we cannot
   *         generate it.
   */
  public static @Nullable KeyStroke keyStrokeFor(char character) {
    return maps.keyStrokeFor(character);
  }

  /**
   * Given a {@link KeyStroke}, returns the equivalent character. Key strokes are defined properly for US keyboards
   * only. To contribute your own, please add them using the method
   * {@link #addKeyStrokesFrom(KeyStrokeMappingProvider)}.
   * 
   * @param keyStroke the given {@code KeyStroke}.
   * @return {@code KeyEvent.VK_UNDEFINED} if the result is unknown.
   */
  public static char charFor(@Nonnull KeyStroke keyStroke) {
    Character character = maps.charFor(keyStroke);
    // Try again, but strip all modifiers but shift
    if (character == null) {
      character = charWithoutModifiersButShift(keyStroke);
    }
    if (character == null) {
      return CHAR_UNDEFINED;
    }
    return character;
  }

  private static @Nullable Character charWithoutModifiersButShift(@Nonnull KeyStroke keyStroke) {
    int mask = keyStroke.getModifiers() & ~SHIFT_MASK;
    return maps.charFor(KeyStroke.getKeyStroke(keyStroke.getKeyCode(), mask));
  }

  private KeyStrokeMap() {}
}
