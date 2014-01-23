/*
 * Created on Jan 27, 2008
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

import static org.fest.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.fest.swing.driver.WindowLikeContainers.closeButtonLocation;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.util.InternalApi;

/**
 * <p>
 * Supports functional testing of AWT or Swing {@code Window}s.
 * </p>
 * 
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.fest.swing.fixture} in your tests.
 * </p>
 * 
 * @author Alex Ruiz
 */
@InternalApi
public class WindowDriver extends ContainerDriver {
  /**
   * Creates a new {@link WindowDriver}.
   * 
   * @param robot the robot to use to simulate user input.
   */
  public WindowDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Resizes the {@code Window} horizontally.
   * 
   * @param w the target {@code Window}.
   * @param width the width that the {@code Window} should have after being resized.
   * @throws ActionFailedException if the {@code Window} is not enabled.
   * @throws ActionFailedException if the {@code Window} is not resizable by the user.
   * @throws ActionFailedException if the {@code Window} is not showing on the screen.
   */
  @RunsInEDT
  public void resizeWidthTo(@Nonnull Window w, int width) {
    doResizeWidth(w, width);
  }

  /**
   * Resizes the {@code Window} vertically.
   * 
   * @param w the target {@code Window}.
   * @param height the height that the {@code Window} should have after being resized.
   * @throws ActionFailedException if the {@code Window} is not enabled.
   * @throws ActionFailedException if the {@code Window} is not resizable by the user.
   * @throws ActionFailedException if the {@code Window} is not showing on the screen.
   */
  @RunsInEDT
  public void resizeHeightTo(@Nonnull Window w, int height) {
    doResizeHeight(w, height);
  }

  /**
   * Resizes the {@code Window} to the given size.
   * 
   * @param w the target {@code Window}.
   * @param size the size to resize the {@code Window} to.
   * @throws ActionFailedException if the {@code Window} is not enabled.
   * @throws ActionFailedException if the {@code Window} is not resizable by the user.
   * @throws ActionFailedException if the {@code Window} is not showing on the screen.
   */
  @RunsInEDT
  public void resizeTo(@Nonnull Window w, @Nonnull Dimension size) {
    resize(w, size.width, size.height);
  }

  /**
   * Moves the {@code Window} to the given location.
   * 
   * @param w the target {@code Window}.
   * @param where the location to move the {@code Window} to.
   * @throws ActionFailedException if the {@code Window} is not enabled.
   * @throws ActionFailedException if the {@code Window} is not movable by the user.
   * @throws ActionFailedException if the {@code Window} is not showing on the screen.
   */
  public void moveTo(@Nonnull Window w, @Nonnull Point where) {
    move(w, where.x, where.y);
  }

  /**
   * Closing the {@code Window}.
   * 
   * @param w the target {@code Window}.
   * @throws ActionFailedException if the {@code Window} is not enabled.
   * @throws ActionFailedException if the {@code Window} is not showing on the screen.
   */
  @RunsInEDT
  public void close(@Nonnull Window w) {
    moveMouseIgnoringAnyError(w, closeInfo(w));
    robot.close(w);
  }

  @RunsInEDT
  private static @Nonnull Point closeInfo(final @Nonnull Window w) {
    Point result = execute(new GuiQuery<Point>() {
      @Override
      protected Point executeInEDT() {
        checkEnabledAndShowing(w);
        return closeButtonLocation(w);
      }
    });
    return checkNotNull(result);
  }

  /**
   * Shows the {@code Window}.
   * 
   * @param w the target {@code Window}.
   */
  @RunsInEDT
  public void show(@Nonnull Window w) {
    robot.showWindow(w);
  }

  /**
   * Shows the {@code Window}, resized to the given size.
   * 
   * @param w the target {@code Window}.
   * @param size the size to resize the {@code Window} to.
   */
  @RunsInEDT
  public void show(@Nonnull Window w, @Nonnull Dimension size) {
    robot.showWindow(w, size);
  }

  /**
   * If the given {@code Window} is visible, brings it to the front and may make it the focused one.
   * 
   * @param w the target {@code Window}.
   */
  @RunsInEDT
  public void moveToFront(@Nonnull Window w) {
    doMoveToFront(w);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void doMoveToFront(final @Nonnull Window w) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        w.toFront();
      }
    });
  }

  /**
   * If the given {@code Window} is visible, sends it to the back and may cause it to lose focus or activation if it is
   * the focused or active.
   * 
   * @param w the target {@code Window}.
   */
  @RunsInEDT
  public void moveToBack(@Nonnull Window w) {
    doMoveToBack(w);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void doMoveToBack(final @Nonnull Window w) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        w.toBack();
      }
    });
  }
}