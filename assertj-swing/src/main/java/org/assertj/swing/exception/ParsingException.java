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
 * Error thrown when an unexpected error occurs while parsing a file.
 *
 * @author Alex Ruiz
 */
public class ParsingException extends RuntimeException {
  /** Generated serial version UID. */
  private static final long serialVersionUID = -8420935990336354323L;

  /**
   * Creates a new {@link ParsingException}.
   *
   * @param message the detail message.
   */
  public ParsingException(@Nonnull String message) {
    super(message);
  }

  /**
   * Creates a new {@link ParsingException}.
   *
   * @param message the detail message.
   * @param cause the cause of the error.
   */
  public ParsingException(@Nonnull String message, @Nonnull Throwable cause) {
    super(message, cause);
  }
}
