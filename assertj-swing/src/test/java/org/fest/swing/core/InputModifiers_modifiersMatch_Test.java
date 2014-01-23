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
import static java.awt.event.KeyEvent.VK_A;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.awt.TestComponents.singletonComponentMock;
import static org.fest.util.Lists.newArrayList;

import java.awt.event.KeyEvent;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link InputModifiers#modifiersMatch(java.awt.event.InputEvent, int)}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class InputModifiers_modifiersMatch_Test {
  private final int modifier;

  @Parameters
  public static Collection<Object[]> modifiers() {
    return newArrayList(new Object[][] {
        { ALT_MASK },
        { ALT_GRAPH_MASK },
        { CTRL_MASK },
        { META_MASK },
        { SHIFT_MASK }
      });
  }

  public InputModifiers_modifiersMatch_Test(int modifier) {
    this.modifier = modifier;
  }

  @Test
  public void should_return_true_if_modifiers_match() {
    KeyEvent e = keyEventWithModifiers(modifier);
    assertThat(InputModifiers.modifiersMatch(e, modifier)).isTrue();
  }

  @Test
  public void _return_false_if_modifiers_do_not_match() {
    KeyEvent e = keyEventWithModifiers(0);
    assertThat(InputModifiers.modifiersMatch(e, modifier)).isFalse();
  }

  private static KeyEvent keyEventWithModifiers(int modifiers) {
    return new KeyEvent(singletonComponentMock(), 0, 0, modifiers, VK_A, 'a');
  }
}
