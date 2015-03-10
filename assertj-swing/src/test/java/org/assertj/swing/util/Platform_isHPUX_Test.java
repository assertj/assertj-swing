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
import static org.assertj.swing.util.OSIdentifierStub.hpUX;
import static org.assertj.swing.util.OSIdentifierStub.windowsXP;

import org.junit.Test;

/**
 * Tests for {@link Platform#isHPUX()}.
 * 
 * @author Alex Ruiz
 */
public class Platform_isHPUX_Test extends Platform_TestCase {
  @Test
  public void should_Return_True_If_OS_Is_HPUX() {
    Platform.initialize(hpUX(), toolkit);
    assertThat(Platform.isHPUX()).isTrue();
  }

  @Test
  public void should_Return_False_If_OS_Is_Not_HPUX() {
    Platform.initialize(windowsXP(), toolkit);
    assertThat(Platform.isHPUX()).isFalse();
  }
}
