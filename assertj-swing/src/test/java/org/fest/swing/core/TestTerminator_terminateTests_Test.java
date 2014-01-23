/*
 * Created on May 2, 2009
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
package org.fest.swing.core;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.fest.test.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link TestTerminator#terminateTests()}.
 *
 * @author Alex Ruiz
 */
public class TestTerminator_terminateTests_Test {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private ThreadsSource threadsSource;
  private FrameDisposer frameDisposer;
  private MainThreadIdentifier mainThreadIdentifier;
  private TestTerminator terminator;

  @Before
  public void setUp() {
    threadsSource = mock(ThreadsSource.class);
    frameDisposer = mock(FrameDisposer.class);
    mainThreadIdentifier = mock(MainThreadIdentifier.class);
    terminator = new TestTerminator(threadsSource, frameDisposer, mainThreadIdentifier);
  }

  @Test
  public void should_terminate_GUI_tests() {
    Thread mainThread = mock(Thread.class);
    Thread[] allThreads = { mainThread };
    when(threadsSource.allThreads()).thenReturn(allThreads);
    when(mainThreadIdentifier.mainThreadIn(allThreads)).thenReturn(mainThread);
    terminateTestsAndCheckExpectedException();
    verify(mainThread).interrupt();
    verify(frameDisposer).disposeFrames();
  }

  @Test
  public void should_not_throw_error_if_main_thread_not_found() {
    Thread[] allThreads = new Thread[0];
    when(threadsSource.allThreads()).thenReturn(allThreads);
    when(mainThreadIdentifier.mainThreadIn(allThreads)).thenReturn(null);
    terminateTestsAndCheckExpectedException();
    verify(frameDisposer).disposeFrames();
  }

  private void terminateTestsAndCheckExpectedException() {
    thrown.expect(RuntimeException.class, "User aborted FEST-Swing tests");
    terminator.terminateTests();
  }
}
