/*
 * Created on Aug 8, 2008
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
package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;

import java.beans.PropertyVetoException;

import javax.annotation.Nonnull;
import javax.swing.JInternalFrame;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;

/**
 * Maximizes or restores a {@code JInternalFrame}. This task is executed in the event dispatch thread (EDT.)
 * 
 * @author Yvonne Wang
 */
final class JInternalFrameSetMaximumTask {
  @RunsInEDT
  static void setMaximum(final @Nonnull JInternalFrame internalFrame, final @Nonnull JInternalFrameAction action) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws PropertyVetoException {
        internalFrame.setIcon(false);
        internalFrame.setMaximum(action.value);
      }
    });
  }

  private JInternalFrameSetMaximumTask() {}
}