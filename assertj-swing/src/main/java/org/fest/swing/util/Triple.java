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

/**
 * A tuple of size 3.
 * 
 * @param <F> the generic type of the 1st. value in this tuple.
 * @param <S> the generic type of the 2nd. value in this tuple.
 * @param <T> the generic type of the 3rd. value in this tuple.
 * 
 * @author Alex Ruiz
 */
public class Triple<F, S, T> extends Pair<F, S> {
  /** The third value in this tuple. */
  public final T third;

  /**
   * Creates a new {@link Triple}.
   * 
   * @param first the 1st. value in this tuple.
   * @param second the 2nd. value in this tuple.
   * @param third the 3rd. value in this tuple.
   * @return the created {@code Triple}.
   */
  public static @Nonnull <F, S, T> Triple<F, S, T> of(F first, S second, T third) {
    return new Triple<F, S, T>(first, second, third);
  }

  private Triple(F first, S second, T third) {
    super(first, second);
    this.third = third;
  }
}
