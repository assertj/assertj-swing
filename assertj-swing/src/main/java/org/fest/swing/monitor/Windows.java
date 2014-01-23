/*
 * Created on Oct 8, 2007
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
package org.fest.swing.monitor;

import static org.fest.util.Maps.newWeakHashMap;

import java.awt.Component;
import java.awt.Window;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.util.VisibleForTesting;

/**
 * Information collected by the monitors in this package.
 * 
 * @author Alex Ruiz
 */
@ThreadSafe
class Windows {
  @VisibleForTesting
  static int WINDOW_READY_DELAY = 10000;

  /** {@link Window#isShowing() isShowing} is true but are not yet ready for input. */
  @GuardedBy("lock")
  final Map<Window, TimerTask> pending = newWeakHashMap();

  /** Considered to be ready to use. */
  @GuardedBy("lock")
  final Map<Window, Boolean> open = newWeakHashMap();

  /** Have sent a {@link java.awt.event.WindowEvent#WINDOW_CLOSED WINDOW_CLOSED} event. */
  @GuardedBy("lock")
  final Map<Window, Boolean> closed = newWeakHashMap();

  /** Not visible. */
  @GuardedBy("lock")
  final Map<Window, Boolean> hidden = newWeakHashMap();

  private final Timer windowReadyTimer;

  private final Object lock = new Object();

  Windows() {
    windowReadyTimer = new Timer("Window Ready Timer", true);
  }

  /**
   * Creates a new {@link WindowVisibilityMonitor} and attaches it to the given {@code Window}.
   * 
   * @param target the {@code Window} to attach the new monitor to.
   */
  void attachNewWindowVisibilityMonitor(Window target) {
    WindowVisibilityMonitor monitor = new WindowVisibilityMonitor(this);
    target.addWindowListener(monitor);
    target.addComponentListener(monitor);
  }

  /**
   * Marks the given window as "ready to use" and if not showing, as "hidden."
   * 
   * @param w the given window.
   */
  @RunsInCurrentThread
  void markExisting(@Nonnull Window w) {
    synchronized (lock) {
      open.put(w, true);
      if (!w.isShowing()) {
        hidden.put(w, true);
      }
    }
  }

  /**
   * Marks the given window as "hidden."
   * 
   * @param w the given window.
   */
  void markAsHidden(@Nonnull Window w) {
    synchronized (lock) {
      hidden.put(w, true);
      removeWindowFrom(w, pending);
    }
  }

  /**
   * Marks the given window as "showing."
   * 
   * @param w the given window.
   */
  void markAsShowing(final @Nonnull Window w) {
    synchronized (lock) {
      TimerTask task = new TimerTask() {
        @Override
        public void run() {
          markAsReady(w);
        }
      };
      windowReadyTimer.schedule(new ProtectingTimerTask(task), WINDOW_READY_DELAY);
      pending.put(w, task);
    }
  }

  /**
   * Marks the given window as "ready to receive OS-level event input."
   * 
   * @param w the given window.
   */
  void markAsReady(@Nonnull Window w) {
    synchronized (lock) {
      if (!pending.containsKey(w)) {
        return;
      }
      removeWindowFrom(w, closed, hidden, pending);
      open.put(w, true);
    }
  }

  /**
   * Marks the given window as "closed."
   * 
   * @param w the given window.
   */
  void markAsClosed(@Nonnull Window w) {
    synchronized (lock) {
      removeWindowFrom(w, open, hidden, pending);
      closed.put(w, true);
    }
  }

  private void removeWindowFrom(Window w, Map<?, ?>... maps) {
    for (Map<?, ?> map : maps) {
      map.remove(w);
    }
  }

  /**
   * Indicates whether the given AWT or Swing {@code Component} is a closed {@code Window}.
   * 
   * @param c the given {@code Component}.
   * @return {@code true} if the given {@code Component} is a closed {@code Window}, {@code false} otherwise.
   */
  boolean isClosed(@Nonnull Component c) {
    synchronized (lock) {
      return closed.containsKey(c);
    }
  }

  /**
   * Indicates whether the given {@code Window} is ready to receive OS-level event input.
   * 
   * @param w the given {@code Window}.
   * @return {@code true} if the given {@code Window} is ready to receive OS-level event input, {@code false} otherwise.
   */
  boolean isReady(@Nonnull Window w) {
    synchronized (lock) {
      return open.containsKey(w) && !hidden.containsKey(w);
    }
  }

  /**
   * Indicates whether the given {@code Window} is hidden.
   * 
   * @param w the given {@code Window}.
   * @return {@code true} if the given {@code Window} is hidden, {@code false} otherwise.
   */
  boolean isHidden(@Nonnull Window w) {
    synchronized (lock) {
      return hidden.containsKey(w);
    }
  }

  /**
   * Indicates the given {@code Window} is showing but not ready to receive OS-level event input.
   * 
   * @param w the given {@code Window}.
   * @return {@code true} if the given {@code Window} is showing but not not ready to receive OS-level event input,
   *         {@code false} otherwise.
   */
  boolean isShowingButNotReady(@Nonnull Window w) {
    synchronized (lock) {
      return pending.containsKey(w);
    }
  }
}
