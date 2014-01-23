/*
 * Created on May 24, 2007
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
package org.fest.swing.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import org.fest.swing.exception.ScreenLockException;

/**
 * A lock that each GUI test should acquire before being executed, to guarantee sequential execution of GUI tests and to
 * prevent GUI tests from blocking each other.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@ThreadSafe
public final class ScreenLock {
  private final Lock lock = new ReentrantLock();
  private final Condition released = lock.newCondition();

  @GuardedBy("lock")
  private Object owner;

  @GuardedBy("lock")
  private boolean acquired;

  /**
   * Acquires this lock. If this lock was already acquired by another object, this method will block until the lock is
   * released.
   * 
   * @param newOwner the new owner of the lock.
   */
  public void acquire(@Nonnull Object newOwner) {
    lock.lock();
    try {
      if (alreadyAcquiredBy(newOwner)) {
        return;
      }
      while (acquired) {
        released.await();
      }
      owner = newOwner;
      acquired = true;
    } catch (InterruptedException ignored) {
      Thread.currentThread().interrupt();
    } finally {
      lock.unlock();
    }
  }

  /**
   * Releases this lock.
   * 
   * @param currentOwner the current owner of the lock.
   * @throws ScreenLockException if the lock has not been previously acquired.
   * @throws ScreenLockException if the given owner is not the same as the current owner of the lock.
   */
  public void release(@Nonnull Object currentOwner) {
    lock.lock();
    try {
      if (!acquired) {
        throw new ScreenLockException("No lock to release");
      }
      if (owner != currentOwner) {
        throw new ScreenLockException(String.format("%s is not the lock owner", currentOwner.toString()));
      }
      acquired = false;
      owner = null;
      released.signal();
    } finally {
      lock.unlock();
    }
  }

  /**
   * Indicates whether this lock was acquired by the given object.
   * 
   * @param possibleOwner the given object, which could be owning the lock.
   * @return {@code true} if the given object is owning the lock; {@code false} otherwise.
   */
  public boolean acquiredBy(@Nonnull Object possibleOwner) {
    lock.lock();
    try {
      return alreadyAcquiredBy(possibleOwner);
    } finally {
      lock.unlock();
    }
  }

  private boolean alreadyAcquiredBy(@Nonnull Object possibleOwner) {
    return acquired && owner == possibleOwner;
  }

  /**
   * Indicates whether this lock is already acquired.
   * 
   * @return {@code true} if the lock is already acquired; {@code false} otherwise.
   * @see #acquiredBy(Object)
   * @since 1.2
   */
  public boolean acquired() {
    lock.lock();
    try {
      return acquired;
    } finally {
      lock.unlock();
    }
  }

  /**
   * @return the singleton instance of this class.
   */
  public static @Nonnull ScreenLock instance() {
    return ScreenLockHolder.instance;
  }

  private static class ScreenLockHolder {
    static ScreenLock instance = new ScreenLock();
  }

  ScreenLock() {}
}
