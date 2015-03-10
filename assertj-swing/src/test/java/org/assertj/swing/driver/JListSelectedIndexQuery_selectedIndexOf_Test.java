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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.driver.JListSetSelectedIndexTask.setSelectedIndex;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.util.Collection;

import javax.swing.JList;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestListModel;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JListSelectedIndexQuery#selectedIndexOf(JList)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class JListSelectedIndexQuery_selectedIndexOf_Test extends RobotBasedTestCase {
  private MyList list;

  private final int selectedIndex;

  @Parameters
  public static Collection<Object[]> selectedIndices() {
    return newArrayList(new Object[][] { { 0 }, { 1 }, { 2 }, { -1 } });
  }

  public JListSelectedIndexQuery_selectedIndexOf_Test(int selectedIndex) {
    this.selectedIndex = selectedIndex;
  }

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    list = window.list;
  }

  @Test
  public void should_Return_Selected_Index_Of_JList() {
    setSelectedIndex(list, selectedIndex);
    robot.waitForIdle();
    list.startRecording();
    assertThat(JListSelectedIndexQuery.selectedIndexOf(list)).isEqualTo(selectedIndex);
    list.requireInvoked("getSelectedIndex");
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

    final MyList list = new MyList("One", "Two", "Three");

    private MyWindow() {
      super(JListSelectedIndexQuery_selectedIndexOf_Test.class);
      addComponents(list);
    }
  }

  private static class MyList extends JList {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyList(Object... elements) {
      setModel(new TestListModel(elements));
    }

    void startRecording() {
      recording = true;
    }

    @Override
    public int getSelectedIndex() {
      if (recording) {
        methodInvocations.invoked("getSelectedIndex");
      }
      return super.getSelectedIndex();
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
