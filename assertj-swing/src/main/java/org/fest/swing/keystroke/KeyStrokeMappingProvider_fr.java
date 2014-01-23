/*
 * Created on Nov 16, 2009
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

import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.VK_0;
import static java.awt.event.KeyEvent.VK_1;
import static java.awt.event.KeyEvent.VK_2;
import static java.awt.event.KeyEvent.VK_3;
import static java.awt.event.KeyEvent.VK_4;
import static java.awt.event.KeyEvent.VK_5;
import static java.awt.event.KeyEvent.VK_6;
import static java.awt.event.KeyEvent.VK_7;
import static java.awt.event.KeyEvent.VK_8;
import static java.awt.event.KeyEvent.VK_9;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_B;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_DELETE;
import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_EQUALS;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_F;
import static java.awt.event.KeyEvent.VK_G;
import static java.awt.event.KeyEvent.VK_H;
import static java.awt.event.KeyEvent.VK_I;
import static java.awt.event.KeyEvent.VK_J;
import static java.awt.event.KeyEvent.VK_K;
import static java.awt.event.KeyEvent.VK_L;
import static java.awt.event.KeyEvent.VK_M;
import static java.awt.event.KeyEvent.VK_N;
import static java.awt.event.KeyEvent.VK_O;
import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_Q;
import static java.awt.event.KeyEvent.VK_R;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_T;
import static java.awt.event.KeyEvent.VK_U;
import static java.awt.event.KeyEvent.VK_V;
import static java.awt.event.KeyEvent.VK_W;
import static java.awt.event.KeyEvent.VK_X;
import static java.awt.event.KeyEvent.VK_Y;
import static java.awt.event.KeyEvent.VK_Z;
import static org.fest.swing.keystroke.KeyStrokeMapping.mapping;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;

import org.fest.util.Lists;

/**
 * Mapping between characters and {@code KeyStroke}s for locale {@code Locale.FRENCH}.
 * 
 * @author Florian SIMON
 * @author Alex Ruiz
 */
public class KeyStrokeMappingProvider_fr implements KeyStrokeMappingProvider {
  /**
   * @return the mapping between characters and {@code KeyStroke}s for locale {@code Locale.FRENCH}.
   */
  @Override
  public @Nonnull Collection<KeyStrokeMapping> keyStrokeMappings() {
    return SingletonHolder.instance;
  }

  // Thread-safe, lazy-loading singleton.
  private static class SingletonHolder {
    static List<KeyStrokeMapping> instance = createMappings();
  }

  private static @Nonnull List<KeyStrokeMapping> createMappings() {
    List<KeyStrokeMapping> mappings = Lists.newArrayList();
    mappings.add(mapping('\b', VK_BACK_SPACE, NO_MASK));
    mappings.add(mapping('', VK_DELETE, NO_MASK));
    mappings.add(mapping('', VK_ESCAPE, NO_MASK));
    mappings.add(mapping('\n', VK_ENTER, NO_MASK));
    mappings.add(mapping('\r', VK_ENTER, NO_MASK));
    mappings.add(mapping('&', VK_1, NO_MASK));
    mappings.add(mapping('1', VK_1, SHIFT_MASK));
    mappings.add(mapping('�', VK_2, NO_MASK));
    mappings.add(mapping('2', VK_2, SHIFT_MASK));
    mappings.add(mapping('"', VK_3, NO_MASK));
    mappings.add(mapping('3', VK_3, SHIFT_MASK));
    mappings.add(mapping('\'', VK_4, NO_MASK));
    mappings.add(mapping('4', VK_4, SHIFT_MASK));
    mappings.add(mapping('(', VK_5, NO_MASK));
    mappings.add(mapping('5', VK_5, SHIFT_MASK));
    mappings.add(mapping('-', VK_6, NO_MASK));
    mappings.add(mapping('6', VK_6, SHIFT_MASK));
    mappings.add(mapping('�', VK_7, NO_MASK));
    mappings.add(mapping('7', VK_7, SHIFT_MASK));
    mappings.add(mapping('_', VK_8, NO_MASK));
    mappings.add(mapping('8', VK_8, SHIFT_MASK));
    mappings.add(mapping('�', VK_9, NO_MASK));
    mappings.add(mapping('9', VK_9, SHIFT_MASK));
    mappings.add(mapping('�', VK_0, NO_MASK));
    mappings.add(mapping('0', VK_0, SHIFT_MASK));
    mappings.add(mapping('=', VK_EQUALS, NO_MASK));
    mappings.add(mapping('+', VK_EQUALS, SHIFT_MASK));
    mappings.add(mapping('a', VK_A, NO_MASK));
    mappings.add(mapping('A', VK_A, SHIFT_MASK));
    mappings.add(mapping('b', VK_B, NO_MASK));
    mappings.add(mapping('B', VK_B, SHIFT_MASK));
    mappings.add(mapping('c', VK_C, NO_MASK));
    mappings.add(mapping('C', VK_C, SHIFT_MASK));
    mappings.add(mapping('d', VK_D, NO_MASK));
    mappings.add(mapping('D', VK_D, SHIFT_MASK));
    mappings.add(mapping('e', VK_E, NO_MASK));
    mappings.add(mapping('E', VK_E, SHIFT_MASK));
    mappings.add(mapping('f', VK_F, NO_MASK));
    mappings.add(mapping('F', VK_F, SHIFT_MASK));
    mappings.add(mapping('g', VK_G, NO_MASK));
    mappings.add(mapping('G', VK_G, SHIFT_MASK));
    mappings.add(mapping('h', VK_H, NO_MASK));
    mappings.add(mapping('H', VK_H, SHIFT_MASK));
    mappings.add(mapping('i', VK_I, NO_MASK));
    mappings.add(mapping('I', VK_I, SHIFT_MASK));
    mappings.add(mapping('j', VK_J, NO_MASK));
    mappings.add(mapping('J', VK_J, SHIFT_MASK));
    mappings.add(mapping('k', VK_K, NO_MASK));
    mappings.add(mapping('K', VK_K, SHIFT_MASK));
    mappings.add(mapping('l', VK_L, NO_MASK));
    mappings.add(mapping('L', VK_L, SHIFT_MASK));
    mappings.add(mapping('m', VK_M, NO_MASK));
    mappings.add(mapping('M', VK_M, SHIFT_MASK));
    mappings.add(mapping('n', VK_N, NO_MASK));
    mappings.add(mapping('N', VK_N, SHIFT_MASK));
    mappings.add(mapping('o', VK_O, NO_MASK));
    mappings.add(mapping('O', VK_O, SHIFT_MASK));
    mappings.add(mapping('p', VK_P, NO_MASK));
    mappings.add(mapping('P', VK_P, SHIFT_MASK));
    mappings.add(mapping('q', VK_Q, NO_MASK));
    mappings.add(mapping('Q', VK_Q, SHIFT_MASK));
    mappings.add(mapping('r', VK_R, NO_MASK));
    mappings.add(mapping('R', VK_R, SHIFT_MASK));
    mappings.add(mapping('s', VK_S, NO_MASK));
    mappings.add(mapping('S', VK_S, SHIFT_MASK));
    mappings.add(mapping('t', VK_T, NO_MASK));
    mappings.add(mapping('T', VK_T, SHIFT_MASK));
    mappings.add(mapping('u', VK_U, NO_MASK));
    mappings.add(mapping('U', VK_U, SHIFT_MASK));
    mappings.add(mapping('v', VK_V, NO_MASK));
    mappings.add(mapping('V', VK_V, SHIFT_MASK));
    mappings.add(mapping('w', VK_W, NO_MASK));
    mappings.add(mapping('W', VK_W, SHIFT_MASK));
    mappings.add(mapping('x', VK_X, NO_MASK));
    mappings.add(mapping('X', VK_X, SHIFT_MASK));
    mappings.add(mapping('y', VK_Y, NO_MASK));
    mappings.add(mapping('Y', VK_Y, SHIFT_MASK));
    mappings.add(mapping('z', VK_Z, NO_MASK));
    mappings.add(mapping('Z', VK_Z, SHIFT_MASK));
    return mappings;
  }
}
