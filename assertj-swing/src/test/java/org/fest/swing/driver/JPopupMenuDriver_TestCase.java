/*
 * Created on Sep 5, 2007
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
package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;

/**
 * Base test case for {@link JPopupMenuDriver}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class JPopupMenuDriver_TestCase extends RobotBasedTestCase {
  MyWindow window;
  JPopupMenu popupMenu;
  JPopupMenuDriver driver;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew(getClass());
    popupMenu = window.popupMenu;
    driver = new JPopupMenuDriver(robot);
  }

  static class MyWindow extends TestWindow {
    final JTextField withPopup = new JTextField("With Pop-up Menu");
    final JPopupMenu popupMenu = new JPopupMenu("Pop-up Menu");

    final JMenuItem menuItem1 = new JMenuItem("First");
    final JMenuItem menuItem2 = new JMenuItem("Second");

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      add(withPopup);
      withPopup.setComponentPopupMenu(popupMenu);
      popupMenu.add(menuItem1);
      menuItem1.setName("first");
      popupMenu.add(menuItem2);
    }
  }
}
