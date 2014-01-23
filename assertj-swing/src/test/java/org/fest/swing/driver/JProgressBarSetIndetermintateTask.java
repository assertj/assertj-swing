/*
 * Created on Dec 4, 2009
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
package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.swing.JProgressBar;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;

/**
 * Sets the property "indeterminate" in a {@code JProgressBar}. This task is executed in the event dispatch thread
 * (EDT.)
 * 
 * @author Alex Ruiz
 */
final class JProgressBarSetIndetermintateTask {
  @RunsInEDT
  static void setIntedeterminate(final @Nonnull JProgressBar progressBar, final boolean indeterminate) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        progressBar.setIndeterminate(indeterminate);
      }
    });
  }

  private JProgressBarSetIndetermintateTask() {}
}