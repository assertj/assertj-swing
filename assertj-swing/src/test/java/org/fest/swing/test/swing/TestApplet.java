/*
 * Created on Jun 5, 2008
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
package org.fest.swing.test.swing;

import static javax.swing.SwingUtilities.invokeAndWait;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.FlowLayout;

import javax.annotation.concurrent.GuardedBy;
import javax.swing.JApplet;
import javax.swing.JButton;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * @author Alex Ruiz
 */
public class TestApplet extends JApplet {
  @GuardedBy("this")
  private boolean initialized;
  @GuardedBy("this")
  private boolean destroyed;
  @GuardedBy("this")
  private boolean started;
  @GuardedBy("this")
  private boolean stopped;

  @RunsInEDT
  public static TestApplet createNew() {
    return execute(new GuiQuery<TestApplet>() {
      @Override
      protected TestApplet executeInEDT() {
        return new TestApplet();
      }
    });
  }

  public TestApplet() {}

  @Override
  public void init() {
    try {
      invokeAndWait(new Runnable() {
        @Override
        public void run() {
          setLayout(new FlowLayout());
          JButton button = new JButton("Click Me");
          button.setName("clickMe");
          add(button);
        }
      });
      synchronized (this) {
        initialized = true;
      }
    } catch (Exception e) {
      System.err.println("createGUI didn't successfully complete");
    }
  }

  @Override
  public void start() {
    synchronized (this) {
      started = true;
    }
  }

  @Override
  public void stop() {
    synchronized (this) {
      stopped = true;
    }
  }

  @Override
  public void destroy() {
    synchronized (this) {
      destroyed = true;
    }
  }

  public synchronized boolean initialized() {
    return initialized;
  }

  public synchronized boolean started() {
    return started;
  }

  public synchronized boolean stopped() {
    return stopped;
  }

  public synchronized boolean destroyed() {
    return destroyed;
  }
}
