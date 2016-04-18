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
package org.assertj.swing.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link InputModifiers#unify(int...)}.
 * 
 * @author Alex Ruiz
 */
public class InputModifiers_unify_withNoModifiers_Test {
  @Test
  public void should_Return_Zero_If_Array_Of_Modifiers_Is_Empty() {
    assertThat(InputModifiers.unify(new int[0])).isZero();
  }

  @Test
  public void should_Return_Zero_If_Array_Of_Modifiers_Is_Null() {
    int[] modifiers = null;
    assertThat(InputModifiers.unify(modifiers)).isZero();
  }
}
