/*
 * Created on Aug 10, 2008
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

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JTabbedPaneTabTitlesQuery#tabTitlesOf(JTabbedPane)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTabbedPaneTabTitlesQuery_tabTitlesOf_Test extends RobotBasedTestCase {
  private JTabbedPane tabbedPane;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    tabbedPane = window.tabbedPane;
  }

  @Test
  public void should_return_tab_titles() {
    assertThat(JTabbedPaneTabTitlesQuery.tabTitlesOf(tabbedPane)).containsOnly("One", "Two");
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
      super(JTabbedPaneTabTitlesQuery_tabTitlesOf_Test.class);
      tabbedPane.addTab("One", new JPanel());
      tabbedPane.addTab("Two", new JPanel());
      addComponents(tabbedPane);
    }
  }
}
