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
package org.assertj.swing.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.JCheckBox;

import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JCheckBoxFixture#JCheckBoxFixture(org.assertj.swing.core.Robot, String)}.
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
  public void should_Lookup_Showing_JCheckBox_By_Name() {
    robot.showWindow(window);
    JCheckBoxFixture fixture = new JCheckBoxFixture(robot, "checkBox");
    assertThat(fixture.robot()).isSameAs(robot);
    assertThat(fixture.target()).isSameAs(window.checkBox);
  }

  @Test(expected = ComponentLookupException.class)
  public void should_Throw_Error_If_JCheckBox_With_Matching_Name_Is_Not_Showing() {
    new JCheckBoxFixture(robot, "checkBox");
  }

  @Test(expected = ComponentLookupException.class)
  public void should_Throw_Error_If_A_JCheckBox_With_Matching_Name_Is_Not_Found() {
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
