/*
 * Created on Aug 23, 2012
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
 * Copyright @2012-2013 the original author or authors.
 */
package org.fest.util;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.LinkedHashSet;

import static java.util.Collections.addAll;
import static org.fest.util.Preconditions.checkNotNull;

/**
 * Utility methods related to {@code java.util.Set}s.
 *
 * @author Alex Ruiz
 */
public final class Sets {
  private Sets() {
  }

  /**
   * Creates a <em>mutable</em> {@code HashSet}.
   *
   * @param <T> the generic type of the {@code HashSet} to create.
   * @return the created {@code HashSet}.
   * @since 1.2.3
   */
  public static @Nonnull <T> HashSet<T> newHashSet() {
    return new HashSet<T>();
  }

  /**
   * Creates a <em>mutable</em> {@code HashSet} containing the given elements.
   *
   * @param <T>      the generic type of the {@code HashSet} to create.
   * @param elements the elements to store in the {@code HashSet}.
   * @return the created {@code HashSet}.
   * @throws NullPointerException if the given {@code Iterable} is {@code null}.
   * @since 1.2.3
   */
  public static @Nonnull <T> HashSet<T> newHashSet(@Nonnull Iterable<? extends T> elements) {
    HashSet<T> set = newHashSet();
    for (T e : elements) {
      set.add(e);
    }
    return set;
  }

  /**
   * Creates a <em>mutable</em> {@code LinkedHashSet}.
   *
   * @param <T> the generic type of the {@code LinkedHashSet} to create.
   * @return the created {@code LinkedHashSet}.
   * @since 1.2.3
   */
  public static @Nonnull <T> LinkedHashSet<T> newLinkedHashSet() {
    return new LinkedHashSet<T>();
  }

  /**
   * Creates a <em>mutable</em> {@link LinkedHashSet} containing the given elements.
   *
   * @param <T>      the generic type of the {@code LinkedHashSet} to create.
   * @param elements the elements to store in the {@code LinkedHashSet}.
   * @return the created {@code LinkedHashSet}.
   * @throws NullPointerException if the given array is {@code null}.
   * @since 1.2.3
   */
  public static @Nonnull <T> LinkedHashSet<T> newLinkedHashSet(@Nonnull T... elements) {
    checkNotNull(elements);
    LinkedHashSet<T> set = new LinkedHashSet<T>();
    addAll(set, elements);
    return set;
  }
}
