/*
 * Created on Mar 7, 2010
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
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT;
import static javax.swing.SwingConstants.BOTTOM;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Strings.concat;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.timing.Pause;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-309" target="_blank">FEST-309</a>.
 * 
 * @author Nicolae Bucalaete
 * @author Alex Ruiz
 */
public class FEST309_scrollAndSelectTab_Test extends RobotBasedTestCase {
  private MyWindow window;
  private FrameFixture frame;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    frame = new FrameFixture(robot, window);
    frame.show();
  }

  @Test
  public void should_scroll_and_click_tab() {
    frame.tabbedPane().selectTab(90);
    Pause.pause(10000);
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private final JTabbedPane tabbedPane = new JTabbedPane();

    @RunsInCurrentThread
    private MyWindow() {
      super(FEST309_scrollAndSelectTab_Test.class);
      configureTabbedPane();
      setLayout(new BorderLayout());
      add(tabbedPane, CENTER);
      setPreferredSize(new Dimension(300, 100));
    }

    private void configureTabbedPane() {
      tabbedPane.setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
      tabbedPane.setTabPlacement(BOTTOM);
      for (int i = 0; i < 100; i++) {
        tabbedPane.addTab(concat("Tab ", i), new JPanel());
      }
    }
  }
}
