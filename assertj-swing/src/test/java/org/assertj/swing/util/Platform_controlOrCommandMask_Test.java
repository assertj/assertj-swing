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
package org.assertj.swing.util;

import static java.awt.Event.CTRL_MASK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;

/**
 * Tests for {@link Platform#controlOrCommandMask()}.
 * 
 * @author Alex Ruiz
 */
public class Platform_controlOrCommandMask_Test extends Platform_TestCase {
  @Test
  public void should_Return_Control_Or_Command_Mask() {
    when(toolkit.getMenuShortcutKeyMask()).thenReturn(CTRL_MASK);
    assertThat(Platform.controlOrCommandMask()).isEqualTo(CTRL_MASK);
  }
}
