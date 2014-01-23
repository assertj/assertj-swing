/*
 * Created on Jul 30, 2009
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
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link Modifiers#keysFor(int)}.
 * 
 * @author Alex Ruiz
 */
public class Modifiers_keysFor_Test {
  @Test
  public void should_return_key_for_Alt_mask() {
    int[] keys = Modifiers.keysFor(ALT_MASK);
    assertThat(keys).hasSize(1).containsOnly(VK_ALT);
  }

  @Test
  public void should_return_key_for_AltGraph_mask() {
    int[] keys = Modifiers.keysFor(ALT_GRAPH_MASK);
    assertThat(keys).hasSize(1).containsOnly(VK_ALT_GRAPH);
  }

  @Test
  public void should_return_key_for_Ctrl_mask() {
    int[] keys = Modifiers.keysFor(CTRL_MASK);
    assertThat(keys).hasSize(1).containsOnly(VK_CONTROL);
  }

  @Test
  public void should_return_key_for_Meta_mask() {
    int[] keys = Modifiers.keysFor(META_MASK);
    assertThat(keys).hasSize(1).containsOnly(VK_META);
  }

  @Test
  public void should_return_key_for_Shift_mask() {
    int[] keys = Modifiers.keysFor(SHIFT_MASK);
    assertThat(keys).hasSize(1).containsOnly(VK_SHIFT);
  }

  @Test
  public void should_return_key_for__more_than_one_mask() {
    int[] keys = Modifiers.keysFor(ALT_MASK | ALT_GRAPH_MASK | CTRL_MASK | META_MASK | SHIFT_MASK);
    assertThat(keys).hasSize(5).containsOnly(VK_ALT, VK_ALT_GRAPH, VK_CONTROL, VK_META, VK_SHIFT);
  }

  @Test
  public void shouldNotReturnKeyIs_mask_is_zero() {
    int[] keys = Modifiers.keysFor(0);
    assertThat(keys).isEmpty();
  }
}
