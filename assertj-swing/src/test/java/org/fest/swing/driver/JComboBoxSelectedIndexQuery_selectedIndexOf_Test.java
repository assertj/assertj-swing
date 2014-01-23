/*
 * Created on Aug 6, 2008
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
import static org.fest.swing.driver.JComboBoxSetSelectedIndexTask.setSelectedIndex;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import javax.swing.JComboBox;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JComboBoxSelectedIndexQuery#selectedIndexOf(JComboBox)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class JComboBoxSelectedIndexQuery_selectedIndexOf_Test extends RobotBasedTestCase {
  private MyComboBox comboBox;

  private final int selectedIndex;

  @Parameters
  public static Collection<Object[]> indices() {
    return newArrayList(new Object[][] {
        { 0 }, { 1 }, { 2 }, { -1 }
      });
  }

  public JComboBoxSelectedIndexQuery_selectedIndexOf_Test(int selectedIndex) {
    this.selectedIndex = selectedIndex;
  }

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    comboBox = window.comboBox;
  }

  @Test
  public void should_return_selected_index_of_JComboBox() {
    comboBox.startRecording();
    setSelectedIndex(comboBox, selectedIndex);
    robot.waitForIdle();
    assertThat(JComboBoxSelectedIndexQuery.selectedIndexOf(comboBox)).isEqualTo(selectedIndex);
    comboBox.requireInvoked("getSelectedIndex");
  }

  private static class MyWindow extends TestWindow {
    final MyComboBox comboBox = new MyComboBox("one", "two", "three");

    @RunsInEDT
    static MyWindow createNew() {
      return GuiActionRunner.execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() throws Throwable {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JComboBoxSelectedIndexQuery_selectedIndexOf_Test.class);
      add(comboBox);
    }
  }

  private static class MyComboBox extends JComboBox {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyComboBox(Object... items) {
      super(items);
    }

    @Override
    public int getSelectedIndex() {
      if (recording) {
        methodInvocations.invoked("getSelectedIndex");
      }
      return super.getSelectedIndex();
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
