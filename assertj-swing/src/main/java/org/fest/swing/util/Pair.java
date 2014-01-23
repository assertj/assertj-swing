/*
 * Created on Jul 29, 2008
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
package org.fest.swing.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Tuple of size 2.
 *
 * @param <F> the generic type of the 1st. value in this tuple.
 * @param <S> the generic type of the 2nd. value in this tuple.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class Pair<F, S> {
  /** The first value in this tuple. */
  public final F first;

  /** The second value in this tuple. */
  public final S second;

  /**
   * Creates a new {@link Pair}.
   *
   * @param first the 1st. value in this tuple.
   * @param second the 2nd. value in this tuple.
   * @return the created {@code Pair}.
   */
  public static @Nonnull <F, S> Pair<F, S> of(@Nullable F first, @Nullable S second) {
    return new Pair<F, S>(first, second);
  }

  Pair(@Nullable F first, @Nullable S second) {
    this.first = first;
    this.second = second;
  }
}
