/*
 * Created on Jul 17, 2009
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
package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JButton;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;

/**
 * Base test case for {@link JComponentDriver}.
 * 
 * @author Alex Ruiz
 */
public class JComponentDriver_TestCase extends RobotBasedTestCase {
  MyWindow window;
  JButton button;
  JComponentDriver driver;

  @Override
  protected final void onSetUp() {
    driver = new JComponentDriver(robot);
    window = MyWindow.createNew(getClass());
    button = window.button;
  }

  static class MyWindow extends TestWindow {
    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    final JButton button = new JButton("Click Me");

    private MyWindow(Class<?> testClass) {
      super(testClass);
      button.setToolTipText("A ToolTip");
      addComponents(button);
    }
  }
}