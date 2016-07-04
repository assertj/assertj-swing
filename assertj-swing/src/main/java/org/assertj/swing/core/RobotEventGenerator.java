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

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.awt.AWT.isPointInScreenBoundaries;
import static org.assertj.swing.awt.AWT.translate;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.exception.ActionFailedException.actionFailure;
import static org.assertj.swing.exception.UnexpectedException.unexpected;
import static org.assertj.swing.timing.Pause.pause;
import static org.assertj.swing.util.Platform.isOSX;
import static org.assertj.swing.util.Platform.isWindows;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Point;
import java.awt.Robot;

import javax.annotation.Nonnull;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.util.RobotFactory;

/**
 * Simulates user input by using an AWT {@code Robot}.
 * 
 * @author Alex Ruiz
 */
class RobotEventGenerator implements InputEventGenerator {
  private static final int KEY_INPUT_DELAY = 200;

  private final Robot robot;
  private final Settings settings;

  RobotEventGenerator() {
    this(new Settings());
  }

  RobotEventGenerator(@Nonnull Settings settings) {
    this(new RobotFactory(), settings);
  }

  RobotEventGenerator(@Nonnull RobotFactory robotFactory, @Nonnull Settings settings) {
    try {
      robot = robotFactory.newRobotInLeftScreen();
      if (isWindows() || isOSX()) {
        pause(500);
      }
    } catch (AWTException e) {
      throw unexpected(e);
    }
    this.settings = settings;
    settings.attachTo(robot);
  }

  @Nonnull
  Robot robot() {
    return robot;
  }

  @RunsInEDT
  @Override
  public void pressMouse(@Nonnull Component c, @Nonnull Point where, int buttons) {
    Point p = checkNotNull(execute(() -> translate(c, where.x, where.y)));
    if (!isPointInScreenBoundaries(p)) {
      throw actionFailure("The component to click is out of the boundaries of the screen");
    }
    pressMouse(p, buttons);
  }

  @Override
  public void pressMouse(@Nonnull Point where, int buttons) {
    moveMouse(where.x, where.y);
    pressMouse(buttons);
  }

  @Override
  public void pressMouse(int buttons) {
    robot.mousePress(buttons);
  }

  @Override
  public void releaseMouse(int buttons) {
    robot.mouseRelease(buttons);
  }

  @Override
  public void rotateMouseWheel(int amount) {
    robot.mouseWheel(amount);
  }

  @RunsInEDT
  @Override
  public void moveMouse(@Nonnull Component c, int x, int y) {
    Point p = checkNotNull(execute(() -> translate(c, x, y)));
    moveMouse(p.x, p.y);
  }

  @Override
  public void moveMouse(int x, int y) {
    robot.mouseMove(x, y);
  }

  @Override
  public void pressKey(int keyCode, char keyChar) {
    try {
      robot.keyPress(keyCode);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(String.format("Invalid key code '%d'", keyCode));
    }
  }

  @Override
  public void releaseKey(int keyCode) {
    robot.keyRelease(keyCode);
    if (!isOSX()) {
      return;
    }
    int delayBetweenEvents = settings.delayBetweenEvents();
    if (KEY_INPUT_DELAY > delayBetweenEvents) {
      pause(KEY_INPUT_DELAY - delayBetweenEvents);
    }
  }
}
