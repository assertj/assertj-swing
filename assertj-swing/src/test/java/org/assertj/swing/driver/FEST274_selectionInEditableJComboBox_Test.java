/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.driver;

import static java.awt.event.KeyEvent.VK_ENTER;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.JComboBox;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
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
  public void should_Return_Selected_Item_In_Editable_JComboBox_If_Selected_Index_Is_Negative_One() {
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
