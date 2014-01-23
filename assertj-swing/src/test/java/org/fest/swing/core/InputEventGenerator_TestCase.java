/*
 * Created on Apr 3, 2008
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
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.awt.AWT.centerOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.timing.Pause.pause;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Ignore;

/**
 * Base test case for implementations of {@link InputEventGenerator}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class InputEventGenerator_TestCase extends SequentialEDTSafeTestCase {
  static final int DELAY = 200;

  MyWindow window;
  InputEventGenerator eventGenerator;

  protected static final String MOVE_MOUSE_TEST = "Move Mouse Test";

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew(getClass());
    extraSetUp();
    eventGenerator = eventGenerator();
    window.display();
  }

  void extraSetUp() {}

  abstract InputEventGenerator eventGenerator();

  @Override
  protected final void onTearDown() {
    window.destroy();
  }

  @Ignore
  public void should_move_mouse() {
    eventGenerator.moveMouse(window, 10, 10);
    pause(DELAY);
    MouseMotionRecorder recorder = MouseMotionRecorder.attachTo(window);
    pause(DELAY);
    Point center = centerOf(window);
    eventGenerator.moveMouse(window, center.x, center.y);
    pause(DELAY);
    assertThat(recorder.point()).isEqualTo(center);
  }

  private static class MouseMotionRecorder extends MouseMotionAdapter {
    private Point point;

    static MouseMotionRecorder attachTo(Component c) {
      MouseMotionRecorder recorder = new MouseMotionRecorder();
      c.addMouseMotionListener(recorder);
      return recorder;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
      point = e.getPoint();
    }

    Point point() {
      return point;
    }
  }

  static class MyWindow extends TestWindow {
    final JTextField textBox = new JTextField(20);

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
      addComponents(textBox);
    }
  }
}
