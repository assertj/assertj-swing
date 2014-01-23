/*
 * Created on Jan 17, 2008
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
package org.fest.swing.exception;

import javax.annotation.Nonnull;

/**
 * Error raised when the location of an AWT or Swing {@code Component} cannot be provided.
 *
 * @author Alex Ruiz
 */
public class LocationUnavailableException extends RuntimeException {
  /**
   * Creates a new {@link LocationUnavailableException}.
   *
   * @param message the detail message.
   */
  public LocationUnavailableException(@Nonnull String message) {
    super(message);
  }

  /**
   * Creates a new {@link LocationUnavailableException}.
   *
   * @param message the detail message.
   * @param cause the cause of the error.
   */
  public LocationUnavailableException(@Nonnull String message, @Nonnull Throwable cause) {
    super(message, cause);
  }
}
