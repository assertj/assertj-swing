/*
 * Created on Jul 30, 2009
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.util.OSFamily.UNIX;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link OSIdentifier#isWindows()}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class OSIdentifier_isHPUX_Test extends OSIdentifier_TestCase {
  private final String hpUX;

  @Parameters
  public static Collection<Object[]> hpUX() {
    return newArrayList(new Object[][] {
        { "hp-ux" }, { "HP-UX" }, { "Hp-Ux" }
      });
  }

  public OSIdentifier_isHPUX_Test(String hpUX) {
    this.hpUX = hpUX;
  }

  @Test
  public void should_return_HPUX_if_OS_name_is_equal_to_HPUX() {
    returnOSName(hpUX);
    returnNoMRJVersion();
    OSIdentifier osIdentifier = new OSIdentifier(propertyReader);
    assertThat(osIdentifier.isHPUX()).isTrue();
    assertThat(osIdentifier.isX11()).isTrue();
    assertThat(osIdentifier.isLinux()).isFalse();
    assertThat(osIdentifier.isMacintosh()).isFalse();
    assertThat(osIdentifier.isOSX()).isFalse();
    assertThat(osIdentifier.isSolaris()).isFalse();
    assertThat(osIdentifier.isWindows()).isFalse();
    assertThat(osIdentifier.isWindows9x()).isFalse();
    assertThat(osIdentifier.isWindowsXP()).isFalse();
    assertThat(osIdentifier.osFamily()).isEqualTo(UNIX);
  }
}
