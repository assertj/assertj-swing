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

import javax.annotation.Nonnull;

/**
 * Installs/uninstalls a {@link NoExitSecurityManager}.
 *
 * @author Alex Ruiz
 */
public class NoExitSecurityManagerInstaller {
  /**
   * Installs a new {@link NoExitSecurityManager} in {@code System}.
   *
   * @return this installer.
   */
  public static @Nonnull NoExitSecurityManagerInstaller installNoExitSecurityManager() {
    return new NoExitSecurityManagerInstaller(new NoExitSecurityManager());
  }

  /**
   * Installs a new {@link NoExitSecurityManager} in {@code System}.
   *
   * @param hook gets notified when an application tries to terminate the current JVM.
   * @return this installer.
   * @throws NullPointerException if the given hook is {@code null}.
   */
  public static @Nonnull NoExitSecurityManagerInstaller installNoExitSecurityManager(@Nonnull ExitCallHook hook) {
    return new NoExitSecurityManagerInstaller(new NoExitSecurityManager(hook));
  }

  private final SecurityManager oldManager;

  private NoExitSecurityManagerInstaller(@Nonnull NoExitSecurityManager newManager) {
    oldManager = System.getSecurityManager();
    System.setSecurityManager(newManager);
  }

  /**
   * Uninstalls the {@link NoExitSecurityManager} installed by {@link #installNoExitSecurityManager()}, restoring the
   * original {@code SecurityManager}.
   */
  public void uninstall() {
    System.setSecurityManager(oldManager);
  }
}
