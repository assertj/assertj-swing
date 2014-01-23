/*
 * Created on Nov 1, 2008
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

import java.awt.Dimension;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JListCellReader;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
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
  public void should_return_empty_array_if_JList_has_no_selection() {
    List<String> selection = JListSelectionValuesQuery.selectionValues(list, cellReader);
    assertThat(selection).isEmpty();
  }

  @Test
  public void should_return_selection_of_JList_as_text() {
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
