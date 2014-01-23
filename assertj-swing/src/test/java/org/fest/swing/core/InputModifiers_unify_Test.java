/*
 * Created on Aug 5, 2009
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
package org.fest.swing.core;

import static java.awt.event.InputEvent.ALT_GRAPH_MASK;
import static java.awt.event.InputEvent.ALT_MASK;
import static java.awt.event.InputEvent.CTRL_MASK;
import static java.awt.event.InputEvent.META_MASK;
import static java.awt.event.InputEvent.SHIFT_MASK;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link InputModifiers}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class InputModifiers_unify_Test {
  private final int[] modifiers;

  @Parameters
  public static Collection<Object[]> modifiers() {
    return newArrayList(new Object[][] {
        { new int[] { ALT_MASK, ALT_GRAPH_MASK, CTRL_MASK, META_MASK, SHIFT_MASK } },
        { new int[] { ALT_MASK, ALT_GRAPH_MASK, CTRL_MASK, META_MASK } },
        { new int[] { ALT_MASK, ALT_GRAPH_MASK, CTRL_MASK } }, { new int[] { ALT_MASK, ALT_GRAPH_MASK } },
        { new int[] { ALT_MASK } }
      });
  }

  public InputModifiers_unify_Test(int[] modifiers) {
    this.modifiers = modifiers;
  }

  @Test
  public void should_unify_modifiers() {
    int unified = InputModifiers.unify(modifiers);
    // asserts that contains only the passed modifiers
    for (int modifier : modifiers) {
      assertThat((unified & modifier) != 0).isTrue();
    }
    // asserts that does not contain modifiers that were not passed
    for (int modifier : missingModifiersIn(modifiers)) {
      assertThat((unified & modifier) != 0).isFalse();
    }
  }

  private List<Integer> missingModifiersIn(int[] existingModifiers) {
    List<Integer> allModifiers = allModifiers();
    for (Integer modifier : existingModifiers) {
      allModifiers.remove(modifier);
    }
    return allModifiers;
  }

  private List<Integer> allModifiers() {
    return newArrayList(ALT_MASK, ALT_GRAPH_MASK, CTRL_MASK, META_MASK, SHIFT_MASK);
  }
}
