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
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_ALT;
import static java.awt.event.KeyEvent.VK_ALT_GRAPH;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_META;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link Modifiers#maskFor(int)}.
 * 
 * @author Alex Ruiz
 */
public class Modifiers_maskFor_Test {
  @Test
  public void should_return_mask_for_AltGraph_modifier() {
    assertThat(Modifiers.maskFor(VK_ALT_GRAPH)).isEqualTo(ALT_GRAPH_MASK);
  }

  @Test
  public void should_return_mask_for_Alt_modifier() {
    assertThat(Modifiers.maskFor(VK_ALT)).isEqualTo(ALT_MASK);
  }

  @Test
  public void should_return_mask_for_Shift_modifier() {
    assertThat(Modifiers.maskFor(VK_SHIFT)).isEqualTo(SHIFT_MASK);
  }

  @Test
  public void should_return_mask_for_Control_modifier() {
    assertThat(Modifiers.maskFor(VK_CONTROL)).isEqualTo(CTRL_MASK);
  }

  @Test
  public void should_return_mask_for_Meta_modifier() {
    assertThat(Modifiers.maskFor(VK_META)).isEqualTo(META_MASK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_throw_error_if_key_is_not_modifier() {
    Modifiers.maskFor(VK_A);
  }
}
