/*
 * Created on May 2, 2009
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
package org.fest.swing.core;

import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Frame;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;

/**
 * Disposes all available AWT or Swing {@code Frame}s.
 * 
 * @author Alex Ruiz
 */
class FrameDisposer {
  @RunsInEDT
  void disposeFrames() {
    doDisposeFrames();
  }

  @RunsInEDT
  private static void doDisposeFrames() {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        for (Frame f : Frame.getFrames()) {
          f.dispose();
        }
      }
    });
  }
}
