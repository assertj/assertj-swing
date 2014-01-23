/*
 * Created on Feb 24, 2008
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JScrollBarValueQuery.valueOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;

import java.awt.Dimension;

import javax.swing.JScrollBar;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;

/**
 * Base test case for {@link JScrollBarDriver}.
 * 
 * @author Alex Ruiz
 */
public abstract class JScrollBarDriver_TestCase extends RobotBasedTestCase {
  static final int MINIMUM = 10;
  static final int MAXIMUM = 80;
  static final int EXTENT = 10;

  JScrollBarDriver driver;
  MyWindow window;
  JScrollBar scrollBar;

  @Override
  protected final void onSetUp() {
    driver = new JScrollBarDriver(robot);
    window = MyWindow.createNew(getClass());
    scrollBar = window.scrollBar;
  }

  final void showWindow() {
    robot.showWindow(window);
  }

  @RunsInEDT
  final void assertThatScrollBarValueIs(int expected) {
    assertThat(valueOf(scrollBar)).isEqualTo(expected);
  }

  @RunsInEDT
  final void disableScrollBar() {
    disable(scrollBar);
    robot.waitForIdle();
  }

  static class MyWindow extends TestWindow {
    final JScrollBar scrollBar = new JScrollBar();

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
      add(scrollBar);
      scrollBar.setPreferredSize(new Dimension(20, 100));
      scrollBar.setBlockIncrement(EXTENT);
      scrollBar.setValue(30);
      scrollBar.setMinimum(MINIMUM);
      scrollBar.setMaximum(MAXIMUM);
      setPreferredSize(new Dimension(60, 200));
    }
  }
}
