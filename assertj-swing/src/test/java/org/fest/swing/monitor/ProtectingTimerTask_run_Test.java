/*
 * Created on Mar 30, 2008
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
package org.fest.swing.monitor;

import static org.fest.assertions.Assertions.assertThat;

import java.util.TimerTask;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link ProtectingTimerTask#run()}.
 * 
 * @author Alex Ruiz
 */
public class ProtectingTimerTask_run_Test {
  private MyTimerTask underProtection;
  private ProtectingTimerTask protecting;

  @Before
  public void setUp() {
    underProtection = new MyTimerTask();
    protecting = new ProtectingTimerTask(underProtection);
  }

  @Test
  public void shouldExecuteTaskUnderProtection() {
    protecting.run();
    assertThat(underProtection.executed).isTrue();
  }

  @Test
  public void shouldIgnoreExceptionsFromTaskUnderProtection() {
    underProtection.exceptionToThrow = new RuntimeException();
    protecting.run();
    assertThat(underProtection.executed).isTrue();
  }

  @Test
  public void shouldNotExecuteIfTaskUnderProtectionIsCancelled() {
    underProtection.cancel();
    protecting.run();
    assertThat(underProtection.executed).isFalse();
  }

  private static class MyTimerTask extends TimerTask {
    boolean executed;
    RuntimeException exceptionToThrow;

    MyTimerTask() {
    }

    @Override
    public void run() {
      executed = true;
      if (exceptionToThrow != null) {
        throw exceptionToThrow;
      }
    }
  }
}
