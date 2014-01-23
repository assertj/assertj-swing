/*
 * Created on Nov 13, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.exception;

import javax.annotation.Nonnull;

/**
 * Error that occurred when simulation of user input failed.
 * 
 * @author Yvonne Wang
 */
public class ActionFailedException extends RuntimeException {
  /**
   * Creates a new {@link ActionFailedException}.
   * 
   * @param message the detail message.
   * @return the created exception.
   */
  public static @Nonnull ActionFailedException actionFailure(@Nonnull String message) {
    return new ActionFailedException(message);
  }

  /**
   * Creates a new {@link ActionFailedException}.
   * 
   * @param message the detail message.
   * @param cause the cause of the error.
   * @return the created exception.
   */
  public static ActionFailedException actionFailure(@Nonnull String message, @Nonnull Throwable cause) {
    return new ActionFailedException(message, cause);
  }

  private ActionFailedException(@Nonnull String message) {
    super(message);
  }

  private ActionFailedException(@Nonnull String message, @Nonnull Throwable cause) {
    super(message, cause);
  }
}
