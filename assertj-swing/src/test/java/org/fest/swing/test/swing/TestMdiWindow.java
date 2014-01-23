/*
 * Created on Oct 25, 2007
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
package org.fest.swing.test.swing;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Strings.concat;

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * An MDI frame.
 *
 * @author Alex Ruiz
 */
public class TestMdiWindow extends TestWindow {
  private static int internalFrameCounter;

  /**
   * Creates a new {@link TestMdiWindow} and displays it on the screen. The default size of the created window is 500 x
   * 300. This method is executed in the event dispatch thread (EDT.)
   *
   * @param testClass the class of the test where the window to create will be used. The simple name of the given class
   *          will be used as the title of the created window.
   * @return the created window.
   */
  @RunsInEDT
  public static TestMdiWindow createAndShowNewWindow(final Class<?> testClass) {
    return execute(new GuiQuery<TestMdiWindow>() {
      @Override
      protected TestMdiWindow executeInEDT() {
        TestMdiWindow window = createInCurrentThread(testClass);
        TestWindow.display(window, new Dimension(500, 300));
        return window;
      }
    });
  }

  /**
   * Creates a new {@link TestMdiWindow}. This method is executed in the event dispatch thread (EDT.)
   *
   * @param testClass the class of the test where the window to create will be used. The simple name of the given class
   *          will be used as the title of the created window.
   * @return the created window.
   */
  @RunsInEDT
  public static TestMdiWindow createNewWindow(final Class<?> testClass) {
    return execute(new GuiQuery<TestMdiWindow>() {
      @Override
      protected TestMdiWindow executeInEDT() {
        return createInCurrentThread(testClass);
      }
    });
  }

  private final JDesktopPane desktop;
  private final JInternalFrame internalFrame;

  @RunsInCurrentThread
  private static TestMdiWindow createInCurrentThread(Class<?> testClass) {
    return new TestMdiWindow(testClass);
  }

  /**
   * Creates a new {@link TestMdiWindow}.
   *
   * @param testClass the class of the test where the window to create will be used. The simple name of the given class
   *          will be used as the title of the created window.
   */
  @RunsInCurrentThread
  protected TestMdiWindow(Class<?> testClass) {
    super(testClass);
    desktop = new JDesktopPane();
    internalFrame = createInternalFrame();
    desktop.add(internalFrame);
    setContentPane(desktop);
    setPreferredSize(new Dimension(500, 300));
  }

  /**
   * Creates a new {@code JInternalFrame} with a size of 200 x 100. The title of the created internal frame includes the
   * value of a counter that is automatically increased every time a new internal frame is created. This method is
   * {@code not} executed in the event dispatch thread (EDT.)
   *
   * @return the created frame.
   */
  @RunsInCurrentThread
  protected static JInternalFrame createInternalFrame() {
    // TODO: should be create and add
    JInternalFrame internalFrame = new JInternalFrame(concat("Internal Frame ", ++internalFrameCounter));
    internalFrame.setIconifiable(true);
    internalFrame.setMaximizable(true);
    internalFrame.setResizable(true);
    internalFrame.setSize(new Dimension(200, 100));
    internalFrame.setVisible(true);
    return internalFrame;
  }

  /**
   * Returns the container containing multiple documents (internal frames.)
   *
   * @return the container containing multiple documents (internal frames.)
   */
  public JDesktopPane desktop() {
    return desktop;
  }

  /**
   * Returns the internal frame created by default.
   *
   * @return the internal frame created by default.
   */
  public JInternalFrame internalFrame() {
    return internalFrame;
  }
}
