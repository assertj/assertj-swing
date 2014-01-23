/*
 * Created on Oct 31, 2007
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
package org.fest.swing.timing;

import static org.fest.util.Objects.HASH_CODE_PRIME;
import static org.fest.util.Preconditions.checkNotNull;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

/**
 * Timeout.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class Timeout {
  private final long duration;

  /**
   * Creates a new {@link Timeout}.
   *
   * @param duration the duration of the timeout in milliseconds.
   * @return the created {@code Timeout}.
   */
  public static @Nonnull Timeout timeout(long duration) {
    return new Timeout(duration);
  }

  /**
   * Creates a new {@link Timeout}.
   *
   * @param duration the duration of the timeout.
   * @param timeUnit the unit of time of the timeout.
   * @return the created {@code Timeout}.
   * @throws NullPointerException if the given time unit is {@code null}.
   */
  public static@Nonnull  Timeout timeout(long duration, @Nonnull TimeUnit timeUnit) {
    checkNotNull(timeUnit);
    return new Timeout(timeUnit.toMillis(duration));
  }

  private Timeout(long duration) {
    this.duration = duration;
  }

  /**
   * @return the duration of the timeout in milliseconds.
   */
  public long duration() {
    return duration;
  }

  @Override
  public int hashCode() {
    return HASH_CODE_PRIME * 1 + (int) (duration ^ (duration >>> 32));
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Timeout other = (Timeout) obj;
    return duration == other.duration;
  }
}
