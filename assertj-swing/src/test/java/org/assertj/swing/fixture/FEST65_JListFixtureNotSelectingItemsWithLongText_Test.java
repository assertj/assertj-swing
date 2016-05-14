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
package org.assertj.swing.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for issue <a href="https://kenai.com/jira/browse/FEST-65" target="_blank">FEST-65</a>.
 * 
 * @author Cimballi
 * @author Alex Ruiz
 */
public class FEST65_JListFixtureNotSelectingItemsWithLongText_Test extends RobotBasedTestCase {
  private static final String SUPER_LONG_TEXT = "wwwwwwwww0wwwwwwwww1wwwwwwwww2wwwwwwwww3wwwwwwwww4wwwwwwwww5wwwwwwwww6wwwwwwwww7wwwwwwwww8wwwwwwwww9";
  private FrameFixture frame;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    frame = new FrameFixture(robot, window);
    robot.showWindow(window);
  }

  @Test
  public void shouldSelectItemWithSuperLongText() {
    String[] selection = frame.list("list").selectItem(SUPER_LONG_TEXT).selection();
    assertThat(selection).containsOnly(SUPER_LONG_TEXT);
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final JList list = new JList(array("aaaaaaaaa0", "aaaaaaaaa1", "aaaaaaaaa2", "aaaaaaaaa3", "aaaaaaaaa4",
        "aaaaaaaaa5", "aaaaaaaaa6", "aaaaaaaaa7", "aaaaaaaaa8", "aaaaaaaaa9", "bbbbbbbbb0", "bbbbbbbbb1", "bbbbbbbbb2",
        "bbbbbbbbb3", "bbbbbbbbb4", "bbbbbbbbb5", "bbbbbbbbb6", "bbbbbbbbb7", "bbbbbbbbb8", "bbbbbbbbb9",
        SUPER_LONG_TEXT));

    private MyWindow() {
      super(FEST65_JListFixtureNotSelectingItemsWithLongText_Test.class);
      list.setName("list");
      JScrollPane scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(new Dimension(150, 100));
      addComponents(scrollPane);
    }
  }

  static String superLongText() {
    StringBuilder b = new StringBuilder();
    for (int i = 0; i < 1000; i++) {
      b.append("a ");
    }
    return b.toString();
  }
}
