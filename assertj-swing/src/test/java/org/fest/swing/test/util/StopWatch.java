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
package org.fest.swing.test.util;

import static java.lang.System.currentTimeMillis;

/**
 * @author Yvonne Wang
 */
public final class StopWatch {
  private boolean started;
  private boolean stopped;
  private long startTime;
  private long endTime;

  public static StopWatch startNewStopWatch() {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    return stopWatch;
  }

  public void start() {
    started = true;
    stopped = false;
    startTime = currentTimeMillis();
    endTime = 0;
  }

  public void stop() {
    stopped = true;
    endTime = currentTimeMillis();
  }

  public long ellapsedTime() {
    if (!started) {
      throw new IllegalStateException("stopwatch has not been started");
    }
    if (!stopped) {
      throw new IllegalStateException("stopwatch has not been stopped");
    }
    return endTime - startTime;
  }
}
