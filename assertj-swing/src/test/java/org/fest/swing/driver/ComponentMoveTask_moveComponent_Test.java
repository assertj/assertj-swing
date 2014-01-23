/*
 * Created on Feb 23, 2008
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

import java.awt.Point;

import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test for {@link ComponentMoveTask#moveComponent(java.awt.Component, Point)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentMoveTask_moveComponent_Test extends RobotBasedTestCase {
  private TestWindow window;
  private Point location;

  @Override
  protected void onSetUp() {
    window = TestWindow.createNewWindow(getClass());
    robot.showWindow(window);
    location = new Point(80, 60);
    assertThat(location).isNotEqualTo(window.getLocationOnScreen());
  }

  @Test
  public void should_move_Component_to_given_location() {
    ComponentMoveTask.moveComponent(window, location);
    robot.waitForIdle();
    assertThat(location).isEqualTo(window.getLocationOnScreen());
  }
}
