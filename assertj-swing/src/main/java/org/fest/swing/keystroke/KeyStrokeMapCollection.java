/*
 * Created on May 21, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.keystroke;

import static org.fest.util.Maps.newHashMap;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.KeyStroke;

/**
 * Mapping between characters and {@code KeyStroke}s.
 * 
 * @author Alex Ruiz
 */
class KeyStrokeMapCollection {
  private final Map<Character, KeyStroke> charToKeyStroke = newHashMap();
  private final Map<KeyStroke, Character> keyStrokeToChar = newHashMap();

  void add(@Nonnull Character character, @Nonnull KeyStroke keyStroke) {
    charToKeyStroke.put(character, keyStroke);
    keyStrokeToChar.put(keyStroke, character);
  }

  void clear() {
    charToKeyStroke.clear();
    keyStrokeToChar.clear();
  }

  boolean isEmpty() {
    return charToKeyStroke.isEmpty() && keyStrokeToChar.isEmpty();
  }

  @Nullable KeyStroke keyStrokeFor(char character) {
    return charToKeyStroke.get(character);
  }

  @Nullable Character charFor(KeyStroke keyStroke) {
    return keyStrokeToChar.get(keyStroke);
  }
}
