/*
 * Created on Jul 30, 2007
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
package org.fest.swing.finder;

import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JButtonFixture;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.WindowLauncher;

/**
 * Base test case for {@link WindowFinder}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class WindowFinder_TestCase extends RobotBasedTestCase {
  private FrameFixture launcher;

  WindowLauncher window;

  @Override
  protected final void onSetUp() {
    window = WindowLauncher.createNew(getClass());
    launcher = new FrameFixture(robot, window);
    extraSetUp();
  }

  void extraSetUp() {}

  final JButtonFixture clickLaunchFrameButton() {
    window.windowLaunchDelay(1);
    return clickLauncherButton("launchFrame");
  }

  final JButtonFixture clickLaunchDialogButton() {
    window.dialogLaunchDelay(1);
    return clickLauncherButton("launchDialog");
  }

  private JButtonFixture clickLauncherButton(String buttonName) {
    launcher.show();
    return launcher.button(buttonName).click();
  }
}
