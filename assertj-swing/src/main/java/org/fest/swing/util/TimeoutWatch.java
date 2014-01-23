/*
 * Created on Oct 29, 2007
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
package org.fest.swing.util;

import static java.lang.System.currentTimeMillis;

import javax.annotation.Nonnull;

/**
 * Time counter with a timeout.
 * 
 * @author Alex Ruiz
 */
public final class TimeoutWatch {
  private final long timeout;
  private long startTime;

  /**
   * Creates and starts a new {@link TimeoutWatch} with the given timeout.
   * 
   * @param timeout the given timeout.
   * @return the new timeout watch.
   */
  public static @Nonnull TimeoutWatch startWatchWithTimeoutOf(long timeout) {
    TimeoutWatch watch = new TimeoutWatch(timeout);
    watch.start();
    return watch;
  }

  private TimeoutWatch(long timeout) {
    this.timeout = timeout;
  }

  void start() {
    startTime = currentTimeMillis();
  }

  public boolean isTimeOut() {
    long timePassed = currentTimeMillis() - startTime;
    return timePassed >= timeout;
  }
}
