/*
 * Created on Jul 31, 2009
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
package org.fest.swing.monitor;

import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Toolkit;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestWindow;
import org.junit.After;
import org.junit.BeforeClass;

/**
 * Base test case for {@link WindowEventQueueMapping} that require a {@link java.awt.Window} for their execution.
 * 
 * @author Alex Ruiz
 */
public abstract class WindowEventQueueMapping_withWindow_TestCase extends WindowEventQueueMapping_TestCase {
  MyWindow window;

  @BeforeClass
  public static void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @Override
  final void onSetUp() {
    ScreenLock.instance().acquire(this);
    window = MyWindow.createNew(toolkit, getClass());
  }

  @After
  public final void tearDown() {
    try {
      window.destroy();
    } finally {
      ScreenLock.instance().release(this);
    }
  }

  static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew(final Toolkit toolkit, final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(toolkit, testClass);
        }
      });
    }

    private final Toolkit toolkit;

    private final boolean setUpComplete;

    private MyWindow(Toolkit toolkit, Class<?> testClass) {
      super(testClass);
      this.toolkit = toolkit;
      setUpComplete = true;
    }

    @Override
    public Toolkit getToolkit() {
      if (setUpComplete) {
        return toolkit;
      }
      return super.getToolkit();
    }
  }
}
