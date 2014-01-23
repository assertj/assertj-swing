/*
 * Created on Jun 6, 2009
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Arrays.array;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.ListModel;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JComboBoxDropDownListFinder#findDropDownList()}.
 * 
 * @author Alex Ruiz
 */
public class JComboBoxDropDownListFinder_findDropDownList_Test extends RobotBasedTestCase {
  private MyWindow window;
  private JComboBoxDropDownListFinder finder;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    finder = new JComboBoxDropDownListFinder(robot);
    robot.showWindow(window);
  }

  @Test
  public void should_find_drop_down_list() {
    showJComboBoxDropDownList();
    JList list = finder.findDropDownList();
    assertThat(list).isNotNull();
    assertThatListContains(list, "first", "second", "third");
  }

  @Test
  public void should_try_to_find_drop_down_list_until_it_is_found() {
    showDropDownListWithDelay();
    JList list = finder.findDropDownList();
    assertThat(list).isNotNull();
    assertThatListContains(list, "first", "second", "third");
  }

  private void showDropDownListWithDelay() {
    java.util.Timer timer = new Timer("showJComboBoxDropDownList", false);
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        showJComboBoxDropDownList();
      }
    }, 18000);
  }

  @RunsInEDT
  private void showJComboBoxDropDownList() {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        window.comboBox.showPopup();
      }
    });
    robot.waitForIdle();
  }

  private void assertThatListContains(final JList list, final String... expectedElements) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        ListModel model = list.getModel();
        int elementCount = model.getSize();
        assertThat(elementCount).isEqualTo(expectedElements.length);
        for (int i = 0; i < elementCount; i++) {
          assertThat(model.getElementAt(i)).isEqualTo(expectedElements[i]);
        }
      }
    });
  }

  @Test
  public void should_return_null_if_drop_down_was_not_found() {
    JList list = finder.findDropDownList();
    assertThat(list).isNull();
  }

  @Test
  public void should_return_null_if_active_drop_down_does_not_belong_to_JComboBox() {
    robot.rightClick(window.textField);
    JList list = finder.findDropDownList();
    assertThat(list).isNull();
  }

  private static class MyWindow extends TestWindow {
    final JComboBox comboBox = new JComboBox(array("first", "second", "third"));
    final JTextField textField = new JTextField(20);

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
      super(JComboBoxDropDownListFinder_findDropDownList_Test.class);
      JPopupMenu popupMenu = new JPopupMenu();
      popupMenu.add(new JMenuItem("One"));
      popupMenu.add(new JMenuItem("Two"));
      textField.setComponentPopupMenu(popupMenu);
      addComponents(comboBox, textField);
    }
  }
}
