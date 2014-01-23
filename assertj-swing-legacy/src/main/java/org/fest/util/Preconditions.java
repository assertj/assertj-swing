/*
 * Created on Sep 4, 2012
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
 * Copyright @2012-2013 Google Inc. and others.
 */
package org.fest.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Verifies correct argument values and state (borrowed from Guava.)
 *
 * @author alruiz@google.com (Alex Ruiz)
 */
public final class Preconditions {
  private Preconditions() {
  }

  /**
   * Verifies that the given {@code String} is not {@code null} or empty.
   *
   * @param s the given {@code String}.
   * @return the validated {@code String}.
   * @throws NullPointerException     if the given {@code String} is {@code null}.
   * @throws IllegalArgumentException if the given {@code String} is empty.
   */
  public static @Nonnull String checkNotNullOrEmpty(@Nullable String s) {
    String checked = checkNotNull(s);
    if (checked.isEmpty()) {
      throw new IllegalArgumentException();
    }
    return checked;
  }

  /**
   * Verifies that the given array is not {@code null} or empty.
   *
   * @param array the given array.
   * @return the validated array.
   * @throws NullPointerException     if the given array is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   */
  public static @Nonnull <T> T[] checkNotNullOrEmpty(@Nullable T[] array) {
    T[] checked = checkNotNull(array);
    if (checked.length == 0) {
      throw new IllegalArgumentException();
    }
    return checked;
  }

  /**
   * Verifies that the given object reference is not {@code null}.
   *
   * @param <T>       the type of the given object reference.
   * @param reference the given object reference.
   * @return the non-{@code null} reference that was validated.
   * @throws NullPointerException if the given object reference is {@code null}.
   */
  public static @Nonnull <T> T checkNotNull(@Nullable T reference) {
    if (reference == null) {
      throw new NullPointerException();
    }
    return reference;
  }
}
