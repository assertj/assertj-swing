/*
 * Created on Aug 11, 2008
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
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JTabbedPaneSelectTabTask#setSelectedTab(JTabbedPane, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTabbedPaneSelectTabTask_setSelectedTab_Test extends RobotBasedTestCase {
  private JTabbedPane tabbedPane;
  private int index;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    tabbedPane = window.tabbedPane;
    index = 1;
  }

  @Test
  public void should_select_tab_under_given_index() {
    assertThat(selectedTabIndex()).isNotEqualTo(index);
    JTabbedPaneSelectTabTask.setSelectedTab(tabbedPane, index);
    robot.waitForIdle();
    assertThat(selectedTabIndex()).isEqualTo(index);
  }

  @RunsInEDT
  private int selectedTabIndex() {
    return selectedIndexOf(tabbedPane);
  }

  @RunsInEDT
  private static int selectedIndexOf(final JTabbedPane tabbedPane) {
    return execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return tabbedPane.getSelectedIndex();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    final JTabbedPane tabbedPane = new JTabbedPane();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JTabbedPaneSelectTabTask_setSelectedTab_Test.class);
      tabbedPane.addTab("One", new JPanel());
      tabbedPane.addTab("Two", new JPanel());
      add(tabbedPane);
      setPreferredSize(new Dimension(300, 200));
    }
  }
}
