/*
 * Created on Aug 11, 2008
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

import static javax.swing.SwingUtilities.isEventDispatchThread;

import org.fest.swing.exception.ActionFailedException;

/**
 * Task that should be executed in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 */
public abstract class GuiTask extends GuiAction {
  /**
   * Runs this task action and verifies that it is executed in the event dispatch thread (EDT.)
   * 
   * @throws ActionFailedException if this task was not executed in the EDT.
   */
  @Override
  public final void run() {
    if (!isEventDispatchThread()) {
      throw ActionFailedException.actionFailure("Task should be executed in the event dispatch thread");
    }
    try {
      executeInEDT();
    } catch (Throwable t) {
      catchedException(t);
    } finally {
      notifyExecutionCompleted();
    }
  }

  /**
   * Specifies the action to execute in the event dispatch thread (EDT.)
   * 
   * @throws Throwable any error thrown when executing an action in the event dispatch thread (EDT.)
   */
  protected abstract void executeInEDT() throws Throwable;
}
