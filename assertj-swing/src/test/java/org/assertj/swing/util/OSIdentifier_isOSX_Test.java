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
import static org.assertj.swing.util.OSFamily.MAC;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link OSIdentifier#isOSX()}.
 * 
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class OSIdentifier_isOSX_Test extends OSIdentifier_TestCase {
  private final String osX;

  @Parameters
  public static Collection<Object[]> osX() {
    return newArrayList(new Object[][] { { "os x" }, { "OS X" } });
  }

  public OSIdentifier_isOSX_Test(String osX) {
    this.osX = osX;
  }

  @Test
  public void should_Return_OSX_If_MRJVersion_Not_Null_And_OS_Name_Contains_OSX() {
    returnOSName(osX);
    OSIdentifier osIdentifier = new OSIdentifier(propertyReader);
    assertThat(osIdentifier.isMacintosh()).isTrue();
    assertThat(osIdentifier.isOSX()).isTrue();
    assertThat(osIdentifier.isHPUX()).isFalse();
    assertThat(osIdentifier.isLinux()).isFalse();
    assertThat(osIdentifier.isSolaris()).isFalse();
    assertThat(osIdentifier.isWindows()).isFalse();
    assertThat(osIdentifier.isWindows9x()).isFalse();
    assertThat(osIdentifier.isWindowsXP()).isFalse();
    assertThat(osIdentifier.isX11()).isFalse();
    assertThat(osIdentifier.osFamily()).isEqualTo(MAC);
  }
}
