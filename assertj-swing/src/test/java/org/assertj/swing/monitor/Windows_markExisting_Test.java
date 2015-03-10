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
package org.assertj.swing.monitor;

import static org.assertj.swing.edt.GuiActionRunner.execute;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link Windows#markExisting(java.awt.Window)}.
 * 
 * @author Alex Ruiz
 */
public class Windows_markExisting_Test extends Windows_TestCase {
  @Test
  public void should_Mark_Visible_Window_As_Ready_And_Not_Hidden() {
    window.display();
    markExisting(windows, window);
    windowState().isReady();
  }

  @Test
  public void should_Mark_Not_Visible_Window_As_Ready_And_Hidden() {
    pack(window);
    markExisting(windows, window);
    windowState().isOpen().isNotClosed().isHidden().isNotPending();
  }

  @RunsInEDT
  private static void markExisting(final Windows windows, final TestWindow window) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        windows.markExisting(window);
      }
    });
  }

  @RunsInEDT
  private static void pack(final TestWindow window) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        window.pack();
      }
    });
  }
}
