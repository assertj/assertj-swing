/*
 * Created on Mar 29, 2008
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
package org.fest.swing.input;

import static org.fest.swing.listener.WeakEventListener.attachAsWeakEventListener;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;

import javax.annotation.Nonnull;

import org.fest.swing.listener.WeakEventListener;
import org.fest.util.VisibleForTesting;

/**
 * AWT event listener which normalizes the event stream by sending a single {@code WINDOW_CLOSED}, instead of one every
 * time dispose is called.
 * 
 * @author Alex Ruiz
 */
public class EventNormalizer implements AWTEventListener {
  private final DisposedWindowMonitor disposedWindowMonitor;

  private WeakEventListener weakEventListener;
  private AWTEventListener listener;

  /**
   * Creates a new {@link EventNormalizer}.
   */
  public EventNormalizer() {
    this(new DisposedWindowMonitor());
  }

  @VisibleForTesting
  EventNormalizer(@Nonnull DisposedWindowMonitor disposedWindowMonitor) {
    this.disposedWindowMonitor = disposedWindowMonitor;
  }

  /**
   * Starts listening for events.
   * 
   * @param toolkit the {@code Toolkit} to use.
   * @param delegate the event listener to delegate event processing to.
   * @param mask the event mask to use to register this normalizer in the {@code Toolkit}.
   */
  public void startListening(@Nonnull Toolkit toolkit, @Nonnull AWTEventListener delegate, long mask) {
    listener = delegate;
    weakEventListener = attachAsWeakEventListener(toolkit, this, mask);
  }

  /**
   * Stops listening for events and disposes the delegate event listener.
   */
  public void stopListening() {
    disposeWeakEventListener();
    listener = null;
  }

  private void disposeWeakEventListener() {
    if (weakEventListener == null) {
      return;
    }
    weakEventListener.dispose();
    weakEventListener = null;
  }

  /**
   * Event reception callback.
   * 
   * @param event the dispatched event.
   */
  @Override
  public void eventDispatched(AWTEvent event) {
    boolean discard = disposedWindowMonitor.isDuplicateDispose(event);
    if (!discard && listener != null) {
      delegate(event);
    }
  }

  private void delegate(@Nonnull AWTEvent e) {
    listener.eventDispatched(e);
  }
}