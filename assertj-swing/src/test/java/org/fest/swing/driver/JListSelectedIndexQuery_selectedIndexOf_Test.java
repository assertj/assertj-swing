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
import static org.fest.swing.driver.JListSetSelectedIndexTask.setSelectedIndex;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import javax.swing.JList;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestListModel;
import org.fest.swing.test.swing.TestWindow;
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
    return newArrayList(new Object[][] {
        { 0 }, { 1 }, { 2 }, { -1 }
      });
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
  public void should_return_selected_index_of_JList() {
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
