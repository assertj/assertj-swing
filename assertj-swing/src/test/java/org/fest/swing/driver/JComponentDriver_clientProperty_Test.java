/*
 * Created on Jul 19, 2009
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JComponent;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link JComponentDriver#clientProperty(JComponent, Object)}.
 * 
 * @author Alex Ruiz
 */
public class JComponentDriver_clientProperty_Test extends JComponentDriver_TestCase {
  @Test
  public void should_return_client_property_under_given_key() {
    putClientProperty(button, "name", "Leia");
    assertThat(driver.clientProperty(button, "name")).isEqualTo("Leia");
  }

  @Test
  public void should_return_null_if_client_property_not_found() {
    assertThat(driver.clientProperty(button, "name")).isNull();
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_key_is_null() {
    driver.clientProperty(button, null);
  }

  @RunsInEDT
  private static void putClientProperty(final JComponent c, final Object key, final Object value) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        c.putClientProperty(key, value);
      }
    });
  }
}
