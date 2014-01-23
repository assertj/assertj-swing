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

import static org.fest.util.Strings.concat;

import java.security.Permission;

import org.fest.util.VisibleForTesting;

/**
 * {@code SecurityManager} that does not allow an application under test to terminate the current JVM. Adapted from
 * Abbot's {@code NoExitSecurityManager}.
 * 
 * @author Alex Ruiz
 */
public class NoExitSecurityManager extends SecurityManager {
  private static final ExitCallHook NULL_HOOK = new ExitCallHook() {
    @Override
    public void exitCalled(int status) {
    }
  };

  private final ExitCallHook hook;
  private final StackTraces stackTraces;

  /**
   * Creates a new {@link NoExitSecurityManager}.
   */
  public NoExitSecurityManager() {
    this(NULL_HOOK);
  }

  /**
   * Creates a new {@link NoExitSecurityManager}.
   * 
   * @param hook notified when an application tries to terminate the current JVM.
   * @throws NullPointerException if the given hook is {@code null}.
   */
  public NoExitSecurityManager(ExitCallHook hook) {
    this(hook, new StackTraces());
  }

  @VisibleForTesting
  NoExitSecurityManager(ExitCallHook hook, StackTraces stackTraces) {
    if (hook == null) {
      throw new NullPointerException(concat("The given ", ExitCallHook.class.getSimpleName(), " should not be null"));
    }
    this.hook = hook;
    this.stackTraces = stackTraces;
  }

  /**
   * Allows everything.
   * 
   * @param permission the specified permission.
   * @param context a system-dependent security context.
   */
  @Override
  public void checkPermission(Permission permission, Object context) {}

  /**
   * Allows everything.
   * 
   * @param permission the specified permission.
   */
  @Override
  public void checkPermission(Permission permission) {}

  /**
   * Throws an {@link ExitException} if an application tries to terminate the current JVM (through
   * {@link Runtime#exit(int)} or {@link Runtime#halt(int)}.)
   * 
   * @param status the exit status.
   * @throws ExitException if an application tries to terminate the current JVM.
   */
  @Override
  public void checkExit(int status) {
    if (exitInvoked()) {
      hook.exitCalled(status);
      throw new ExitException(concat("Application tried to terminate current JVM with status ", status));
    }
  }

  /**
   * Indicates whether "exit" has been invoked through a call of {@link Runtime#exit(int)} or {@link Runtime#halt(int)}.
   * 
   * @return {@code true} if an exit has been invoked through a call of {@code Runtime.exit} or {@code Runtime.halt};
   *         {@code false} otherwise.
   */
  private boolean exitInvoked() {
    for (StackTraceElement e : stackTraces.stackTraceInCurrentThread()) {
      if (exitInvoked(e)) {
        return true;
      }
    }
    return false;
  }

  private boolean exitInvoked(StackTraceElement e) {
    if (!Runtime.class.getName().equals(e.getClassName())) {
      return false;
    }
    final String method = e.getMethodName();
    return "exit".equals(method) || "halt".equals(method);
  }
}
