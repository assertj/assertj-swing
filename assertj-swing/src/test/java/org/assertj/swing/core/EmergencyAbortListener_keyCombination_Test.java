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
import static java.awt.event.InputEvent.META_MASK;
import static java.awt.event.KeyEvent.VK_C;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.core.InputModifiers.isAltDown;
import static org.assertj.swing.core.InputModifiers.isAltGraphDown;
import static org.assertj.swing.core.InputModifiers.isControlDown;
import static org.assertj.swing.core.InputModifiers.isMetaDown;
import static org.assertj.swing.core.InputModifiers.isShiftDown;
import static org.assertj.swing.core.KeyPressInfo.keyCode;
import static org.assertj.swing.test.awt.Toolkits.singletonToolkitMock;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link EmergencyAbortListener#keyCombination(KeyPressInfo)}.
 * 
 * @author Alex Ruiz
 */
public class EmergencyAbortListener_keyCombination_Test {
  private EmergencyAbortListener listener;

  @Before
  public void setUp() {
    listener = new EmergencyAbortListener(singletonToolkitMock());
  }

  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_KeyPressInfo_Is_Null() {
    listener.keyCombination(null);
  }

  @Test
  public void should_Update_Key_Combination() {
    listener.keyCombination(keyCode(VK_C).modifiers(ALT_MASK, META_MASK));
    assertThat(listener.keyCode()).isEqualTo(VK_C);
    assertThatModifiersAreAltAndMeta(listener.modifiers());
  }

  private void assertThatModifiersAreAltAndMeta(int modifiers) {
    assertThat(isAltDown(modifiers)).isTrue();
    assertThat(isMetaDown(modifiers)).isTrue();
    assertThat(isAltGraphDown(modifiers)).isFalse();
    assertThat(isControlDown(modifiers)).isFalse();
    assertThat(isShiftDown(modifiers)).isFalse();
  }
}
