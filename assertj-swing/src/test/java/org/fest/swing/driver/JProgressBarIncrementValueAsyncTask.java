/*
 * Created on Dec 6, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.fest.swing.driver.JProgressBarIncrementValueTask.incrementValue;
import static org.fest.util.Strings.concat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.annotation.Nonnull;
import javax.swing.JProgressBar;

import org.fest.swing.core.Robot;

/**
 * Asynchronously increments the value of a {@code JProgressBar} 3 times, given an increment and a period of time in
 * between increments.
 * 
 * @author Alex Ruiz
 */
class JProgressBarIncrementValueAsyncTask {
  private static Logger logger = Logger.getAnonymousLogger();

  static @Nonnull
  TaskBuilder with(@Nonnull JProgressBar progressBar) {
    return new TaskBuilder(progressBar);
  }

  private static final int INCREMENT_COUNT = 3;

  private final ExecutorService executor = newSingleThreadExecutor();
  private final Runnable task;
  private Future<?> future;

  private final Robot robot;
  private final JProgressBar progressBar;
  private final int increment;
  private final long periodInMs;

  private JProgressBarIncrementValueAsyncTask(@Nonnull Robot robot, @Nonnull JProgressBar progressBar, int increment,
      long periodInMs) {
    this.robot = robot;
    this.progressBar = progressBar;
    this.increment = increment;
    this.periodInMs = periodInMs;
    task = createInnerTask();
  }

  private @Nonnull
  Runnable createInnerTask() {
    return new Runnable() {
      @Override
      public void run() {
        try {
          for (int i = 0; i < INCREMENT_COUNT; i++) {
            sleepAndIncrementValue();
          }
        } catch (InterruptedException e) {
          logger.info("Task has been cancelled");
        }
      }
    };
  }

  private void sleepAndIncrementValue() throws InterruptedException {
    logger.info(concat("Going to sleep for ", periodInMs, " ms"));
    Thread.sleep(periodInMs);
    int newValue = incrementValue(progressBar, increment);
    logger.info(concat("Incremented JProgressBar value to ", newValue));
    robot.waitForIdle();
  }

  synchronized void runAsynchronously() {
    future = executor.submit(task);
  }

  synchronized void cancelIfNotFinished() {
    if (future != null && !future.isDone()) {
      future.cancel(true);
    }
  }

  static class TaskBuilder {
    private final JProgressBar progressBar;
    private int increment = 10;
    private long periodInMs = 1000;

    TaskBuilder(@Nonnull JProgressBar progressBar) {
      this.progressBar = progressBar;
    }

    @Nonnull
    TaskBuilder increment(int value) {
      increment = value;
      return this;
    }

    @Nonnull
    TaskBuilder every(long duration, TimeUnit timeUnit) {
      periodInMs = timeUnit.toMillis(duration);
      return this;
    }

    @Nonnull
    JProgressBarIncrementValueAsyncTask createTask(@Nonnull Robot robot) {
      return new JProgressBarIncrementValueAsyncTask(robot, progressBar, increment, periodInMs);
    }
  }
}
