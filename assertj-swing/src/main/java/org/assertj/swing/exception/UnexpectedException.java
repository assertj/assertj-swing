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
package org.assertj.swing.exception;

import javax.annotation.Nonnull;

/**
 * Error thrown when an unexpected error occurs.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class UnexpectedException extends RuntimeException {
  /** Generated serial version UID. */
  private static final long serialVersionUID = 1097894632492119256L;

  /**
   * Returns a {@link UnexpectedException}.
   *
   * @param cause the unexpected exception.
   * @return the created exception.
   */
  public static @Nonnull UnexpectedException unexpected(@Nonnull Throwable cause) {
    return new UnexpectedException(cause);
  }

  /**
   * Creates a new {@link UnexpectedException}.
   *
   * @param cause the unexpected exception.
   */
  public @Nonnull UnexpectedException(@Nonnull Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new {@link UnexpectedException}.
   *
   * @param message the detail message.
   * @param cause the unexpected exception.
   */
  public UnexpectedException(@Nonnull String message, @Nonnull Throwable cause) {
    super(message, cause);
  }
}
