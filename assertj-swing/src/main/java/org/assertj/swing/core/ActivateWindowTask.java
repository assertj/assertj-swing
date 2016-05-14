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
package org.assertj.swing.core;

import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Window;

import javax.annotation.Nonnull;

import org.assertj.swing.annotation.RunsInEDT;

/**
 * Activates an AWT {@code Window}. "Activate" means that the given window is brought to the front and then gets the
 * keyboard focus.
 *
 * @author Alex Ruiz
 */
class ActivateWindowTask {
  @RunsInEDT
  static void activateWindow(final @Nonnull Window w) {
    execute(() -> {
      w.toFront();
      w.requestFocusInWindow();
    });
  }

  private ActivateWindowTask() {
  }
}
