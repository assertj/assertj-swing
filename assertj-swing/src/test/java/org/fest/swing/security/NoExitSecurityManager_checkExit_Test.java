/*
 * Created on Dec 21, 2009
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
package org.fest.swing.security;

import static org.fest.test.ExpectedException.none;
import static org.fest.util.Arrays.array;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.fest.test.ExpectedException;
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
  public void should_call_hook_and_throw_error_if_Runtime_exit_was_called() {
    StackTraceElement[] stackTrace = array(methodInRuntime("exit"));
    when(stackTraces.stackTraceInCurrentThread()).thenReturn(stackTrace);
    thrown.expect(ExitException.class);
    securityManager.checkExit(0);
    verify(hook).exitCalled(0);
  }

  @Test
  public void should_call_hook_and_throw_error_if_Runtime_halt_was_called() {
    StackTraceElement[] stackTrace = array(methodInRuntime("halt"));
    when(stackTraces.stackTraceInCurrentThread()).thenReturn(stackTrace);
    thrown.expect(ExitException.class);
    securityManager.checkExit(0);
    verify(hook).exitCalled(0);
  }

  @Test
  public void should_not_call_hook_and_throw_error_if_method_called_is_in_Runtime_but_is_not_exit_or_halt() {
    StackTraceElement[] stackTrace = array(methodInRuntime("availableProcessors"));
    when(stackTraces.stackTraceInCurrentThread()).thenReturn(stackTrace);
    securityManager.checkExit(0);
    verifyZeroInteractions(hook);
  }

  private StackTraceElement methodInRuntime(String methodName) {
    return new StackTraceElement(Runtime.class.getName(), methodName, "Runtime.java", 0);
  }

  @Test
  public void should_not_call_hook_and_throw_error_if_method_called_is_not_Runtime_exit_or_halt() {
    StackTraceElement e = new StackTraceElement(String.class.getName(), "substring", "String.java", 0);
    StackTraceElement[] stackTrace = array(e);
    when(stackTraces.stackTraceInCurrentThread()).thenReturn(stackTrace);
    securityManager.checkExit(0);
    verifyZeroInteractions(hook);
  }
}
