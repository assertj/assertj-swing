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
package org.assertj.swing.monitor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

/**
 * Tests for {@link WindowMonitor#isWindowReady(java.awt.Window)}.
 * 
 * @author Alex Ruiz
 */
public class WindowMonitor_isWindowReady_Test extends WindowMonitor_TestCase {
  @Test
  public void should_Return_True_If_Windows_Indicates_That_Window_Is_Ready() {
    when(windows.isReady(frame)).thenReturn(true);
    assertThat(monitor.isWindowReady(frame)).isTrue();
  }

  @Test
  public void should_Check_With_WindowStatus_If_Window_Is_Ready_When_Windows_Indicates_It_Is_Not_Ready() {
    when(windows.isReady(frame)).thenReturn(false);
    assertThat(monitor.isWindowReady(frame)).isFalse();
    verify(windowStatus).checkIfReady(frame);
  }
}
