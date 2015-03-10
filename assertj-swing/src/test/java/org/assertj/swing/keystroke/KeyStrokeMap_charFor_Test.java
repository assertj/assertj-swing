/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.keystroke;

import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.CHAR_UNDEFINED;
import static java.awt.event.KeyEvent.VK_A;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.swing.KeyStroke;

import org.junit.Test;

/**
 * Tests for {@link KeyStrokeMap#charFor(javax.swing.KeyStroke)}.
 * 
 * @author Alex Ruiz
 */
public class KeyStrokeMap_charFor_Test extends KeyStrokeMap_TestCase {
  @Test
  public void should_Return_Char_For_KeyStroke() {
    when(provider.keyStrokeMappings()).thenReturn(mappings);
    KeyStrokeMap.addKeyStrokesFrom(provider);
    assertThat(KeyStrokeMap.charFor(keyStroke)).isEqualTo('A');
  }

  @Test
  public void should_Strip_Modifiers_Except_Shift_If_Char_For_KeyStroke_Not_Found() {
    Character character = 'a';
    KeyStrokeMapCollection maps = mock(KeyStrokeMapCollection.class);
    KeyStrokeMap.updateKeyStrokeMapCollection(maps);
    when(maps.charFor(keyStroke)).thenReturn(null);
    when(maps.charFor(removeModifiersExceptShift(keyStroke))).thenReturn(character);
    assertThat(KeyStrokeMap.charFor(keyStroke)).isEqualTo(character);
  }

  @Test
  public void should_Return_Undefined_Character_If_Char_For_KeyStroke_Not_Found() {
    KeyStrokeMapCollection maps = mock(KeyStrokeMapCollection.class);
    KeyStrokeMap.updateKeyStrokeMapCollection(maps);
    when(maps.charFor(keyStroke)).thenReturn(null);
    when(maps.charFor(removeModifiersExceptShift(keyStroke))).thenReturn(null);
    assertThat(KeyStrokeMap.charFor(keyStroke)).isEqualTo(CHAR_UNDEFINED);
  }

  private static KeyStroke removeModifiersExceptShift(KeyStroke base) {
    final int mask = base.getModifiers() & ~SHIFT_MASK;
    return KeyStroke.getKeyStroke(VK_A, mask);
  }
}
