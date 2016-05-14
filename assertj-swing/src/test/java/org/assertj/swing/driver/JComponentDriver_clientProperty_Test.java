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
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.JComponent;

import org.assertj.swing.annotation.RunsInEDT;
import org.junit.Test;

/**
 * Tests for {@link JComponentDriver#clientProperty(JComponent, Object)}.
 * 
 * @author Alex Ruiz
 */
public class JComponentDriver_clientProperty_Test extends JComponentDriver_TestCase {
  @Test
  public void should_Return_Client_Property_Under_Given_Key() {
    putClientProperty(button, "name", "Leia");
    assertThat(driver.clientProperty(button, "name")).isEqualTo("Leia");
  }

  @Test
  public void should_Return_Null_If_Client_Property_Not_Found() {
    assertThat(driver.clientProperty(button, "name")).isNull();
  }

  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Key_Is_Null() {
    driver.clientProperty(button, null);
  }

  @RunsInEDT
  private static void putClientProperty(final JComponent c, final Object key, final Object value) {
    execute(() -> c.putClientProperty(key, value));
  }
}
