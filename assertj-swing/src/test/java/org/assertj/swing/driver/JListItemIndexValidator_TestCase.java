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

import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;

/**
 * Base test case for {@link JListItemPreconditions}.
 * 
 * @author Alex Ruiz
 */
public class JListItemIndexValidator_TestCase extends RobotBasedTestCase {
  JList list;

  @Rule
  public ExpectedException thrown = none();

  @Override
  protected final void onSetUp() {
    MyWindow window = MyWindow.createNew(getClass());
    list = window.list;
  }

  static class MyWindow extends TestWindow {
    private static final Dimension LIST_SIZE = new Dimension(80, 40);

    final JList list = new JList(array("One", "Two", "Three"));

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(decorate(list));
    }

    private static JScrollPane decorate(JList list) {
      JScrollPane scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(LIST_SIZE);
      return scrollPane;
    }
  }
}
