/*
 * Created on Aug 28, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.swing.core.ComponentLookupScope.ALL;
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Fix for <a href="http://code.google.com/p/fest/issues/detail?id=20&can=2&q=" target="_blank">issue 20</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug20_cannotFindComponentInJPanel_Test extends RobotBasedTestCase {
  private FrameFixture frame;

  @Override
  protected void onSetUp() {
    robot.settings().componentLookupScope(ALL);
    frame = new FrameFixture(robot, MyWindow.createNew());
  }

  @Test
  public void should_find_list() {
    frame.list("list"); // should find list
  }

  static class MyWindow extends TestWindow {
    private final JPanel panel = new JPanel();
    private final JList list = new JList();

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
      super(Bug20_cannotFindComponentInJPanel_Test.class);
      add(panel);
      panel.add(new JScrollPane(list));
      list.setName("list");
    }
  }
}
