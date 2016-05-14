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
package org.assertj.swing.edt;

import static javax.swing.SwingUtilities.isEventDispatchThread;
import static org.assertj.swing.exception.ActionFailedException.actionFailure;

import javax.annotation.Nullable;

/**
 * Executes in the event dispatch thread (EDT) an action that returns a value.
 *
 * @param <T> the return type of the action to execute.
 *
 * @author Alex Ruiz
 */
public abstract class GuiQuery<T> extends GuiAction {
  private T result;

  /**
   * Executes the query in the event dispatch thread (EDT). This method waits until the action has finish its execution.
   *
   * @throws org.assertj.swing.exception.ActionFailedException if this task is not executed in the event dispatch thread
   *           (EDT).
   */
  @Override
  public final void run() {
    if (!isEventDispatchThread()) {
      throw actionFailure("Query should be executed in the event dispatch thread");
    }
    try {
      result = executeInEDT();
    } catch (Throwable t) {
      catchedException(t);
    } finally {
      notifyExecutionCompleted();
    }
  }

  /**
   * Specifies the action to execute in the event dispatch thread (EDT).
   *
   * @return the result of the execution of the action.
   * @throws Throwable any error thrown when executing an action in the event dispatch thread (EDT).
   */
  protected abstract @Nullable T executeInEDT() throws Throwable;

  final @Nullable T result() {
    return result;
  }

  final void clearResult() {
    result = null;
  }
}
