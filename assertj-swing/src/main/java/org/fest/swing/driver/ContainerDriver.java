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

import static org.fest.reflect.core.Reflection.method;
import static org.fest.swing.driver.ComponentMovableQuery.isUserMovable;
import static org.fest.swing.driver.ComponentMoveTask.moveComponent;
import static org.fest.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.fest.swing.driver.ComponentPreconditions.checkShowing;
import static org.fest.swing.driver.ComponentSetSizeTask.setComponentSize;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.format.Formatting.format;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.IllegalComponentStateException;
import java.awt.Insets;
import java.awt.Point;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JInternalFrame;

import org.fest.reflect.exception.ReflectionError;
import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.util.Pair;
import org.fest.swing.util.Triple;
import org.fest.util.InternalApi;
import org.fest.util.VisibleForTesting;

/**
 * <p>
 * Supports functional testing of AWT or Swing {@code Container}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.fest.swing.fixture} in your tests.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@InternalApi
public abstract class ContainerDriver extends ComponentDriver {
  /**
   * Creates a new {@link ContainerDriver}.
   *
   * @param robot the robot to use to simulate user input.
   */
  public ContainerDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Resizes the AWT or Swing {@code Container} horizontally.
   *
   * @param c the given {@code Container}.
   * @param width the width that the {@code Container} should have after being resized.
   * @throws IllegalStateException if the {@code Container} is not enabled.
   * @throws IllegalStateException if the {@code Container} is not resizable by the user.
   * @throws IllegalStateException if the {@code Container} is not showing on the screen.
   */
  @RunsInEDT
  protected final void doResizeWidth(@Nonnull Container c, int width) {
    Pair<Dimension, Insets> resizeInfo = resizeInfo(c);
    Dimension size = resizeInfo.first;
    resizeBy(c, resizeInfo, width - size.width, 0);
  }

  /**
   * Resizes the AWT or Swing {@code Container} vertically.
   *
   * @param c the given {@code Container}.
   * @param height the height that the {@code Container} should have after being resized.
   * @throws IllegalStateException if the {@code Container} is not enabled.
   * @throws IllegalStateException if the {@code Container} is not resizable by the user.
   * @throws IllegalStateException if the {@code Container} is not showing on the screen.
   */
  @RunsInEDT
  protected final void doResizeHeight(@Nonnull Container c, int height) {
    Pair<Dimension, Insets> resizeInfo = resizeInfo(c);
    Dimension size = resizeInfo.first;
    resizeBy(c, resizeInfo, 0, height - size.height);
  }

  /**
   * Resizes the AWT or Swing {@code Container} to the given size.
   *
   * @param c the given {@code Container}.
   * @param width the width to resize the {@code Container} to.
   * @param height the height to resize the {@code Container} to.
   * @throws IllegalStateException if the {@code Container} is not enabled.
   * @throws IllegalStateException if the {@code Container} is not resizable by the user.
   * @throws IllegalStateException if the {@code Container} is not showing on the screen.
   */
  @RunsInEDT
  protected final void resize(@Nonnull Container c, int width, int height) {
    Pair<Dimension, Insets> resizeInfo = resizeInfo(c);
    Dimension size = resizeInfo.first;
    resizeBy(c, resizeInfo, width - size.width, height - size.height);
  }

  @RunsInEDT
  private @Nonnull Pair<Dimension, Insets> resizeInfo(final @Nonnull Container c) {
    Pair<Dimension, Insets> result = execute(new GuiQuery<Pair<Dimension, Insets>>() {
      @Override
      protected @Nullable Pair<Dimension, Insets> executeInEDT() {
        checkCanResize(c);
        return Pair.of(c.getSize(), c.getInsets());
      }
    });
    return checkNotNull(result);
  }

  @VisibleForTesting
  @RunsInCurrentThread
  void checkCanResize(@Nonnull Container c) {
    if (!isResizable(c)) {
      String msg = String.format("Expecting component %s to be resizable by the user", format(c));
      throw new IllegalStateException(msg);
    }
    if (c instanceof JInternalFrame) {
      checkShowing(c);
      return;
    }
    checkEnabledAndShowing(c);
  }

  @RunsInCurrentThread
  protected boolean isResizable(@Nonnull Container c) {
    try {
      Boolean resizable = method("isResizable").withReturnType(boolean.class).in(c).invoke();
      return checkNotNull(resizable);
    } catch (ReflectionError e) {
      return false;
    }
  }

  @RunsInEDT
  private void resizeBy(@Nonnull Container c, @Nonnull Pair<Dimension, Insets> resizeInfo, int x, int y) {
    simulateResizeStarted(c, resizeInfo, x, y);
    Dimension size = resizeInfo.first;
    setComponentSize(c, size.width + x, size.height + y);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void simulateResizeStarted(@Nonnull Container c, @Nonnull Pair<Dimension, Insets> resizeInfo, int x, int y) {
    Point p = resizeLocation(resizeInfo);
    moveMouseIgnoringAnyError(c, p);
    moveMouseIgnoringAnyError(c, p.x + x, p.y + y);
  }

  private static @Nonnull Point resizeLocation(final @Nonnull Pair<Dimension, Insets> resizeInfo) {
    return resizeLocation(checkNotNull(resizeInfo.first), checkNotNull(resizeInfo.second));
  }

  private static @Nonnull Point resizeLocation(@Nonnull Dimension size, @Nonnull Insets insets) {
    return resizeLocation(size.width, size.height, insets.right, insets.bottom);
  }

  private static @Nonnull Point resizeLocation(int width, int height, int right, int bottom) {
    return new Point(width - right / 2, height - bottom / 2);
  }

  /**
   * Move the given AWT or Swing {@code Container} to the requested location.
   *
   * @param c the given {@code Container}.
   * @param x the horizontal coordinate.
   * @param y the vertical coordinate.
   * @throws IllegalStateException if the {@code Container} is not enabled.
   * @throws IllegalStateException if the {@code Container} is not movable by the user.
   * @throws IllegalStateException if the {@code Container} is not showing on the screen.
   */
  @RunsInEDT
  public void move(@Nonnull Container c, int x, int y) {
    Triple<Dimension, Insets, Point> moveInfo = moveInfo(c);
    Point locationOnScreen = moveInfo.third;
    moveBy(c, moveInfo, x - locationOnScreen.x, y - locationOnScreen.y);
  }

  @RunsInEDT
  private @Nonnull Triple<Dimension, Insets, Point> moveInfo(final @Nonnull Container c) {
    Triple<Dimension, Insets, Point> result = execute(new GuiQuery<Triple<Dimension, Insets, Point>>() {
      @Override
      protected @Nullable Triple<Dimension, Insets, Point> executeInEDT() {
        checkCanMove(c);
        Point locationOnScreen = null;
        try {
          locationOnScreen = c.getLocationOnScreen();
        } catch (IllegalComponentStateException e) {
          // we should not get to this point, validateIsShowing should have already catched that the container is not
          // visible.
        }
        if (locationOnScreen == null) {
          String msg = String.format("Expecting component %s to be showing on the screen", format(c));
          throw new IllegalStateException(msg);
        }
        return Triple.of(c.getSize(), c.getInsets(), locationOnScreen);
      }
    });
    return checkNotNull(result);
  }

  @RunsInCurrentThread
  private void checkCanMove(@Nonnull Container c) {
    checkEnabledAndShowing(c);
    if (!isUserMovable(c)) {
      String msg = String.format("Expecting component %s to be movable by the user", format(c));
      throw new IllegalStateException(msg);
    }
  }

  @RunsInEDT
  private void moveBy(@Nonnull Container c, @Nonnull Triple<Dimension, Insets, Point> moveInfo, int x, int y) {
    simulateMoveStarted(c, moveInfo, x, y);
    Point locationOnScreen = moveInfo.third;
    Point location = new Point(locationOnScreen.x + x, locationOnScreen.y + y);
    moveComponent(c, location);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void simulateMoveStarted(@Nonnull Container c, @Nonnull Triple<Dimension, Insets, Point> moveInfo, int x,
      int y) {
    Point p = moveLocation(checkNotNull(moveInfo.first), checkNotNull(moveInfo.second));
    moveMouseIgnoringAnyError(c, p);
    moveMouseIgnoringAnyError(c, p.x + x, p.y + y);
  }

  // Returns where the mouse usually grabs to move a container (or window.) Center of the top of the frame is usually a
  // good choice.
  private @Nonnull Point moveLocation(@Nonnull Dimension size, @Nonnull Insets insets) {
    return new Point(size.width / 2, insets.top / 2);
  }
}
