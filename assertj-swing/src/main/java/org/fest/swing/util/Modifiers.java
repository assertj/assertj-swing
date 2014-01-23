/*
 * Created on Mar 24, 2008
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
package org.fest.swing.util;

import static java.awt.event.InputEvent.ALT_GRAPH_MASK;
import static java.awt.event.InputEvent.ALT_MASK;
import static java.awt.event.InputEvent.CTRL_MASK;
import static java.awt.event.InputEvent.META_MASK;
import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.VK_ALT;
import static java.awt.event.KeyEvent.VK_ALT_GRAPH;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_META;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.lang.String.valueOf;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Strings.concat;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

/**
 * Utility methods related to input modifiers. This class also maps modifier masks to key codes for the following
 * modifiers:
 * <ul>
 * <li>Alt</li>
 * <li>AltGraph</li>
 * <li>Control</li>
 * <li>Meta</li>
 * <li>Shift</li>
 * </ul>
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class Modifiers {
  private static final Map<Integer, Integer> MODIFIER_TO_KEY = new LinkedHashMap<Integer, Integer>();
  private static final Map<Integer, Integer> KEY_TO_MODIFIER = new LinkedHashMap<Integer, Integer>();

  static {
    MODIFIER_TO_KEY.put(ALT_GRAPH_MASK, VK_ALT_GRAPH);
    KEY_TO_MODIFIER.put(VK_ALT_GRAPH, ALT_GRAPH_MASK);
    MODIFIER_TO_KEY.put(ALT_MASK, VK_ALT);
    KEY_TO_MODIFIER.put(VK_ALT, ALT_MASK);
    MODIFIER_TO_KEY.put(SHIFT_MASK, VK_SHIFT);
    KEY_TO_MODIFIER.put(VK_SHIFT, SHIFT_MASK);
    MODIFIER_TO_KEY.put(CTRL_MASK, VK_CONTROL);
    KEY_TO_MODIFIER.put(VK_CONTROL, CTRL_MASK);
    MODIFIER_TO_KEY.put(META_MASK, VK_META);
    KEY_TO_MODIFIER.put(VK_META, META_MASK);
  }

  /**
   * Returns the key codes for the given modifier mask.
   * 
   * @param modifierMask the given modifier mask.
   * @return the key codes for the given modifier mask.
   */
  public static @Nonnull int[] keysFor(int modifierMask) {
    List<Integer> keyList = newArrayList();
    for (Integer mask : MODIFIER_TO_KEY.keySet()) {
      if ((modifierMask & mask) != 0) {
        keyList.add(MODIFIER_TO_KEY.get(mask));
      }
    }
    int keyCount = keyList.size();
    int[] keys = new int[keyCount];
    for (int i = 0; i < keyCount; i++) {
      keys[i] = keyList.get(i);
    }
    return keys;
  }

  /**
   * Indicates whether the given key code is a modifier.
   * 
   * @param keyCode the given key code.
   * @return {@code true} if the given key code is a modifier, {@code false} otherwise.
   */
  public static boolean isModifier(int keyCode) {
    return KEY_TO_MODIFIER.containsKey(keyCode);
  }

  /**
   * Returns the modifier mask for the given key code.
   * 
   * @param keyCode the given key code.
   * @return the modifier mask for the given key code.
   * @throws IllegalArgumentException if the given key code is not a modifier.
   */
  public static int maskFor(int keyCode) {
    if (!KEY_TO_MODIFIER.containsKey(keyCode)) {
      throw new IllegalArgumentException(concat("Keycode '", valueOf(keyCode), "' is not a modifier"));
    }
    return KEY_TO_MODIFIER.get(keyCode);
  }

  /**
   * Updates the given modifier mask with the given key code, only if the given key code belongs to a modifier key.
   * 
   * @param keyCode the given key code.
   * @param modifierMask the given modifier mask.
   * @return the updated modifier mask.
   */
  public static int updateModifierWithKeyCode(int keyCode, int modifierMask) {
    int updatedModifierMask = modifierMask;
    for (Map.Entry<Integer, Integer> entry : MODIFIER_TO_KEY.entrySet()) {
      int value = entry.getValue();
      if (value != keyCode) {
        continue;
      }
      int key = entry.getKey();
      updatedModifierMask |= key;
      break;
    }
    return updatedModifierMask;
  }

  private Modifiers() {}
}
