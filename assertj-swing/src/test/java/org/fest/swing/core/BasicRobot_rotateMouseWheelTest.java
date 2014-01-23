/*
 * Created on Jul 26, 2009
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Arrays.array;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.annotation.Nonnull;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link BasicRobot#rotateMouseWheel(java.awt.Component, int)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_rotateMouseWheelTest extends BasicRobot_TestCase {
  private JList list;
  private JScrollPane scrollPane;

  @RunsInEDT
  @Override
  void beforeShowingWindow() {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        list = new JList(array("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight"));
        scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        window().add(scrollPane);
      }
    });
  }

  @Test
  public void should_rotate_mouse_wheel() {
    assertThat(firstVisibleIndexOf(list)).isEqualTo(0);
    MouseWheelRecorder recorder = MouseWheelRecorder.attachTo(scrollPane);
    int amount = 50;
    robot().rotateMouseWheel(list, amount);
    assertThat(recorder.wheelRotation()).isEqualTo(amount);
    assertThat(firstVisibleIndexOf(list)).isGreaterThan(0);
  }

  @RunsInEDT
  private static int firstVisibleIndexOf(final JList list) {
    Integer result = execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return list.getFirstVisibleIndex();
      }
    });
    return checkNotNull(result);
  }

  private static class MouseWheelRecorder implements MouseWheelListener {
    private int wheelRotation;

    static @Nonnull MouseWheelRecorder attachTo(@Nonnull Component c) {
      MouseWheelRecorder recorder = new MouseWheelRecorder();
      c.addMouseWheelListener(recorder);
      return recorder;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
      wheelRotation = e.getWheelRotation();
    }

    int wheelRotation() {
      return wheelRotation;
    }
  }
}
