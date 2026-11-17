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
package org.assertj.swing.monitor;

import static java.util.logging.Level.WARNING;

import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * Prevents misbehaving {@code TimerTask}s from canceling the timer thread by throwing exceptions and/or errors.
 * 
 * @author Alex Ruiz
 */
class ProtectingTimerTask extends TimerTask {
  private static Logger logger = Logger.getLogger(ProtectingTimerTask.class.getCanonicalName());

  private final TimerTask task;
  private volatile boolean canceled;

  ProtectingTimerTask(TimerTask task) {
    this.task = task;
  }

  @Override
  public void run() {
    if (canceled) {
      return;
    }
    try {
      task.run();
    } catch (Throwable thrown) {
      handleException(thrown);
    }
  }

  @Override
  public boolean cancel() {
    canceled = true;
    task.cancel();
    return super.cancel();
  }

  private void handleException(Throwable thrown) {
    logger.log(WARNING, "Exception thrown by a TimerTask", thrown);
  }
}
