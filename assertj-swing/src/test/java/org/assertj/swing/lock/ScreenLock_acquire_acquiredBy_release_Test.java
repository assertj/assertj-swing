/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.lock;

import static edu.umd.cs.mtc.TestFramework.runManyTimes;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;

/**
 * Tests for {@link ScreenLock#acquire(Object)}, {@link ScreenLock#acquiredBy(Object)} and
 * {@link ScreenLock#release(Object)}.
 * 
 * @author Alex Ruiz
 */
public class ScreenLock_acquire_acquiredBy_release_Test extends MultithreadedTestCase {
  private LockOwner owner1;
  private LockOwner owner2;
  private ScreenLock lock;

  @Override
  public void initialize() {
    owner1 = new LockOwner("Owner #1");
    owner2 = new LockOwner("Owner #2");
    lock = new ScreenLock();
  }

  public void thread1() {
    lock.acquire(owner1);
    assertThat(lock.acquired()).isTrue();
    assertThat(lock.acquiredBy(owner1)).isTrue();
    waitForTick(2);
    lock.release(owner1);
  }

  public void thread2() {
    waitForTick(1);
    lock.acquire(owner2);
  }

  @Override
  public void finish() {
    assertThat(lock.acquiredBy(owner2)).isTrue();
    lock.release(owner2);
    assertThat(lock.acquired()).isFalse();
  }

  @Test
  public void should_Acquire_Lock_And_Queue_Others_Wanting_Lock() throws Throwable {
    runManyTimes(new ScreenLock_acquire_acquiredBy_release_Test(), 5);
  }

  private static class LockOwner {
    private final String name;

    LockOwner(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return name;
    }
  }
}
