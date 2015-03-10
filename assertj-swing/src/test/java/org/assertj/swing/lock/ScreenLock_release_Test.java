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

import org.assertj.swing.exception.ScreenLockException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link ScreenLock#release(Object)}.
 * 
 * @author Alex Ruiz
 */
public class ScreenLock_release_Test {
  private ScreenLock lock;
  private Object owner;

  @Before
  public void setUp() {
    lock = new ScreenLock();
    owner = new Object();
  }

  @After
  public void tearDown() {
    if (lock.acquiredBy(owner)) {
      lock.release(owner);
    }
  }

  @Test(expected = ScreenLockException.class)
  public void should_Throw_Error_If_Trying_To_Release_With_Different_Owner() {
    lock.acquire(owner);
    lock.release(new Object());
  }

  @Test(expected = ScreenLockException.class)
  public void should_Throw_Error_If_Trying_To_Release_Without_Being_Locked() {
    lock.release(owner);
  }
}
