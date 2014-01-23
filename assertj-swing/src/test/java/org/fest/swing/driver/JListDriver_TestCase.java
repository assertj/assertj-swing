/*
 * Created on Feb 24, 2008
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

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static org.fest.swing.driver.JListSetSelectedIndexTask.setSelectedIndex;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;

import java.awt.Dimension;
import java.awt.Point;

import javax.annotation.Nonnull;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestList;
import org.fest.swing.test.swing.TestWindow;

/**
 * Base test case for {@link JListDriver}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class JListDriver_TestCase extends RobotBasedTestCase {
  JListCellReaderStub cellReader;
  MyWindow window;
  TestList list;
  JListDriver driver;

  @Override
  protected final void onSetUp() {
    cellReader = new JListCellReaderStub();
    driver = new JListDriver(robot);
    driver.replaceCellReader(cellReader);
    window = MyWindow.createNew(getClass());
    list = window.list;
    extraSetUp();
  }

  void extraSetUp() {}

  static Object[][] indicesOutOfBounds() {
    return new Object[][] { { -1 }, { 100 } };
  }

  final void showWindow() {
    robot.showWindow(window);
  }

  final void assertThatCellReaderWasCalled() {
    cellReader.requireInvoked("valueAt");
  }

  @RunsInEDT
  final void disableList() {
    disable(list);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void clearSelection() {
    setSelectedIndex(list, (-1));
    robot.waitForIdle();
  }

  @RunsInEDT
  final int locationToIndex(Point p) {
    return locationToIndex(list, p);
  }

  @RunsInEDT
  private static int locationToIndex(final JList list, final Point p) {
    return execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return list.locationToIndex(p);
      }
    });
  }

  @RunsInEDT
  final Object selectedValue() {
    return selectedValue(list);
  }

  @RunsInEDT
  private static Object selectedValue(final JList list) {
    return execute(new GuiQuery<Object>() {
      @Override
      protected Object executeInEDT() {
        return list.getSelectedValue();
      }
    });
  }

  @RunsInEDT
  final Object[] selectedValues() {
    return selectedValues(list);
  }

  @RunsInEDT
  private static Object[] selectedValues(final JList list) {
    return execute(new GuiQuery<Object[]>() {
      @Override
      protected Object[] executeInEDT() {
        return list.getSelectedValues();
      }
    });
  }

  @RunsInEDT
  final void selectFirstItem() {
    select(0);
  }

  @RunsInEDT
  final void select(int... indices) {
    setSelectedIndices(list, indices);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setSelectedIndices(final JList list, final int... indices) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        list.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
        list.setSelectedIndices(indices);
      }
    });
  }

  static class MyWindow extends TestWindow {
    private static final Dimension LIST_SIZE = new Dimension(80, 40);

    final TestList list = new TestList("one", "two", "three");

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      list.setName("list");
      addList(list);
    }

    void addList(JList newList) {
      add(decorate(newList));
    }

    private static JScrollPane decorate(JList list) {
      JScrollPane scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(LIST_SIZE);
      return scrollPane;
    }
  }

  static class JListCellReaderStub extends BasicJListCellReader {
    private final MethodInvocations methodInvocations = new MethodInvocations();

    JListCellReaderStub() {
    }

    @Override
    public String valueAt(@Nonnull JList list, int index) {
      methodInvocations.invoked("valueAt");
      return super.valueAt(list, index);
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
