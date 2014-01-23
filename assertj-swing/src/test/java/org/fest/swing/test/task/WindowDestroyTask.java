/*
 * Created on Sep 9, 2008
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
package org.fest.swing.test.task;

import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Window;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;

/**
 * Hides and disposes a {@code Window}.
 * 
 * @author Alex Ruiz
 */
public final class WindowDestroyTask {
  /**
   * Hides and disposes the given {@code Window}. This action is executed in the event dispatch thread (EDT.)
   * 
   * @param w the {@code Window} to hide and dispose.
   */
  @RunsInEDT
  public static void hideAndDisposeInEDT(final @Nullable Window w) {
    if (w == null) {
      return;
    }
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        hideAndDispose(w);
      }
    });
  }

  /**
   * <p>
   * Hides and disposes the given {@code Window}.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param w the {@code Window} to hide and dispose.
   */
  @RunsInCurrentThread
  public static void hideAndDispose(@Nonnull Window w) {
    w.setVisible(false);
    w.dispose();
  }

  private WindowDestroyTask() {}
}
