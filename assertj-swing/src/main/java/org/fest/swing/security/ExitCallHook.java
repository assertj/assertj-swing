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

/**
 * Hook called by {@link NoExitSecurityManager} when an application tries to terminate the current JVM. This hook is
 * called before throwing {@link ExitException}.
 *
 * @author Alex Ruiz
 */
public interface ExitCallHook {
  /**
   * Implement this method to do any context-specific cleanup. This hook is provided since it may not always be possible
   * to catch the {@link ExitException} explicitly (like when it's caught by someone else, or thrown from the event
   * dispatch thread).
   *
   * @param status the status the exit status.
   */
  void exitCalled(int status);
}
