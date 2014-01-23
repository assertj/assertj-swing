/*
 * Created on Jul 20, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.core;

import javax.annotation.Nonnull;

/**
 * <p>
 * Exception handler for the AWT event thread, to make sure we can get a back-trace dump when running FEST-Swing are
 * terminated by a {@link EmergencyAbortListener}.
 * </p>
 * 
 * <p>
 * This exception handler is passed to the JVM using the system property "sun.awt.exception.handler" to override the
 * default exception handling behavior of the event dispatch thread (EDT.)
 * </p>
 * 
 * <p>
 * This is a Sun-specific feature (or "bug".) See <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4714232"
 * target="_blank">bug 4714232</a>.
 * </p>
 * 
 * @author <a href="mailto:simeon.fitch@mseedsoft.com">Simeon H.K. Fitch</a>
 */
public class SimpleFallbackExceptionHandler {
  /**
   * Prints the stack trace of the given exception to the standard error stream.
   * 
   * @param t the given exception.
   */
  public void handle(@Nonnull Throwable t) {
    t.printStackTrace();
  }
}