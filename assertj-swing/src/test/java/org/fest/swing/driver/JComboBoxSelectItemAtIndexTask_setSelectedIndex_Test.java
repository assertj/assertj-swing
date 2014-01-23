/*
 * Created on Jul 21, 2008
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
import static org.fest.util.Arrays.array;

import javax.swing.JComboBox;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JComboBoxSetSelectedIndexTask#setSelectedIndex(JComboBox, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxSelectItemAtIndexTask_setSelectedIndex_Test extends RobotBasedTestCase {
  private JComboBox comboBox;
  private int index;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    comboBox = window.comboBox;
    index = 1;
  }

  @Test
  public void should_set_selected_index() {
    assertThat(selectedIndexOf(comboBox)).isNotEqualTo(index);
    JComboBoxSetSelectedIndexTask.setSelectedIndex(comboBox, index);
    robot.waitForIdle();
    assertThat(selectedIndexOf(comboBox)).isEqualTo(index);
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
    final JComboBox comboBox = new JComboBox(array("first", "second", "third"));

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
      super(JComboBoxSelectItemAtIndexTask_setSelectedIndex_Test.class);
      addComponents(comboBox);
    }
  }
}
