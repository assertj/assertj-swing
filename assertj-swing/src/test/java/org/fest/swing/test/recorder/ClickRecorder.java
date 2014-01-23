/*
 * Created on Sep 21, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.test.recorder;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.MouseButton.MIDDLE_BUTTON;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.util.Maps.newHashMap;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.annotation.Nonnull;

import org.fest.assertions.AssertExtension;
import org.fest.swing.core.MouseButton;

/**
 * An event listener that records mouse events.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ClickRecorder implements AssertExtension {
  public static @Nonnull ClickRecorder attachTo(@Nonnull Component target) {
    ClickRecorder recorder = new ClickRecorder();
    attach(new ClickListener(recorder), target);
    return recorder;
  }

  private static void attach(@Nonnull ClickListener listener, @Nonnull Component target) {
    target.addMouseListener(listener);
    if (!(target instanceof Container)) {
      return;
    }
    for (Component c : ((Container) target).getComponents()) {
      attach(listener, checkNotNull(c));
    }
  }

  private static final Map<Integer, MouseButton> MOUSE_BUTTON_MAP = newHashMap();

  static {
    MOUSE_BUTTON_MAP.put(MouseEvent.BUTTON1, LEFT_BUTTON);
    MOUSE_BUTTON_MAP.put(MouseEvent.BUTTON2, MIDDLE_BUTTON);
    MOUSE_BUTTON_MAP.put(MouseEvent.BUTTON3, RIGHT_BUTTON);
  }

  private MouseButton clickedButton;
  private int clickCount;
  private Point pointClicked;

  private static class ClickListener extends MouseAdapter {
    private final ClickRecorder owner;

    ClickListener(@Nonnull ClickRecorder owner) {
      this.owner = owner;
    }

    @Override
    public void mousePressed(MouseEvent e) {
      record(checkNotNull(e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      record(checkNotNull(e));
    }

    private void record(@Nonnull MouseEvent e) {
      owner.clickedButton = MOUSE_BUTTON_MAP.get(e.getButton());
      owner.clickCount = e.getClickCount();
      owner.pointClicked = e.getPoint();
    }
  }

  public @Nonnull ClickRecorder wasNotClicked() {
    assertThat(clickedButton).isNull();
    return this;
  }

  public @Nonnull ClickRecorder timesClicked(int times) {
    assertThat(clickCount).isEqualTo(times);
    return this;
  }

  public @Nonnull ClickRecorder wasClicked() {
    return clicked(LEFT_BUTTON).timesClicked(1);
  }

  public @Nonnull ClickRecorder wasDoubleClicked() {
    return clicked(LEFT_BUTTON).timesClicked(2);
  }

  public @Nonnull ClickRecorder wasRightClicked() {
    return clicked(RIGHT_BUTTON).timesClicked(1);
  }

  public @Nonnull ClickRecorder clicked(@Nonnull MouseButton button) {
    return wasClickedWith(button);
  }

  public @Nonnull ClickRecorder wasClickedWith(@Nonnull MouseButton button) {
    assertThat(clickedButton).isEqualTo(button);
    return this;
  }

  public @Nonnull ClickRecorder clickedAt(@Nonnull Point p) {
    assertThat(pointClicked).isEqualTo(p);
    return this;
  }

  public @Nonnull Point pointClicked() {
    return pointClicked;
  }
}