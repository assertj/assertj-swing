/*
 * Created on Nov 19, 2008
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
 * A range of values.
 * 
 * @param <T> the generic type of the values in this range.
 * 
 * @author Yvonne Wang
 */
public final class GenericRange<T> {
  private final T from;
  private final T to;

  /**
   * Creates a new {@link GenericRange}.
   * 
   * @param from the initial value of this range.
   * @param to the final value of this range.
   */
  public GenericRange(@Nonnull T from, @Nonnull T to) {
    this.from = from;
    this.to = to;
  }

  /**
   * @return the initial value of this range.
   */
  public @Nonnull T from() {
    return from;
  }

  /**
   * @return the final value of this range.
   */
  public @Nonnull T to() {
    return to;
  }
}
