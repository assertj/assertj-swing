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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.util.OSFamily.WINDOWS;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link OSIdentifier#isWindowsXP()}.
 * 
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class OSIdentifier_isWindowsXP_Test extends OSIdentifier_TestCase {
  private final String windowsXP;

  @Parameters
  public static Collection<Object[]> windowsXP() {
    return newArrayList(new Object[][] { { "windowsxp" }, { "WindowsXP" }, { "WINDOWSXP" } });
  }

  public OSIdentifier_isWindowsXP_Test(String windowsXP) {
    this.windowsXP = windowsXP;
  }

  @Test
  public void should_Return_WindowsXP_If_OS_Name_Starts_With_Windows_And_Contains_XP() {
    returnOSName(windowsXP);
    OSIdentifier osIdentifier = new OSIdentifier(propertyReader);
    assertThat(osIdentifier.isWindows()).isTrue();
    assertThat(osIdentifier.isWindowsXP()).isTrue();
    assertThat(osIdentifier.isHPUX()).isFalse();
    assertThat(osIdentifier.isLinux()).isFalse();
    assertThat(osIdentifier.isMacintosh()).isFalse();
    assertThat(osIdentifier.isOSX()).isFalse();
    assertThat(osIdentifier.isSolaris()).isFalse();
    assertThat(osIdentifier.isWindows9x()).isFalse();
    assertThat(osIdentifier.isX11()).isFalse();
    assertThat(osIdentifier.osFamily()).isEqualTo(WINDOWS);
  }
}
