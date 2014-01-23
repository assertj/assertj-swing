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
import static org.fest.swing.util.OSFamily.WINDOWS;
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
public class OSIdentifier_isWindows9x_Test extends OSIdentifier_TestCase {
  private final String windows9x;

  @Parameters
  public static Collection<Object[]> windows9x() {
    return newArrayList(new Object[][] {
        { "windows95" }, { "Windows98" }, { "WINDOWSME" }
      });
  }

  public OSIdentifier_isWindows9x_Test(String windows9x) {
    this.windows9x = windows9x;
  }

  @Test
  public void should_return_is_Windows9x_if_OS_name_starts_with_Windows_and_contains_any_9x_version() {
    returnOSName(windows9x);
    returnNoMRJVersion();
    OSIdentifier osIdentifier = new OSIdentifier(propertyReader);
    assertThat(osIdentifier.isWindows()).isTrue();
    assertThat(osIdentifier.isWindows9x()).isTrue();
    assertThat(osIdentifier.isHPUX()).isFalse();
    assertThat(osIdentifier.isLinux()).isFalse();
    assertThat(osIdentifier.isMacintosh()).isFalse();
    assertThat(osIdentifier.isOSX()).isFalse();
    assertThat(osIdentifier.isSolaris()).isFalse();
    assertThat(osIdentifier.isWindowsXP()).isFalse();
    assertThat(osIdentifier.isX11()).isFalse();
    assertThat(osIdentifier.osFamily()).isEqualTo(WINDOWS);
  }
}
