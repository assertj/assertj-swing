/*
 * Created on Jul 27, 2009
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

import static java.awt.event.KeyEvent.VK_A;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.InputModifiers.isAltDown;
import static org.fest.swing.core.InputModifiers.isAltGraphDown;
import static org.fest.swing.core.InputModifiers.isControlDown;
import static org.fest.swing.core.InputModifiers.isMetaDown;
import static org.fest.swing.core.InputModifiers.isShiftDown;
import static org.fest.swing.test.awt.Toolkits.singletonToolkitMock;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link EmergencyAbortListener#EmergencyAbortListener(java.awt.Toolkit)}.
 * 
 * @author Alex Ruiz
 */
public class EmergencyAbortListener_constructor_Test {
  private EmergencyAbortListener listener;

  @Before
  public void setUp() {
    listener = new EmergencyAbortListener(singletonToolkitMock());
  }

  @Test
  public void should_have_CTRL_SHIFT_A_as_default_key_combination() {
    assertThat(listener.keyCode()).isEqualTo(VK_A);
    assertThatModifiersAreCtrlAndShift(listener.modifiers());
  }

  private void assertThatModifiersAreCtrlAndShift(int modifiers) {
    assertThat(isControlDown(modifiers)).isTrue();
    assertThat(isShiftDown(modifiers)).isTrue();
    assertThat(isAltDown(modifiers)).isFalse();
    assertThat(isAltGraphDown(modifiers)).isFalse();
    assertThat(isMetaDown(modifiers)).isFalse();
  }
}
