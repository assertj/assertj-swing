/*
 * Created on Jan 17, 2009
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
package org.fest.swing.testing;

import javax.annotation.Nonnull;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;

/**
 * Template for test cases that use FEST-Swing.
 *
 * @since 1.1
 *
 * @author Alex Ruiz
 */
public abstract class FestSwingTestCaseTemplate {
  private Robot robot;

  public FestSwingTestCaseTemplate() {
    robot = null; // Just to satisfy FindBugs
  }

  /**
   * Creates this test's {@link Robot} using a new AWT hierarchy.
   */
  protected final void setUpRobot() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
  }

  /**
   * Cleans up resources used by this test's {@link Robot}.
   */
  protected final void cleanUp() {
    robot.cleanUp();
  }

  /**
   * @return this test's {@link Robot}
   */
  protected final @Nonnull Robot robot() {
    return robot;
  }
}
