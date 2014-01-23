/*
 * Created on Nov 12, 2007
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

import static java.awt.AWTEvent.COMPONENT_EVENT_MASK;
import static java.awt.AWTEvent.WINDOW_EVENT_MASK;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.awt.Toolkits.newToolkitStub;
import static org.fest.util.Arrays.array;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.awt.ToolkitStub;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;

/**
 * Base test case for {@link NewHierarchy}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class NewHierarchy_TestCase extends SequentialEDTSafeTestCase {
  static final long EVENT_MASK = WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK;

  ToolkitStub toolkit;
  WindowFilter filter;
  MyWindow window;

  @Override
  protected final void onSetUp() {
    toolkit = newToolkitStub();
    window = MyWindow.createNew(getClass());
    filter = new WindowFilter();
  }

  @Override
  protected final void onTearDown() {
    window.destroy();
  }

  // TODO Test method dispose(Window)

  static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    final JComboBox comboBox = new JComboBox(array("One", "Two"));
    final JTextField textField = new JTextField(20);

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(comboBox, textField);
    }
  }
}
