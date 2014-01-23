/*
 * Created on Aug 14, 2008
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
package org.fest.swing.edt;

import java.util.concurrent.CountDownLatch;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Action executed in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 */
abstract class GuiAction implements Runnable {
  private boolean executedInEDT;
  private Throwable caughtException;
  private CountDownLatch executionNotification;

  final @Nullable Throwable catchedException() {
    return caughtException;
  }

  final void catchedException(@Nullable Throwable catched) {
    caughtException = catched;
  }

  final boolean wasExecutedInEDT() {
    return executedInEDT;
  }

  final void clearCaughtException() {
    caughtException = null;
  }

  final void executionNotification(@Nonnull CountDownLatch c) {
    executionNotification = c;
  }

  final void notifyExecutionCompleted() {
    executedInEDT();
    if (executionNotification == null) {
      return;
    }
    executionNotification.countDown();
    executionNotification = null;
  }

  private void executedInEDT() {
    executedInEDT = true;
  }
}