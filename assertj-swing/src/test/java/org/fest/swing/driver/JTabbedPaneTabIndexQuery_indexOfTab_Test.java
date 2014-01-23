/*
 * Created on Aug 22, 2008
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
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.util.StringTextMatcher;
import org.junit.Test;

/**
 * Tests for {@link JTabbedPaneTabIndexQuery#indexOfTab(JTabbedPane, org.fest.swing.util.TextMatcher)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTabbedPaneTabIndexQuery_indexOfTab_Test extends RobotBasedTestCase {
  private JTabbedPane tabbedPane;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    tabbedPane = window.tabbedPane;
  }

  @Test
  public void should_return_index_of_tab() {
    int index = indexOfTab(tabbedPane, "First");
    assertThat(index).isEqualTo(0);
  }

  @Test
  public void should_return_negative_one_if_no_matching_title_found() {
    int index = indexOfTab(tabbedPane, "Hello");
    assertThat(index).isEqualTo(-1);
  }

  @Test
  public void should_return_negative_one_if_JTabbedPane_does_not_have_tabs() {
    removeAllTabsIn(tabbedPane);
    robot.waitForIdle();
    int index = indexOfTab(tabbedPane, "First");
    assertThat(index).isEqualTo(-1);
  }

  @RunsInEDT
  private static int indexOfTab(final JTabbedPane tabbedPane, final String title) {
    return execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return JTabbedPaneTabIndexQuery.indexOfTab(tabbedPane, new StringTextMatcher(title));
      }
    });
  }

  @RunsInEDT
  private static void removeAllTabsIn(final JTabbedPane tabbedPane) {
    execute(new GuiTask() {
      @Override
      public void executeInEDT() {
        tabbedPane.removeAll();
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
      super(JTabbedPaneTabIndexQuery_indexOfTab_Test.class);
      tabbedPane.addTab("First", new JPanel());
      tabbedPane.addTab("Second", new JPanel());
      tabbedPane.addTab("Third", new JPanel());
      addComponents(tabbedPane);
    }
  }
}
