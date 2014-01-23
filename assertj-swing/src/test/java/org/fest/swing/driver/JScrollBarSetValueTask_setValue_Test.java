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

import static java.awt.Adjustable.HORIZONTAL;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JScrollBarValueQuery.valueOf;
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JScrollBar;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JScrollBarSetValueTask#setValue(JScrollBar, int)}.
 * 
 * @author Alex Ruiz
 */
public class JScrollBarSetValueTask_setValue_Test extends RobotBasedTestCase {
  private JScrollBar scrollBar;
  private int value;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    scrollBar = window.scrollBar;
    value = 6;
  }

  @Test
  public void should_set_value_to_JScrollBar() {
    JScrollBarSetValueTask.setValue(scrollBar, value);
    robot.waitForIdle();
    assertThat(valueOf(scrollBar)).isEqualTo(value);
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

    final JScrollBar scrollBar = new JScrollBar(HORIZONTAL);

    private MyWindow() {
      super(JScrollBarSetValueTask_setValue_Test.class);
      scrollBar.setMinimum(2);
      scrollBar.setMaximum(20);
      scrollBar.setValue(8);
      addComponents(scrollBar);
    }
  }
}
