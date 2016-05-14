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
package org.assertj.swing.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.awt.AWT.centerOf;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.timing.Pause.pause;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTextField;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.SequentialEDTSafeTestCase;
import org.assertj.swing.test.swing.TestWindow;
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

  void extraSetUp() {
  }

  abstract InputEventGenerator eventGenerator();

  @Override
  protected final void onTearDown() {
    window.destroy();
  }

  @Ignore
  public void should_Move_Mouse() {
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
      return execute(() -> new MyWindow(testClass));
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(textBox);
    }
  }
}
