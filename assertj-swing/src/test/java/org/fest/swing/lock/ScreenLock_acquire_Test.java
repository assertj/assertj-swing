/*
 * Created on Mar 17, 2010
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
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.lock;

import static edu.umd.cs.mtc.TestFramework.runOnce;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;

/**
 * Tests for {@link ScreenLock#acquire(Object)}.
 * 
 * @author Alex Ruiz
 */
public class ScreenLock_acquire_Test extends MultithreadedTestCase {
  private ScreenLock lock;
  private Object owner;

  @Override
  public void initialize() {
    lock = new ScreenLock();
    owner = new Object();
  }

  public void thread1() {
    lock.acquire(owner);
    lock.acquire(owner);
    assertThat(lock.acquiredBy(owner));
  }

  @Override
  public void finish() {
    if (lock.acquiredBy(owner)) {
      lock.release(owner);
    }
  }

  @Test
  public void should_not_block_if_current_owner_tries_to_acquire_lock_again() throws Throwable {
    runOnce(new ScreenLock_acquire_Test());
  }
}
