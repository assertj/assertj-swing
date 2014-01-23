/*
 * Created on Dec 13, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static java.awt.event.KeyEvent.VK_ENTER;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Arrays.array;

import javax.swing.JComboBox;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-274" target="_blank">FEST-274</a>
 *
 * @author Alex Ruiz
 */
public class FEST274_selectionInEditableJComboBox_Test extends RobotBasedTestCase {
  private JComboBoxDriver driver;
  private JComboBox comboBox;

  @Override
  protected void onSetUp() {
    driver = new JComboBoxDriver(robot);
    MyWindow window = MyWindow.createNew();
    robot.showWindow(window);
    comboBox = window.comboBox;
  }

  @Test
  public void should_return_selected_item_in_editable_JComboBox_if_selected_index_is_negative_one() {
    driver.enterText(comboBox, "Four");
    driver.pressAndReleaseKeys(comboBox, VK_ENTER);
    driver.requireSelection(comboBox, "Four");
  }

  private static class MyWindow extends TestWindow {
    private final JComboBox comboBox;

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
      super(FEST274_selectionInEditableJComboBox_Test.class);
      comboBox = new JComboBox(array("One", "Two", "Three"));
      comboBox.setEditable(true);
      comboBox.setSelectedIndex(-1);
      addComponents(comboBox);
    }
  }
}
