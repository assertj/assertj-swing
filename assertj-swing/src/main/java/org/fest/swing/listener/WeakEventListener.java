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
package org.fest.swing.listener;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.lang.ref.WeakReference;

import javax.annotation.Nonnull;

import org.fest.util.VisibleForTesting;

/**
 * Event listener that wraps a given {@code AWTEventListener} and:
 * <ul>
 * <li>attaches itself to the default toolkit</li>
 * <li>dispatches any given event to the wrapped listener</li>
 * <li>removes itself from the default toolkit when the wrapped listener gets garbage-collected</li>
 * </ul>
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class WeakEventListener implements AWTEventListener {
  private final WeakReference<AWTEventListener> listenerReference;
  private final Toolkit toolkit;

  /**
   * Creates a new {@link WeakEventListener} and adds it to the given {@code Toolkit} using the given event mask. The
   * created {@link WeakEventListener} simply "decorates" the given {@code AWTEventListener}. All events dispatched to
   * the {@link WeakEventListener} are re-routed to the underlying {@code AWTEventListener}. When the underlying
   * {@code AWTEventListener} is garbage-collected, the {@link WeakEventListener} will remove itself from the toolkit.
   * 
   * @param toolkit the given AWT {@code Toolkit}.
   * @param listener the underlying listener to wrap.
   * @param eventMask the event mask to use to attach the {@code WeakEventListener} to the toolkit.
   * @return the created {@code WeakEventListener}.
   */
  public static @Nonnull WeakEventListener attachAsWeakEventListener(@Nonnull Toolkit toolkit,
      @Nonnull AWTEventListener listener, long eventMask) {
    WeakEventListener l = new WeakEventListener(toolkit, listener);
    toolkit.addAWTEventListener(l, eventMask);
    return l;
  }

  private WeakEventListener(@Nonnull Toolkit toolkit, @Nonnull AWTEventListener listener) {
    listenerReference = new WeakReference<AWTEventListener>(listener);
    this.toolkit = toolkit;
  }

  /**
   * @return the underlying listener.
   */
  public @Nonnull AWTEventListener underlyingListener() {
    return listenerReference.get();
  }

  /**
   * Dispatches the given event to the wrapped event listener. If the wrapped listener is {@code null}
   * (garbage-collected,) this listener will remove itself from the default toolkit.
   * 
   * @param e the event dispatched in the AWT.
   */
  @Override
  public void eventDispatched(AWTEvent e) {
    AWTEventListener listener = listenerReference.get();
    if (listener == null) {
      dispose();
      return;
    }
    listener.eventDispatched(e);
  }

  /** Removes itself from the {@code Toolkit} this listener is attached to. */
  public void dispose() {
    toolkit.removeAWTEventListener(this);
  }

  /**
   * Removes the wrapped listener from the {@link WeakReference} (to simulate garbage collection). This method should be
   * used only for <strong>testing only</strong>.
   */
  @VisibleForTesting
  void simulateUnderlyingListenerIsGarbageCollected() {
    listenerReference.clear();
  }
}
