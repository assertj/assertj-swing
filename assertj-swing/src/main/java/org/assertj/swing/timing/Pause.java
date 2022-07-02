/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2018 the original author or authors.
 */
package org.assertj.swing.timing;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.core.util.Preconditions.checkNotNullOrEmpty;
import static org.assertj.swing.timing.Timeout.timeout;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.assertj.core.presentation.StandardRepresentation;
import org.assertj.swing.exception.WaitTimedOutError;

/**
 * Waits for period of time or for a particular condition to be satisfied.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class Pause {
  private static final Timeout DEFAULT_TIMEOUT = timeout();
  private static final int SLEEP_INTERVAL = 10;

  /**
   * Waits until the given condition is satisfied.
   * 
   * @param condition the condition to verify.
   * @throws NullPointerException if the given condition is {@code null}.
   * @throws WaitTimedOutError if the wait times out (more than 30 seconds).
   */
  public static void pause(@Nonnull Condition condition) {
    pause(condition, DEFAULT_TIMEOUT);
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
   * @param timeout the timeout (in milliseconds).
   * @throws NullPointerException if the given condition is {@code null}.
   * @throws WaitTimedOutError if the wait times out.
   */
  public static void pause(@Nonnull final Condition condition, final long timeout) {
    checkNotNull(condition);
    try {
      performPause(timeout, condition);
    } finally {
      condition.done();
    }
  }

  private static void performPause(long timeout, Condition condition) {
    AtomicBoolean cancelled = new AtomicBoolean(false);

    Runnable runnable = () -> {
      while (!cancelled.get() && !Thread.currentThread().isInterrupted() && !condition.test()) {
        pause();
      }
    };
    Future<Void> future = CompletableFuture.runAsync(runnable);
    try {
      future.get(timeout, TimeUnit.MILLISECONDS);
    } catch (ExecutionException e) {
      if (e.getCause() instanceof RuntimeException) {
        throw (RuntimeException) e.getCause();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      cancelled.set(true);
      throw new WaitTimedOutError("Timed out waiting for " + new StandardRepresentation().toStringOf(condition));
    }
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
    pause(conditions, DEFAULT_TIMEOUT);
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
   * @param timeout the timeout (in milliseconds).
   * @throws NullPointerException if the array of conditions is {@code null}.
   * @throws IllegalArgumentException if the array of conditions is empty.
   * @throws NullPointerException if the array of conditions has one or more {@code null} values.
   * @throws WaitTimedOutError if the wait times out.
   */
  public static void pause(@Nonnull final Condition[] conditions, final long timeout) {
    checkNotNullOrEmpty(conditions);
    for (Condition condition : conditions) {
      checkNotNull(condition);
    }
    Condition condition = allConditions(conditions);
    try {
      performPause(timeout, condition);
    } finally {
      condition.done();
    }
  }

  private static Condition allConditions(@Nonnull Condition[] conditions) {
    String description = Stream.of(conditions)
            .map(Condition::toString)
            .collect(Collectors.joining("\n", "=>", ""));
    return new Condition(description) {
      @Override
      public boolean test() {
        for (Condition condition : conditions) {
          if (!condition.test()) {
            return false;
          }
        }
        return true;
      }

      @Override
      protected void done() {
        for (Condition condition : conditions) {
         condition.done();
        }
      }
    };

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

  private Pause() {
  }
}
