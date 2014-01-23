/*
 * Created on Dic 1, 2009
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

import static org.fest.swing.driver.JProgressBarSetIndetermintateTask.setIntedeterminate;
import static org.fest.swing.driver.JProgressBarSetValueTask.setValue;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;

import javax.swing.JProgressBar;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;

/**
 * Base test case for {@link JProgressBarDriver}.
 * 
 * @author Alex Ruiz
 */
public abstract class JProgressBarDriver_TestCase extends RobotBasedTestCase {
  JProgressBar progressBar;
  JProgressBarDriver driver;

  @Override
  protected final void onSetUp() {
    driver = new JProgressBarDriver(robot);
    MyWindow window = MyWindow.createNew(getClass());
    progressBar = window.progressBar;
  }

  @RunsInEDT
  final void updateValueTo(int value) {
    setValue(progressBar, value);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void makeIndeterminate() {
    setIntedeterminate(progressBar, true);
    robot.waitForIdle();
  }

  static class MyWindow extends TestWindow {
    final JProgressBar progressBar = new JProgressBar();

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
      progressBar.setValue(60);
      progressBar.setString("60%");
      progressBar.setStringPainted(true);
      add(progressBar);
      setPreferredSize(new Dimension(100, 50));
    }
  }
}
