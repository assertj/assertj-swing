/*
 * Created on Jul 19, 2008
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
package org.fest.swing.core;

import static org.fest.swing.util.AWTExceptionHandlerInstaller.installAWTExceptionHandler;

import javax.annotation.Nonnull;

/**
 * Terminates any running FEST-Swing tests.
 * 
 * @author <a href="mailto:simeon.fitch@mseedsoft.com">Simeon H.K. Fitch</a>
 */
class TestTerminator {
  private final ThreadsSource threadsSource;
  private final FrameDisposer frameDisposer;
  private final MainThreadIdentifier mainThreadIdentifier;

  TestTerminator() {
    this(new ThreadsSource(), new FrameDisposer(), new MainThreadIdentifier());
  }

  TestTerminator(@Nonnull ThreadsSource threadsSource, @Nonnull FrameDisposer frameDisposer,
      @Nonnull MainThreadIdentifier mainThreadIdentifier) {
    this.threadsSource = threadsSource;
    this.frameDisposer = frameDisposer;
    this.mainThreadIdentifier = mainThreadIdentifier;
  }

  /*
   * We do three things to signal an abort.
   * 1) sent an interrupt signal to main thread
   * 2) dispose all available frames.
   * 3) throw RuntimeException on AWT event thread
   */
  void terminateTests() {
    pokeMainThread();
    frameDisposer.disposeFrames();
    throw new RuntimeException("User aborted FEST-Swing tests");
  }

  /*
   * Calls {@link Thread#interrupt()} on main thread in attempt to interrupt current FEST operation. Only affects thread
   * if it is in a {@link Object#wait()} or {@link Thread#sleep(long)} method.
   */
  private void pokeMainThread() {
    Thread mainThread = mainThreadIdentifier.mainThreadIn(threadsSource.allThreads());
    if (mainThread != null) {
      mainThread.interrupt();
    }
  }

  static {
    // Make sure there's an exception handler that will dump a stack trace on abort.
    installAWTExceptionHandler(SimpleFallbackExceptionHandler.class);
  }
}
