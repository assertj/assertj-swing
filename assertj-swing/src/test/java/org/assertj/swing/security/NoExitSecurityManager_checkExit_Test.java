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
package org.assertj.swing.security;

import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.test.ExpectedException.none;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.assertj.swing.test.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link NoExitSecurityManager#checkExit(int)}.
 * 
 * @author Alex Ruiz
 */
public class NoExitSecurityManager_checkExit_Test {
  @Rule
  public ExpectedException thrown = none();

  private ExitCallHook hook;
  private StackTraces stackTraces;
  private NoExitSecurityManager securityManager;

  @Before
  public void setUp() {
    hook = mock(ExitCallHook.class);
    stackTraces = mock(StackTraces.class);
    securityManager = new NoExitSecurityManager(hook, stackTraces);
  }

  @Test
  public void should_Call_Hook_And_Throw_Error_If_Runtime_Exit_Was_Called() {
    StackTraceElement[] stackTrace = array(methodInRuntime("exit"));
    when(stackTraces.stackTraceInCurrentThread()).thenReturn(stackTrace);
    thrown.expect(ExitException.class);
    securityManager.checkExit(0);
    verify(hook).exitCalled(0);
  }

  @Test
  public void should_Call_Hook_And_Throw_Error_If_Runtime_Halt_Was_Called() {
    StackTraceElement[] stackTrace = array(methodInRuntime("halt"));
    when(stackTraces.stackTraceInCurrentThread()).thenReturn(stackTrace);
    thrown.expect(ExitException.class);
    securityManager.checkExit(0);
    verify(hook).exitCalled(0);
  }

  @Test
  public void should_Not_Call_Hook_And_Throw_Error_If_Method_Called_Is_In_Runtime_But_Is_Not_Exit_Or_Halt() {
    StackTraceElement[] stackTrace = array(methodInRuntime("availableProcessors"));
    when(stackTraces.stackTraceInCurrentThread()).thenReturn(stackTrace);
    securityManager.checkExit(0);
    verifyZeroInteractions(hook);
  }

  private StackTraceElement methodInRuntime(String methodName) {
    return new StackTraceElement(Runtime.class.getName(), methodName, "Runtime.java", 0);
  }

  @Test
  public void should_Not_Call_Hook_And_Throw_Error_If_Method_Called_Is_Not_Runtime_Exit_Or_Halt() {
    StackTraceElement e = new StackTraceElement(String.class.getName(), "substring", "String.java", 0);
    StackTraceElement[] stackTrace = array(e);
    when(stackTraces.stackTraceInCurrentThread()).thenReturn(stackTrace);
    securityManager.checkExit(0);
    verifyZeroInteractions(hook);
  }
}
