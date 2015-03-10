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
import static java.awt.event.KeyEvent.VK_A;
import static javax.swing.KeyStroke.getKeyStroke;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link KeyStrokeMapping#mapping(char, int, int)}.
 * 
 * @author Alex Ruiz
 */
public class KeyStrokeMapping_mapping_Test {
  @Test
  public void should_Create_Mapping_From_Char_KeyCode_And_Modifiers() {
    KeyStrokeMapping mapping = KeyStrokeMapping.mapping('A', VK_A, SHIFT_MASK);
    assertThat(mapping.character()).isEqualTo('A');
    assertThat(mapping.keyStroke()).isEqualTo(getKeyStroke(VK_A, SHIFT_MASK));
  }
}
