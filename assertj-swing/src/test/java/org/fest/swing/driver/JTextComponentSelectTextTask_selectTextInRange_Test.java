/*
 * Created on Aug 11, 2008
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
import static org.fest.swing.driver.JTextComponentSelectedTextQuery.selectedTextOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Lists.newArrayList;

import java.awt.Dimension;
import java.util.Collection;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JTextComponentSelectTextTask#selectTextInRange(JTextComponent, int, int)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class JTextComponentSelectTextTask_selectTextInRange_Test extends RobotBasedTestCase {
  static final String TEXTBOX_TEXT = "Hello World";

  private JTextComponent textBox;

  private final int start;
  private final int end;

  @Parameters
  public static Collection<Object[]> ranges() {
    return newArrayList(new Object[][] {
        { 0, 5 }, { 1, 9 }, { 6, 8 }
      });
  }

  public JTextComponentSelectTextTask_selectTextInRange_Test(int start, int end) {
    this.start = start;
    this.end = end;
  }

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    textBox = window.textBox;
    robot.showWindow(window);
  }

  @Test
  public void should_select_text() {
    selectTextInRange(textBox, start, end);
    robot.waitForIdle();
    String selection = selectedTextOf(textBox);
    assertThat(selection).isEqualTo(TEXTBOX_TEXT.substring(start, end));
  }

  @RunsInEDT
  private static void selectTextInRange(final JTextComponent textBox, final int start, final int end) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        JTextComponentSelectTextTask.selectTextInRange(textBox, start, end);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    final JTextField textBox = new JTextField(20);

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
      super(JTextComponentSelectTextTask_selectTextInRange_Test.class);
      setPreferredSize(new Dimension(80, 60));
      add(textBox);
      textBox.setText(TEXTBOX_TEXT);
    }
  }
}
