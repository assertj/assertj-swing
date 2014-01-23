/*
 * Created on Jul 19, 2008
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

import static java.awt.event.InputEvent.ALT_GRAPH_MASK;
import static java.awt.event.InputEvent.ALT_MASK;
import static java.awt.event.InputEvent.CTRL_MASK;
import static java.awt.event.InputEvent.META_MASK;
import static java.awt.event.InputEvent.SHIFT_MASK;

import java.awt.event.InputEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Utility methods related to input modifiers.
 *
 * @author Alex Ruiz
 */
final class InputModifiers {
  static int unify(@Nullable int... modifiers) {
    if (modifiers == null) {
      return 0;
    }
    int unified = 0;
    if (modifiers.length > 0) {
      unified = modifiers[0];
      for (int i = 1; i < modifiers.length; i++) {
        unified |= modifiers[i];
      }
    }
    return unified;
  }

  static boolean isShiftDown(int modifiers) {
    return (modifiers & SHIFT_MASK) != 0;
  }

  static boolean isControlDown(int modifiers) {
    return (modifiers & CTRL_MASK) != 0;
  }

  static boolean isMetaDown(int modifiers) {
    return (modifiers & META_MASK) != 0;
  }

  static boolean isAltDown(int modifiers) {
    return (modifiers & ALT_MASK) != 0;
  }

  static boolean isAltGraphDown(int modifiers) {
    return (modifiers & ALT_GRAPH_MASK) != 0;
  }

  static boolean modifiersMatch(@Nonnull InputEvent e, int modifiers) {
    if (e.isAltDown() != isAltDown(modifiers)) {
      return false;
    }
    if (e.isAltGraphDown() != isAltGraphDown(modifiers)) {
      return false;
    }
    if (e.isControlDown() != isControlDown(modifiers)) {
      return false;
    }
    if (e.isMetaDown() != isMetaDown(modifiers)) {
      return false;
    }
    if (e.isShiftDown() != isShiftDown(modifiers)) {
      return false;
    }
    return true;
  }

  private InputModifiers() {}
}
