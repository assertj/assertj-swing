/*
 * Created on Dec 19, 2007
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

import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;
import static org.fest.util.Arrays.format;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.util.TimeoutWatch;

/**
 * Waits for period of time or for a particular condition to be satisfied.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class Pause {
  private static final int DEFAULT_DELAY = 30000;
  private static final int SLEEP_INTERVAL = 10;

  /**
   * Waits until the given condition is satisfied.
   * 
   * @param condition the condition to verify.
   * @throws NullPointerException if the given condition is {@code null}.
   * @throws WaitTimedOutError if the wait times out (more than 30 seconds).
   */
  public static void pause(@Nonnull Condition condition) {
    pause(condition, DEFAULT_DELAY);
  }

  /**
   * Waits until the given condition is satisfied.
   * 
   * @param condition the condition to verify.
   * @param timeout the timeout.
   * @throws NullPointerException if the given timeout is {@code null}.
   * @throws NullPointerException if the given condition is {@code null}.
   * @throws WaitTimedOutError if the wait times out.
   */
  public static void pause(@Nonnull Condition condition, @Nonnull Timeout timeout) {
    checkNotNull(timeout);
    pause(condition, timeout.duration());
  }

  /**
   * Waits until the given condition is satisfied.
   * 
   * @param condition the condition to verify.
   * @param timeout the timeout (in milliseconds.)
   * @throws NullPointerException if the given condition is {@code null}.
   * @throws WaitTimedOutError if the wait times out.
   */
  public static void pause(@Nonnull Condition condition, long timeout) {
    checkNotNull(condition);
    TimeoutWatch watch = startWatchWithTimeoutOf(timeout);
    while (!condition.test()) {
      if (watch.isTimeOut() && !condition.test()) {
        condition.done();
        throw new WaitTimedOutError(String.format("Timed out waiting for %s", condition.toString()));
      }
      pause(SLEEP_INTERVAL);
    }
    condition.done();
  }

  /**
   * Waits until the given conditions are satisfied.
   * 
   * @param conditions the conditions to verify.
   * @throws NullPointerException if the array of conditions is {@code null}.
   * @throws IllegalArgumentException if the array of conditions is empty.
   * @throws NullPointerException if the array of conditions has one or more {@code null} values.
   * @throws WaitTimedOutError if the wait times out (more than 30 seconds).
   */
  public static void pause(@Nonnull Condition[] conditions) {
    pause(conditions, DEFAULT_DELAY);
  }

  /**
   * Waits until the given conditions are satisfied.
   * 
   * @param conditions the conditions to verify.
   * @param timeout the timeout.
   * @throws NullPointerException if the given timeout is {@code null}.
   * @throws NullPointerException if the array of conditions is {@code null}.
   * @throws IllegalArgumentException if the array of conditions is empty.
   * @throws NullPointerException if the array of conditions has one or more {@code null} values.
   * @throws WaitTimedOutError if the wait times out.
   */
  public static void pause(@Nonnull Condition[] conditions, @Nonnull Timeout timeout) {
    pause(conditions, timeout.duration());
  }

  /**
   * Waits until the given conditions are satisfied.
   * 
   * @param conditions the conditions to verify.
   * @param timeout the timeout (in milliseconds.)
   * @throws NullPointerException if the array of conditions is {@code null}.
   * @throws IllegalArgumentException if the array of conditions is empty.
   * @throws NullPointerException if the array of conditions has one or more {@code null} values.
   * @throws WaitTimedOutError if the wait times out.
   */
  public static void pause(@Nonnull Condition[] conditions, long timeout) {
    checkNotNullOrEmpty(conditions);
    TimeoutWatch watch = startWatchWithTimeoutOf(timeout);
    while (!areSatisfied(conditions)) {
      if (watch.isTimeOut()) {
        for (Condition condition : conditions) {
          condition.done();
        }
        throw new WaitTimedOutError(String.format("Timed out waiting for %s", format(conditions)));
      }
      pause(SLEEP_INTERVAL);
    }
    for (Condition condition : conditions) {
      condition.done();
    }
  }

  private static boolean areSatisfied(@Nonnull Condition[] conditions) {
    for (Condition condition : conditions) {
      checkNotNull(condition);
      if (!condition.test()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Sleeps for the specified time.
   * 
   * @param timeout the quantity of time units to sleep.
   * @param unit the time units.
   * @see #pause(long)
   * @throws NullPointerException if {@code unit} is {@code null}.
   */
  public static void pause(long timeout, @Nonnull TimeUnit unit) {
    checkNotNull(unit);
    pause(unit.toMillis(timeout));
  }

  /**
   * <p>
   * Sleeps for the specified time.
   * </p>
   * 
   * <p>
   * To catch any {@code InterruptedException}s that occur, {@code Thread#sleep(long)} may be used instead.
   * </p>
   * 
   * @param ms the time to sleep in milliseconds.
   */
  public static void pause(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Sleeps for 10 milliseconds.
   */
  public static void pause() {
    pause(SLEEP_INTERVAL);
  }

  private Pause() {}
}
