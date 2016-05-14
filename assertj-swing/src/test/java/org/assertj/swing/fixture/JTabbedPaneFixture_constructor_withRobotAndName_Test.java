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

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JTabbedPaneFixture#JTabbedPaneFixture(org.assertj.swing.core.Robot, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTabbedPaneFixture_constructor_withRobotAndName_Test extends RobotBasedTestCase {
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
  }

  @Test
  public void should_Lookup_Showing_JTabbedPane_By_Name() {
    robot.showWindow(window);
    JTabbedPaneFixture fixture = new JTabbedPaneFixture(robot, "tabbedPane");
    assertThat(fixture.robot()).isSameAs(robot);
    assertThat(fixture.target()).isSameAs(window.tabbedPane);
  }

  @Test(expected = ComponentLookupException.class)
  public void should_Throw_Error_If_JTabbedPane_With_Matching_Name_Is_Not_Showing() {
    new JTabbedPaneFixture(robot, "tabbedPane");
  }

  @Test(expected = ComponentLookupException.class)
  public void should_Throw_Error_If_A_JTabbedPane_With_Matching_Name_Is_Not_Found() {
    new JTabbedPaneFixture(robot, "other");
  }

  private static class MyWindow extends TestWindow {
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final JTabbedPane tabbedPane = new JTabbedPane();

    private MyWindow() {
      super(JTabbedPaneFixture_constructor_withRobotAndName_Test.class);
      tabbedPane.setPreferredSize(new Dimension(100, 50));
      tabbedPane.setName("tabbedPane");
      tabbedPane.addTab("One", new JPanel());
      addComponents(tabbedPane);
    }
  }
}
