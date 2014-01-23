/*
 * Created on Nov 21, 2008
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
package org.fest.swing.driver;

import static org.fest.swing.query.ComponentVisibleQuery.isVisible;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.util.TimeoutWatch;

/**
 * Waits until a particular AWT or Swing {@code Component} shows up on the screen.
 * 
 * @author Alex Ruiz
 */
public final class ComponentShownWaiter extends ComponentAdapter {
  private static final int DEFAULT_TIMEOUT = 5000;
  private static final int DEFAULT_SLEEP_TIME = 10;

  private Component toWaitFor;
  private volatile boolean shown;

  /**
   * Waits until the given AWT or Swing {@code Component} is shown on the screen, using a timeout of 5 seconds.
   * 
   * @param toWaitFor the {@code Component} to wait for.
   * @throws WaitTimedOutError if the {@code Component} is not shown before the default timeout of 5 seconds.
   */
  public static void waitTillShown(@Nonnull Component toWaitFor) {
    new ComponentShownWaiter(toWaitFor).startWaiting(DEFAULT_TIMEOUT);
  }

  /**
   * Waits until the given AWT or Swing {@code Component} is shown on the screen.
   * 
   * @param toWaitFor the {@code Component} to wait for.
   * @param timeout the amount to time (in milliseconds) to wait for the {@code Component} to be shown.
   * @throws WaitTimedOutError if the {@code Component} is not shown before the given timeout expires.
   */
  public static void waitTillShown(@Nonnull Component toWaitFor, long timeout) {
    new ComponentShownWaiter(toWaitFor).startWaiting(timeout);
  }

  private ComponentShownWaiter(@Nonnull Component toWaitFor) {
    this.toWaitFor = toWaitFor;
    toWaitFor.addComponentListener(this);
  }

  private void startWaiting(long timeout) {
    if (alreadyVisible()) {
      return;
    }
    TimeoutWatch watch = startWatchWithTimeoutOf(timeout);
    while (!shown) {
      pause(DEFAULT_SLEEP_TIME);
      if (watch.isTimeOut()) {
        done();
        throw new WaitTimedOutError("Timed out waiting for component to be visible");
      }
    }
  }

  private boolean alreadyVisible() {
    if (!isVisible(toWaitFor)) {
      return false;
    }
    done();
    return true;
  }

  /**
   * Notification that the AWT or Swing {@code Component} to wait for is finally shown on the screen.
   * 
   * @param e the event raised when the {@code Component} has been made visible.
   */
  @RunsInEDT
  @Override
  public void componentShown(@Nullable ComponentEvent e) {
    shown = true;
    done();
  }

  private void done() {
    toWaitFor.removeComponentListener(this);
    toWaitFor = null;
  }
}
