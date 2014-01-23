/*
 * Created on Oct 29, 2007
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
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.JOptionPaneLauncherWindow;

/**
 * Base test case for {@link JOptionPaneFinder}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class JOptionPaneFinder_TestCase extends RobotBasedTestCase {
  private FrameFixture frameFixture;

  JOptionPaneLauncherWindow window;

  @Override
  protected final void onSetUp() {
    window = JOptionPaneLauncherWindow.createNew(JOptionPaneFinder_TestCase.class);
    frameFixture = new FrameFixture(robot, window);
    extraSetUp();
  }

  void extraSetUp() {}

  final void clickMessageButton() {
    frameFixture.show();
    frameFixture.button("message").click();
  }
}