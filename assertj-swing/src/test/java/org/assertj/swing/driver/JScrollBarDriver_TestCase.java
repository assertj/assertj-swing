/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.driver.JScrollBarValueQuery.valueOf;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.task.ComponentSetEnabledTask.disable;

import java.awt.Dimension;

import javax.swing.JScrollBar;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;

/**
 * Base test case for {@link JScrollBarDriver}.
 * 
 * @author Alex Ruiz
 */
public abstract class JScrollBarDriver_TestCase extends RobotBasedTestCase {
  static final int MINIMUM = 10;
  static final int MAXIMUM = 80;
  static final int EXTENT = 10;

  @Rule
  public ExpectedException thrown = none();

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
