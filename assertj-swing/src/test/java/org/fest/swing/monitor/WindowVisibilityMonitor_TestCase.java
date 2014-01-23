/*
 * Created on Oct 10, 2007
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
package org.fest.swing.monitor;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.monitor.TestWindows.newWindowsMock;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.fest.swing.test.core.MethodInvocations.Args.args;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowListener;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.MethodInvocations.Args;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;

/**
 * Tests for {@link WindowVisibilityMonitor}.
 * 
 * @author Alex Ruiz
 */
public abstract class WindowVisibilityMonitor_TestCase extends SequentialEDTSafeTestCase {
  MyWindow window;
  Windows windows;
  WindowVisibilityMonitor monitor;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew();
    windows = newWindowsMock();
    monitor = new WindowVisibilityMonitor(windows);
  }

  @Override
  protected final void onTearDown() {
    window.destroy();
  }

  final ComponentEvent componentEventWithWindowAsSource() {
    return componentEvent(window);
  }

  @RunsInEDT
  final ComponentEvent componentEventWithTextFieldAsSource() {
    return componentEvent(textField().createNew());
  }

  private ComponentEvent componentEvent(Component source) {
    return new ComponentEvent(source, 8);
  }

  static class MyWindow extends TestWindow {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(WindowVisibilityMonitor_TestCase.class);
    }

    void startRecording() {
      recording = true;
    }

    @Override
    public synchronized void removeWindowListener(WindowListener l) {
      if (recording) {
        methodInvocations.invoked("removeWindowListener", args(l));
      }
      super.removeWindowListener(l);
    }

    @Override
    public synchronized void removeComponentListener(ComponentListener l) {
      if (recording) {
        methodInvocations.invoked("removeComponentListener", args(l));
      }
      super.removeComponentListener(l);
    }

    MethodInvocations requireInvoked(String methodName, Args args) {
      return methodInvocations.requireInvoked(methodName, args);
    }
  }
}
