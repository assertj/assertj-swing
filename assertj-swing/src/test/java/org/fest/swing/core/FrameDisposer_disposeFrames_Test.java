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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Lists.newArrayList;

import java.util.List;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link FrameDisposer#disposeFrames()}.
 *
 * @author Alex Ruiz
 */
public class FrameDisposer_disposeFrames_Test extends SequentialEDTSafeTestCase {
  private MyWindow[] windows;
  private FrameDisposer disposer;

  @Override
  protected void onSetUp() {
    windows = MyWindow.windows();
    disposer = new FrameDisposer();
  }

  @Override
  protected void onTearDown() {
    for (MyWindow w : windows) {
      w.destroy();
    }
  }

  @Test
  public void shouldDisposeFrames() {
    disposer.disposeFrames();
    for (MyWindow w : windows) {
      assertThat(w.disposed).isTrue();
    }
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow[] windows() {
      final List<MyWindow> windows = newArrayList();
      execute(new GuiTask() {
        @Override
        protected void executeInEDT() {
          for (int i = 0; i < 3; i++) {
            MyWindow w = new MyWindow();
            display(w);
            windows.add(w);
          }
        }
      });
      return windows.toArray(new MyWindow[windows.size()]);
    }

    boolean disposed;

    private MyWindow() {
      super(FrameDisposer_disposeFrames_Test.class);
    }

    @Override
    public void dispose() {
      disposed = true;
      super.dispose();
    }
  }
}
