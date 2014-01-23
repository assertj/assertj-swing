/*
 * Created on Jul 26, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.test.task;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.swing.ComponentHasFocusCondition.untilFocused;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.swing.timing.Timeout.timeout;

import java.awt.Component;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;

/**
 * Requests input focus for a {@code Component}. This task is executed in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 */
public final class ComponentRequestFocusAndWaitForFocusGainTask {
  @RunsInEDT
  public static void giveFocusAndWaitTillIsFocused(final @Nonnull Component c) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        c.requestFocus();
      }
    });
    waitTillHasFocus(c);
  }

  @RunsInEDT
  public static void waitTillHasFocus(final @Nonnull Component c) {
    pause(untilFocused(c), timeout(500));
  }

  private ComponentRequestFocusAndWaitForFocusGainTask() {}
}
