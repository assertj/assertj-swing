/*
 * Created on Oct 20, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.hierarchy;

import static org.fest.swing.edt.GuiActionRunner.execute;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link ExistingHierarchy#dispose(java.awt.Window)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ExistingHierarchy_dispose_Test extends SequentialEDTSafeTestCase {
  private MyWindow window;
  private ExistingHierarchy hierarchy;

  @Override
  protected void onSetUp() {
    hierarchy = new ExistingHierarchy();
    window = MyWindow.createAndShow(getClass());
  }

  @Override
  protected void onTearDown() {
    window.destroy();
  }

  @Test
  public void should_dispose_Window() {
    window.startRecording();
    hierarchy.dispose(window);
    window.requireInvoked("dispose");
  }

  private static class MyWindow extends TestWindow {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    @RunsInEDT
    static MyWindow createAndShow(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return display(new MyWindow(testClass));
        }
      });
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
    }

    @Override
    public void dispose() {
      if (recording) {
        methodInvocations.invoked("dispose");
      }
      super.dispose();
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  };
}
