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
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.awt.AWT.centerOf;
import static org.assertj.swing.timing.Pause.pause;

import java.awt.Point;
import java.util.Collection;

import javax.annotation.Nonnull;

import org.assertj.swing.test.recorder.ClickRecorder;
import org.assertj.swing.test.recorder.ClickRecorderManager;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Base test case for implementations of {@link InputEventGenerator#pressMouse(Point, int)} and
 * {@link InputEventGenerator#releaseMouse(int)}.
 * 
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public abstract class InputEventGenerator_pressMouse_TestCase extends InputEventGenerator_TestCase {
  @Rule
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  private final MouseButton button;
  private final int buttonMask;

  @Parameters
  public static @Nonnull Collection<Object[]> mouseButtons() {
    return newArrayList(MouseButtonProvider.mouseButtons());
  }

  public InputEventGenerator_pressMouse_TestCase(@Nonnull MouseButton button) {
    this.button = checkNotNull(button);
    buttonMask = button.mask;
  }

  @Test
  public void should_Press_Mouse_Button_At_Given_Point_And_Release_Mouse_Button() {
    Point center = centerOf(window);
    eventGenerator.moveMouse(window, center.x, center.y); // indirectly testing mouseMove :)
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window);
    eventGenerator.pressMouse(buttonMask);
    eventGenerator.releaseMouse(buttonMask);
    pause(DELAY);
    assertThat(recorder.clicked(button));
  }
}
