/*
 * Created on Jul 7, 2009
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
import static org.fest.swing.core.ComponentLookupScope.DEFAULT;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Component;

import javax.swing.JLabel;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for bug <a href="http://jira.codehaus.org/browse/FEST-139" target="_blank">FEST_139</a>.
 *
 * @author Woody Folsom
 * @author Alex Ruiz
 */
public class FEST139_ComponentFinderNotUsingRobotSettings_Test extends RobotBasedTestCase {
  @Test
  public void finder_should_use_settings_from_Robot() {
    MyWindow.createNew();
    MyWindow window = MyWindow.createNew();
    robot.showWindow(window);
    assertThat(robot.settings().componentLookupScope()).isEqualTo(DEFAULT);
    Component found = robot.finder().findByName("testLabel");
    assertThat(found).isSameAs(window.label);
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

    final JLabel label = new JLabel("Test Label");

    private MyWindow() {
      super(FEST139_ComponentFinderNotUsingRobotSettings_Test.class);
      label.setName("testLabel");
      addComponents(label);
    }
  }
}
