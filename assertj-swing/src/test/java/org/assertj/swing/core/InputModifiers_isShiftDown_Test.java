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

import static java.awt.event.InputEvent.ALT_MASK;
import static java.awt.event.InputEvent.SHIFT_MASK;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link InputModifiers#isShiftDown(int)}.
 * 
 * @author Alex Ruiz
 */
public class InputModifiers_isShiftDown_Test {
  @Test
  public void should_Return_True_If_Shift_Mask_Is_Present() {
    assertThat(InputModifiers.isShiftDown(SHIFT_MASK | ALT_MASK)).isTrue();
  }

  @Test
  public void should_Return_False_If_Shift_Mask_Is_Not_Present() {
    assertThat(InputModifiers.isShiftDown(ALT_MASK)).isFalse();
  }
}
