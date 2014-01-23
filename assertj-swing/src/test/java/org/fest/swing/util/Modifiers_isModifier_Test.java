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

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_ALT;
import static java.awt.event.KeyEvent.VK_ALT_GRAPH;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_META;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link Modifiers#isModifier(int)}.
 * 
 * @author Alex Ruiz
 */
public class Modifiers_isModifier_Test {
  @Test
  public void should_return_true_if_key_is_AltGraph_modifier() {
    assertThat(Modifiers.isModifier(VK_ALT_GRAPH)).isTrue();
  }

  @Test
  public void should_return_true_if_key_is_Alt_modifier() {
    assertThat(Modifiers.isModifier(VK_ALT)).isTrue();
  }

  @Test
  public void should_return_true_if_key_is_Shift_modifier() {
    assertThat(Modifiers.isModifier(VK_SHIFT)).isTrue();
  }

  @Test
  public void should_return_true_if_key_is_Control_modifier() {
    assertThat(Modifiers.isModifier(VK_CONTROL)).isTrue();
  }

  @Test
  public void should_return_true_if_key_is_Meta_modifier() {
    assertThat(Modifiers.isModifier(VK_META)).isTrue();
  }

  @Test
  public void should_return_false_if_given_key_is_not_modifier() {
    assertThat(Modifiers.isModifier(VK_A)).isFalse();
  }
}
