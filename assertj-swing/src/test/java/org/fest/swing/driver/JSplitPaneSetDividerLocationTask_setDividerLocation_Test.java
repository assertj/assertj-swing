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

import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JSplitPane;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JSplitPaneSetDividerLocationTask#setDividerLocation(JSplitPane, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JSplitPaneSetDividerLocationTask_setDividerLocation_Test extends RobotBasedTestCase {
  private JSplitPane splitPane;
  private int location;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    splitPane = window.splitPane;
    location = 200;
  }

  @Test
  public void should_move_divider_to_given_location() {
    assertThat(dividerLocation()).isNotEqualTo(location);
    JSplitPaneSetDividerLocationTask.setDividerLocation(splitPane, location);
    robot.waitForIdle();
    assertThat(dividerLocation()).isEqualTo(location);
  }

  @RunsInEDT
  private int dividerLocation() {
    return dividerLocationOf(splitPane);
  }

  @RunsInEDT
  private static int dividerLocationOf(final JSplitPane splitPane) {
    return execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return splitPane.getDividerLocation();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    final JSplitPane splitPane;

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
      super(JSplitPaneSetDividerLocationTask_setDividerLocation_Test.class);
      splitPane = new JSplitPane(VERTICAL_SPLIT, new JList(), new JList());
      splitPane.setDividerLocation(150);
      splitPane.setPreferredSize(new Dimension(300, 300));
      add(splitPane);
    }
  }
}
