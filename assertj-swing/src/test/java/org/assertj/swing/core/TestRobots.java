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
package org.assertj.swing.core;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Implementations of {@link Robot} to be used for testing.
 *
 * @author Alex Ruiz
 */
public final class TestRobots {
  public static Robot singletonRobotMock() {
    return LazyLoadedSingleton.INSTANCE;
  }

  private static class LazyLoadedSingleton {
    static final Robot INSTANCE = newRobotMock();
  }

  public static Robot newRobotMock() {
    final Robot mock = mock(Robot.class);
    doAnswer(new Answer<Object>(){
      @Override
      public Object answer(final InvocationOnMock invocation) throws Throwable {
        invocation.<Runnable> getArgument(1).run();
        return null;
      }
    }).when(mock).pressKeyWhileRunning(ArgumentMatchers.anyInt(), ArgumentMatchers.anyObject());

    return mock;
  }

  private TestRobots() {}
}
