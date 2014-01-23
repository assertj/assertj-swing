/*
 * Created on Mar 16, 2008
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=116">Bug 116</a>.
 * 
 * @author Yvonne Wang
 */
public class Bug116_scrollToItemToSelectInJComboBox_Test extends RobotBasedTestCase {
  private JComboBoxFixture comboBox;
  private MyWindow window;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew();
    comboBox = new JComboBoxFixture(robot, window.comboBox);
    robot.showWindow(window);
  }

  @Test
  public void should_scroll_to_item_before_selecting_it() {
    int toSelect = 99;
    comboBox.selectItem(toSelect);
    assertThat(selectedIndexOf(window.comboBox)).isEqualTo(toSelect);
  }

  @RunsInEDT
  private static int selectedIndexOf(final JComboBox comboBox) {
    return execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return comboBox.getSelectedIndex();
      }
    });
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

    final JComboBox comboBox = new JComboBox();

    private MyWindow() {
      super(Bug116_scrollToItemToSelectInJComboBox_Test.class);
      add(comboBox);
      int itemCount = 100;
      Object[] content = new Object[itemCount];
      for (int i = 0; i < itemCount; i++) {
        content[i] = String.valueOf(i + 1);
      }
      comboBox.setModel(new DefaultComboBoxModel(content));
    }
  }
}
