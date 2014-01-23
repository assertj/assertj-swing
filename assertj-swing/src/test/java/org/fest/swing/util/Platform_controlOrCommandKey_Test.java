/*
 * Created on Jul 31, 2009
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

import static java.awt.event.InputEvent.CTRL_MASK;
import static java.awt.event.InputEvent.META_MASK;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_META;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Lists.newArrayList;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link Platform#controlOrCommandKey()}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class Platform_controlOrCommandKey_Test extends Platform_TestCase {
  private final int mask;
  private final int keyCode;

  @Parameters
  public static Collection<Object[]> booleans() {
    return newArrayList(new Object[][] {
        { CTRL_MASK, VK_CONTROL },
        { META_MASK, VK_META }
      });
  }

  public Platform_controlOrCommandKey_Test(int mask, int keyCode) {
    this.mask = mask;
    this.keyCode = keyCode;
  }

  @Test
  public void should_return_control_or_command_key() {
    when(toolkit.getMenuShortcutKeyMask()).thenReturn(mask);
    assertThat(Platform.controlOrCommandKey()).isEqualTo(keyCode);
  }
}
