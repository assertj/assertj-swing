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
import static org.assertj.swing.util.OSIdentifierStub.linux;
import static org.assertj.swing.util.OSIdentifierStub.osX;
import static org.assertj.swing.util.OSIdentifierStub.windows9x;

import org.junit.Test;

/**
 * Tests for {@link Platform#canResizeWindows()}.
 * 
 * @author Alex Ruiz
 */
public class Platform_canResizeWindows_Test extends Platform_TestCase {
  @Test
  public void should_Return_Cannot_Resize_Windows_If_OS_Is_Windows() {
    Platform.initialize(windows9x(), toolkit);
    assertThat(Platform.canResizeWindows()).isEqualTo(false);
  }

  @Test
  public void should_Return_Cannot_Resize_Windows_If_OS_Is_OSX() {
    Platform.initialize(osX(), toolkit);
    assertThat(Platform.canResizeWindows()).isEqualTo(false);
  }

  @Test
  public void should_Return_Can_Resize_Windows_If_OS_Is_Not_Windows_Or_OSX() {
    Platform.initialize(linux(), toolkit);
    assertThat(Platform.canResizeWindows()).isEqualTo(true);
  }
}
