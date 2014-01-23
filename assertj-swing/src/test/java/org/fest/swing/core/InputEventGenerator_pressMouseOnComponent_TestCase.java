/*
 * Created on Aug 4, 2009
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
import static org.fest.swing.awt.AWT.centerOf;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Point;
import java.util.Collection;

import javax.annotation.Nonnull;

import org.fest.swing.test.recorder.ClickRecorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Base test case for implementations of {@link InputEventGenerator#pressMouse(java.awt.Component, java.awt.Point, int)}
 * and {@link InputEventGenerator#releaseMouse(int)}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public abstract class InputEventGenerator_pressMouseOnComponent_TestCase extends InputEventGenerator_TestCase {
  private final MouseButton button;
  private final int buttonMask;

  @Parameters
  public static @Nonnull Collection<Object[]> mouseButtons() {
    return newArrayList(MouseButtonProvider.mouseButtons());
  }

  public InputEventGenerator_pressMouseOnComponent_TestCase(@Nonnull MouseButton button) {
    this.button = checkNotNull(button);
    buttonMask = button.mask;
  }

  @Test
  public void should_press_mouse_button_on_Component_and_release_mouse_button() {
    ClickRecorder recorder = ClickRecorder.attachTo(window.textBox);
    Point componentCenter = centerOf(window.textBox);
    eventGenerator.pressMouse(window.textBox, componentCenter, buttonMask);
    eventGenerator.releaseMouse(buttonMask);
    pause(DELAY);
    recorder.clicked(button);
    assertThat(recorder.pointClicked()).isEqualTo(componentCenter);
  }
}
