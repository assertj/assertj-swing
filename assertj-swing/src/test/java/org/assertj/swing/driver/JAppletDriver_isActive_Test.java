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
import static org.assertj.core.util.Lists.newArrayList;

import java.util.Collection;

import javax.swing.JApplet;

import org.assertj.swing.test.data.BooleanProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JAppletDriver#isActive(JApplet)}.
 * 
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class JAppletDriver_isActive_Test extends JAppletDriver_TestCase {
  private final boolean active;

  @Parameters
  public static Collection<Object[]> booleans() {
    return newArrayList(BooleanProvider.booleans());
  }

  public JAppletDriver_isActive_Test(boolean active) {
    this.active = active;
  }

  @Test
  public void should_Return_Is_Active() {
    applet().setActive(active);
    boolean result = driver().isActive(applet());
    assertThat(result).isEqualTo(active);
    assertThat(applet().wasMethodCalledInEDT("isActive")).isTrue();
  }
}
