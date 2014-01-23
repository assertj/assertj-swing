/*
 * Created on Nov 18, 2009
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JCheckBox;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JCheckBoxFixture#JCheckBoxFixture(org.fest.swing.core.Robot, String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JCheckBoxFixture_constructor_withRobotAndName_Test extends RobotBasedTestCase {
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
  }

  @Test
  public void should_lookup_showing_JCheckBox_by_name() {
    robot.showWindow(window);
    JCheckBoxFixture fixture = new JCheckBoxFixture(robot, "checkBox");
    assertThat(fixture.robot()).isSameAs(robot);
    assertThat(fixture.target()).isSameAs(window.checkBox);
  }

  @Test(expected = ComponentLookupException.class)
  public void should_throw_error_if_JCheckBox_with_matching_name_is_not_showing() {
    new JCheckBoxFixture(robot, "checkBox");
  }

  @Test(expected = ComponentLookupException.class)
  public void should_throw_error_if_a_JCheckBox_with_matching_name_is_not_found() {
    new JCheckBoxFixture(robot, "other");
  }

  private static class MyWindow extends TestWindow {
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JCheckBox checkBox = new JCheckBox("Check Me");

    private MyWindow() {
      super(JCheckBoxFixture_constructor_withRobotAndName_Test.class);
      checkBox.setName("checkBox");
      addComponents(checkBox);
    }
  }
}
