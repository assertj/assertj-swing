/*
 * Created on Jan 13, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.core;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.Box.createVerticalStrut;
import static javax.swing.BoxLayout.Y_AXIS;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.JButtonFixture;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link Scrolling#scrollToVisible(Robot, javax.swing.JComponent)}.
 *
 * @author Juhos Csaba-Zsolt
 * @author Alex Ruiz
 */
public class Scrolling_scrollToVisible_Test extends RobotBasedTestCase {
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createAndShow();
  }

  @Test
  public void should_auto_scroll_to_JButton() {
    JButton button = window.button;
    Scrolling.scrollToVisible(robot, button);
    new JButtonFixture(robot, button).click();
  }

  private static class MyWindow extends TestWindow {
    final JButton button = new JButton("Click Me");

    static MyWindow createAndShow() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          MyWindow w = new MyWindow();
          display(w);
          w.setLocation(0, 0);
          w.setSize(w.getToolkit().getScreenSize());
          return w;
        }
      });
    }

    private MyWindow() {
      super(Scrolling_scrollToVisible_Test.class);
      setLayout(new BorderLayout());
      add(buildNestedScrollPanes(32, 1000), CENTER);
    }

    Component buildNestedScrollPanes(int levelCount, int pixelCount) {
      // if no more levels to build, just return the button
      if (levelCount == 0) {
        return button;
      }
      return scrollPaneWith(box(levelCount, pixelCount));
    }

    private Box box(int levelCount, int pixelCount) {
      Box box = new Box(Y_AXIS);
      box.add(createVerticalStrut(pixelCount));
      // create the nested component recursively
      box.add(buildNestedScrollPanes(levelCount - 1, pixelCount));
      return box;
    }

    private Component scrollPaneWith(Box box) {
      JScrollPane p = new JScrollPane();
      p.setViewportView(box);
      return p;
    }
  }
}
