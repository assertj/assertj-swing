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

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static org.assertj.swing.driver.JListSetSelectedIndexTask.setSelectedIndex;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.task.ComponentSetEnabledTask.disable;

import java.awt.Dimension;
import java.awt.Point;

import javax.annotation.Nonnull;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestList;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;

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

  @Rule
  public ExpectedException thrown = none();

  @Override
  protected final void onSetUp() {
    cellReader = new JListCellReaderStub();
    driver = new JListDriver(robot);
    driver.replaceCellReader(cellReader);
    window = MyWindow.createNew(getClass());
    list = window.list;
    extraSetUp();
  }

  void extraSetUp() {
  }

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
    return execute(() -> list.locationToIndex(p));
  }

  @RunsInEDT
  final Object selectedValue() {
    return selectedValue(list);
  }

  @RunsInEDT
  private static Object selectedValue(final JList list) {
    return execute(() -> list.getSelectedValue());
  }

  @RunsInEDT
  final Object[] selectedValues() {
    return selectedValues(list);
  }

  @RunsInEDT
  private static Object[] selectedValues(final JList list) {
    return execute(() -> list.getSelectedValues());
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
    execute(() -> {
      list.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
      list.setSelectedIndices(indices);
    });
  }

  static class MyWindow extends TestWindow {
    private static final Dimension LIST_SIZE = new Dimension(80, 40);

    final TestList list = new TestList("one", "two", "three");

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
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
