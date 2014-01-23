/*
 * Created on Jul 26, 2009
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
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.query.ComponentShowingQuery.isShowing;
import static org.fest.swing.query.ComponentVisibleQuery.isVisible;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;
import static org.fest.swing.timing.Pause.pause;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.timing.Condition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link BasicRobot#close(java.awt.Window)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_closeWindow_Test extends EDTSafeTestCase {
  private BasicRobot robot;
  private TestWindow w;

  @Before
  public void setUp() {
    robot = (BasicRobot) BasicRobot.robotWithNewAwtHierarchy();
    w = TestWindow.createNewWindow(getClass());
    w.display();
  }

  @After
  public void tearDown() {
    robot.cleanUp();
  }

  @Test
  public void should_close_window() {
    robot.close(w);
    pause(new Condition("Window closed") {
      @Override
      public boolean test() {
        return !isVisible(w);
      }
    });
    assertThat(isVisible(w)).isFalse();
  }

  @Test
  public void should_not_close_window_if_window_not_showing() {
    hide(w);
    robot.waitForIdle();
    robot.close(w);
    assertThat(isShowing(w)).isFalse();
  }
}
