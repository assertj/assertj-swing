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
import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.cell.JListCellReader;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JListSelectionValuesQuery#selectionValues(JList, JListCellReader)}.
 * 
 * @author Alex Ruiz
 */
public class JListSelectionValuesQuery_selectionValues_Test extends RobotBasedTestCase {
  private JList list;
  private JListCellReader cellReader;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    list = window.list;
    cellReader = new BasicJListCellReader();
  }

  @Test
  public void should_Return_Empty_Array_If_JList_Has_No_Selection() {
    List<String> selection = JListSelectionValuesQuery.selectionValues(list, cellReader);
    assertThat(selection).isEmpty();
  }

  @Test
  public void should_Return_Selection_Of_JList_As_Text() {
    setSelectedIndices(list, 0, 1, 2);
    robot.waitForIdle();
    List<String> selection = JListSelectionValuesQuery.selectionValues(list, cellReader);
    assertThat(selection).containsOnly("One", "Two", "Three");
  }

  @RunsInEDT
  private void setSelectedIndices(final JList list, final int... indices) {
    int count = indices.length;
    final int[] toSelect = new int[count];
    for (int i = 0; i < count; i++) {
      toSelect[i] = indices[i];
    }
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        list.setSelectedIndices(toSelect);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final Dimension LIST_SIZE = new Dimension(80, 40);

    final JList list = new JList(array("One", "Two", "Three", "Four"));

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
      super(JListSelectionValuesQuery_selectionValues_Test.class);
      addComponents(decorate(list));
    }

    private static JScrollPane decorate(JList list) {
      JScrollPane scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(LIST_SIZE);
      return scrollPane;
    }
  }
}
