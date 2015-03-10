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
package org.assertj.swing.util;

import static java.awt.event.InputEvent.ALT_GRAPH_MASK;
import static java.awt.event.InputEvent.ALT_MASK;
import static java.awt.event.InputEvent.CTRL_MASK;
import static java.awt.event.InputEvent.META_MASK;
import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_ALT;
import static java.awt.event.KeyEvent.VK_ALT_GRAPH;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_META;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link Modifiers#updateModifierWithKeyCode(int, int)}.
 * 
 * @author Alex Ruiz
 */
public class Modifiers_updateModifierWithKeyCode_Test {
  @Test
  public void should_Update_Modifier_If_KeyCode_Is_Alt() {
    int modifierMask = 0;
    int updatedModifierMask = Modifiers.updateModifierWithKeyCode(VK_ALT, modifierMask);
    assertThat(updatedModifierMask).isNotEqualTo(modifierMask);
    assertThat(updatedModifierMask & ALT_MASK).isNotEqualTo(0);
  }

  @Test
  public void should_Update_Modifier_If_KeyCode_Is_AltGraph() {
    int modifierMask = 0;
    int updatedModifierMask = Modifiers.updateModifierWithKeyCode(VK_ALT_GRAPH, modifierMask);
    assertThat(updatedModifierMask).isNotEqualTo(modifierMask);
    assertThat(updatedModifierMask & ALT_GRAPH_MASK).isNotEqualTo(0);
  }

  @Test
  public void should_Update_Modifier_If_KeyCode_Is_Ctrl() {
    int modifierMask = 0;
    int updatedModifierMask = Modifiers.updateModifierWithKeyCode(VK_CONTROL, modifierMask);
    assertThat(updatedModifierMask).isNotEqualTo(modifierMask);
    assertThat(updatedModifierMask & CTRL_MASK).isNotEqualTo(0);
  }

  @Test
  public void should_Update_Modifier_If_KeyCode_Is_Meta() {
    int modifierMask = 0;
    int updatedModifierMask = Modifiers.updateModifierWithKeyCode(VK_META, modifierMask);
    assertThat(updatedModifierMask).isNotEqualTo(modifierMask);
    assertThat(updatedModifierMask & META_MASK).isNotEqualTo(0);
  }

  @Test
  public void should_Update_Modifier_If_KeyCode_Is_Shift() {
    int modifierMask = 0;
    int updatedModifierMask = Modifiers.updateModifierWithKeyCode(VK_SHIFT, modifierMask);
    assertThat(updatedModifierMask).isNotEqualTo(modifierMask);
    assertThat(updatedModifierMask & SHIFT_MASK).isNotEqualTo(0);
  }

  @Test
  public void should_Not_Update_Modifier_If_KeyCode_Is_Not_Modifier() {
    int modifierMask = 0;
    int updatedModifierMask = Modifiers.updateModifierWithKeyCode(VK_A, modifierMask);
    assertThat(updatedModifierMask).isEqualTo(modifierMask);
    assertThat(updatedModifierMask & ALT_MASK).isEqualTo(0);
    assertThat(updatedModifierMask & ALT_GRAPH_MASK).isEqualTo(0);
    assertThat(updatedModifierMask & CTRL_MASK).isEqualTo(0);
    assertThat(updatedModifierMask & META_MASK).isEqualTo(0);
    assertThat(updatedModifierMask & SHIFT_MASK).isEqualTo(0);
  }
}
