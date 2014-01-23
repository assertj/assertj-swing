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

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JTextComponentFixture#JTextComponentFixture(org.fest.swing.core.Robot, String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTextComponentFixture_constructor_withRobotAndName_Test extends RobotBasedTestCase {
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
  }

  @Test
  public void should_lookup_showing_JTextComponent_by_name() {
    robot.showWindow(window);
    JTextComponentFixture fixture = new JTextComponentFixture(robot, "textField");
    assertThat(fixture.robot()).isSameAs(robot);
    assertThat(fixture.target()).isSameAs(window.textField);
  }

  @Test(expected = ComponentLookupException.class)
  public void should_throw_error_if_JTextComponent_with_matching_name_is_not_showing() {
    new JTextComponentFixture(robot, "textField");
  }

  @Test(expected = ComponentLookupException.class)
  public void should_throw_error_if_a_JTextComponent_with_matching_name_is_not_found() {
    new JTextComponentFixture(robot, "other");
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

    final JTextComponent textField = new JTextField(10);

    private MyWindow() {
      super(JTextComponentFixture_constructor_withRobotAndName_Test.class);
      textField.setName("textField");
      addComponents(textField);
    }
  }
}
