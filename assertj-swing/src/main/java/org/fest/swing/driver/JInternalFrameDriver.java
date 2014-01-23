/*
 * Created on Feb 1, 2008
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

import static org.fest.swing.driver.ComponentPreconditions.checkShowing;
import static org.fest.swing.driver.JInternalFrameAction.DEICONIFY;
import static org.fest.swing.driver.JInternalFrameAction.ICONIFY;
import static org.fest.swing.driver.JInternalFrameAction.MAXIMIZE;
import static org.fest.swing.driver.JInternalFrameAction.NORMALIZE;
import static org.fest.swing.driver.JInternalFrameIconQuery.isIconified;
import static org.fest.swing.driver.JInternalFrameSetIconTask.setIcon;
import static org.fest.swing.driver.JInternalFrameSetMaximumTask.setMaximum;
import static org.fest.swing.driver.WindowLikeContainers.closeButtonLocation;
import static org.fest.swing.driver.WindowLikeContainers.iconifyButtonLocation;
import static org.fest.swing.driver.WindowLikeContainers.maximizeButtonLocation;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyVetoException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JInternalFrame;
import javax.swing.JInternalFrame.JDesktopIcon;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.UnexpectedException;
import org.fest.swing.util.Pair;
import org.fest.swing.util.Triple;
import org.fest.util.InternalApi;
import org.fest.util.VisibleForTesting;

/**
 * <p>
 * Supports functional testing of {@code JInternalFrame}s.
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
public class JInternalFrameDriver extends JComponentDriver {
  /**
   * Creates a new {@link JInternalFrameDriver}.
   *
   * @param robot the robot to use to simulate user input.
   */
  public JInternalFrameDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Brings the given {@code JInternalFrame} to the front.
   *
   * @param internalFrame the target {@code JInternalFrame}.
   */
  @RunsInEDT
  public void moveToFront(final @Nonnull JInternalFrame internalFrame) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        // it seems that moving to front always works, regardless if the internal frame is invisible and/or disabled.
        internalFrame.toFront();
      }
    });
  }

  /**
   * Brings the given {@code JInternalFrame} to the back.
   *
   * @param internalFrame the target {@code JInternalFrame}.
   */
  @RunsInEDT
  public void moveToBack(final @Nonnull JInternalFrame internalFrame) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        // it seems that moving to back always works, regardless if the internal frame is invisible and/or disabled.
        internalFrame.moveToBack();
      }
    });
  }

  /**
   * Maximizes the given {@code JInternalFrame}, deconifying it first if it is iconified.
   *
   * @param internalFrame the target {@code JInternalFrame}.
   * @throws IllegalStateException if the {@code JInternalFrame} is not maximizable.
   * @throws IllegalStateException if the {@code JInternalFrame} is not showing on the screen.
   * @throws ActionFailedException if the {@code JInternalFrame} vetoes the action.
   */
  @RunsInEDT
  public void maximize(@Nonnull JInternalFrame internalFrame) {
    Pair<Container, Point> maximizeLocation = maximizeLocationOf(internalFrame);
    maximizeOrNormalize(internalFrame, MAXIMIZE, maximizeLocation);
  }

  @RunsInEDT
  private static @Nonnull Pair<Container, Point> maximizeLocationOf(final @Nonnull JInternalFrame internalFrame) {
    Pair<Container, Point> result = execute(new GuiQuery<Pair<Container, Point>>() {
      @Override
      protected @Nullable Pair<Container, Point> executeInEDT() {
        checkCanMaximize(internalFrame);
        return findMaximizeLocation(internalFrame);
      }
    });
    return checkNotNull(result);
  }

  @RunsInCurrentThread
  private static void checkCanMaximize(@Nonnull JInternalFrame internalFrame) {
    checkShowingOrIconified(internalFrame);
    if (!internalFrame.isMaximizable()) {
      String msg = String.format("The JInternalFrame <%s> is not maximizable", format(internalFrame));
      throw new IllegalStateException(msg);
    }
  }

  /**
   * Normalizes the given {@code JInternalFrame}, deconifying it first if it is iconified.
   *
   * @param internalFrame the target {@code JInternalFrame}.
   * @throws IllegalStateException if the {@code JInternalFrame} is not showing on the screen.
   * @throws ActionFailedException if the {@code JInternalFrame} vetoes the action.
   */
  @RunsInEDT
  public void normalize(@Nonnull JInternalFrame internalFrame) {
    Pair<Container, Point> normalizeLocation = validateAndFindNormalizeLocation(internalFrame);
    maximizeOrNormalize(internalFrame, NORMALIZE, normalizeLocation);
  }

  @RunsInEDT
  private static Pair<Container, Point> validateAndFindNormalizeLocation(final @Nonnull JInternalFrame internalFrame) {
    return execute(new GuiQuery<Pair<Container, Point>>() {
      @Override
      protected Pair<Container, Point> executeInEDT() {
        checkShowingOrIconified(internalFrame);
        return findMaximizeLocation(internalFrame);
      }
    });
  }

  @RunsInCurrentThread
  private static void checkShowingOrIconified(@Nonnull JInternalFrame internalFrame) {
    if (!internalFrame.isIcon()) {
      checkShowing(internalFrame);
    }
  }

  @RunsInCurrentThread
  private static @Nonnull Pair<Container, Point> findMaximizeLocation(@Nonnull JInternalFrame internalFrame) {
    Container clickTarget = internalFrame.isIcon() ? internalFrame.getDesktopIcon() : internalFrame;
    Point location = maximizeButtonLocation(checkNotNull(clickTarget));
    return Pair.of(clickTarget, location);
  }

  @RunsInEDT
  private void maximizeOrNormalize(@Nonnull JInternalFrame internalFrame, @Nonnull JInternalFrameAction action,
      @Nonnull Pair<Container, Point> toMoveMouseTo) {
    moveMouseIgnoringAnyError(toMoveMouseTo.first, toMoveMouseTo.second);
    setMaximumProperty(internalFrame, action);
  }

  @RunsInEDT
  private void setMaximumProperty(@Nonnull JInternalFrame internalFrame, @Nonnull JInternalFrameAction action) {
    try {
      setMaximum(internalFrame, action);
      robot.waitForIdle();
    } catch (UnexpectedException unexpected) {
      failIfVetoed(internalFrame, action, unexpected);
    }
  }

  /**
   * Iconifies the given {@code JInternalFrame}.
   *
   * @param internalFrame the target {@code JInternalFrame}.
   * @throws IllegalStateException if the {@code JInternalFrame} is not showing on the screen.
   * @throws IllegalStateException if the {@code JInternalFrame} is not iconifiable.
   * @throws ActionFailedException if the {@code JInternalFrame} vetoes the action.
   */
  @RunsInEDT
  public void iconify(@Nonnull JInternalFrame internalFrame) {
    Pair<Boolean, Point> iconifyInfo = findIconifyInfo(internalFrame);
    if (iconifyInfo.first) {
      return; // internal frame is already iconified
    }
    moveMouseIgnoringAnyError(internalFrame, iconifyInfo.second);
    setIconProperty(internalFrame, ICONIFY);
  }

  @RunsInEDT
  private static @Nonnull Pair<Boolean, Point> findIconifyInfo(final @Nonnull JInternalFrame internalFrame) {
    Pair<Boolean, Point> result = execute(new GuiQuery<Pair<Boolean, Point>>() {
      @Override
      protected @Nullable Pair<Boolean, Point> executeInEDT() throws Throwable {
        checkShowingOrIconified(internalFrame);
        if (!internalFrame.isIconifiable()) {
          String msg = String.format("The JInternalFrame <%s> is not iconifiable.", format(internalFrame));
          throw new IllegalStateException(msg);
        }
        return iconifyInfo(internalFrame);
      }
    });
    return checkNotNull(result);
  }

  @RunsInCurrentThread
  private static @Nonnull Pair<Boolean, Point> iconifyInfo(@Nonnull JInternalFrame internalFrame) {
    boolean iconified = isIconified(internalFrame);
    if (iconified) {
      return Pair.of(true, null);
    }
    return Pair.of(iconified, findIconifyLocation(internalFrame));
  }

  /**
   * De-iconifies the given {@code JInternalFrame}.
   *
   * @param internalFrame the target {@code JInternalFrame}.
   * @throws IllegalStateException if the {@code JInternalFrame} is not showing on the screen.
   * @throws ActionFailedException if the {@code JInternalFrame} vetoes the action.
   */
  @RunsInEDT
  public void deiconify(@Nonnull JInternalFrame internalFrame) {
    Triple<Boolean, Container, Point> deiconifyInfo = validateAndfindDeiconifyInfo(internalFrame);
    if (deiconifyInfo.first) {
      return; // internal frame is already de-iconified
    }
    moveMouseIgnoringAnyError(deiconifyInfo.second, deiconifyInfo.third);
    setIconProperty(internalFrame, DEICONIFY);
  }

  @RunsInEDT
  private static @Nonnull Triple<Boolean, Container, Point> validateAndfindDeiconifyInfo(
      final @Nonnull JInternalFrame internalFrame) {
    Triple<Boolean, Container, Point> result = execute(new GuiQuery<Triple<Boolean, Container, Point>>() {
      @Override
      protected @Nullable Triple<Boolean, Container, Point> executeInEDT() throws Throwable {
        checkShowingOrIconified(internalFrame);
        return deiconifyInfo(internalFrame);
      }
    });
    return checkNotNull(result);
  }

  @RunsInCurrentThread
  private static @Nonnull Triple<Boolean, Container, Point> deiconifyInfo(@Nonnull JInternalFrame internalFrame) {
    boolean deiconified = !isIconified(internalFrame);
    if (deiconified) {
      return Triple.of(true, null, null);
    }
    Container desktopIcon = checkNotNull(internalFrame.getDesktopIcon());
    return Triple.of(deiconified, desktopIcon, iconifyButtonLocation(desktopIcon));
  }

  @RunsInCurrentThread
  private static @Nonnull Point findIconifyLocation(JInternalFrame internalFrame) {
    JDesktopIcon desktopIcon = checkNotNull(internalFrame.getDesktopIcon());
    return iconifyButtonLocation(desktopIcon);
  }

  @RunsInEDT
  private void setIconProperty(@Nonnull JInternalFrame internalFrame, @Nonnull JInternalFrameAction action) {
    try {
      setIcon(internalFrame, action);
      robot.waitForIdle();
    } catch (UnexpectedException unexpected) {
      failIfVetoed(internalFrame, action, unexpected);
    }
  }

  @VisibleForTesting
  void failIfVetoed(@Nonnull JInternalFrame internalFrame, @Nonnull JInternalFrameAction action,
      @Nonnull UnexpectedException unexpected) {
    PropertyVetoException vetoError = vetoFrom(unexpected);
    if (vetoError == null) {
      return;
    }
    String msg = String.format("%s of %s was vetoed: <%s>", action.name, format(internalFrame), vetoError.getMessage());
    throw actionFailure(msg);
  }

  private @Nullable PropertyVetoException vetoFrom(@Nonnull UnexpectedException unexpected) {
    Throwable cause = unexpected.getCause();
    if (!(cause instanceof PropertyVetoException)) {
      return null;
    }
    return (PropertyVetoException) cause;
  }

  /**
   * Resizes the {@code JInternalFrame} horizontally.
   *
   * @param internalFrame the target {@code JInternalFrame}.
   * @param width the width that the {@code JInternalFrame} should have after being resized.
   * @throws IllegalStateException if the {@code JInternalFrame} is not showing on the screen.
   * @throws IllegalStateException if the {@code JInternalFrame} is not resizable by the user.
   */
  @RunsInEDT
  public void resizeWidth(@Nonnull JInternalFrame internalFrame, int width) {
    doResizeWidth(internalFrame, width);
  }

  /**
   * Resizes the {@code JInternalFrame} vertically.
   *
   * @param w the target {@code JInternalFrame}.
   * @param height the height that the {@code JInternalFrame} should have after being resized.
   * @throws IllegalStateException if the {@code JInternalFrame} is not showing on the screen.
   * @throws IllegalStateException if the {@code JInternalFrame} is not resizable by the user.
   */
  @RunsInEDT
  public void resizeHeight(@Nonnull JInternalFrame w, int height) {
    doResizeHeight(w, height);
  }

  /**
   * Resizes the {@code JInternalFrame} to the given size.
   *
   * @param internalFrame the target {@code JInternalFrame}.
   * @param size the size to resize the {@code JInternalFrame} to.
   * @throws IllegalStateException if the {@code JInternalFrame} is not showing on the screen.
   * @throws IllegalStateException if the {@code JInternalFrame} is not resizable by the user.
   */
  @RunsInEDT
  public void resizeTo(@Nonnull JInternalFrame internalFrame, @Nonnull Dimension size) {
    resize(internalFrame, size.width, size.height);
  }

  /**
   * Moves the {@code JInternalFrame} to the given location.
   *
   * @param internalFrame the target {@code JInternalFrame}.
   * @param where the location to move the {@code JInternalFrame} to.
   * @throws IllegalStateException if the {@code JInternalFrame} is not showing on the screen.
   */
  @RunsInEDT
  public void move(@Nonnull JInternalFrame internalFrame, @Nonnull Point where) {
    move(internalFrame, where.x, where.y);
  }

  /**
   * Closes the given {@code JInternalFrame}.
   *
   * @param internalFrame the target {@code JInternalFrame}.
   * @throws IllegalStateException if the {@code JInternalFrame} is not showing on the screen.
   * @throws IllegalStateException if the {@code JInternalFrame} is not closable.
   */
  @RunsInEDT
  public void close(@Nonnull JInternalFrame internalFrame) {
    Point closeButtonLocation = findCloseButtonLocation(internalFrame);
    if (closeButtonLocation == null) {
      return; // internal frame is already closed
    }
    moveMouseIgnoringAnyError(internalFrame, closeButtonLocation);
    JInternalFrameCloseTask.close(internalFrame);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static @Nullable Point findCloseButtonLocation(final @Nonnull JInternalFrame internalFrame) {
    return execute(new GuiQuery<Point>() {
      @Override
      protected @Nullable Point executeInEDT() {
        checkShowing(internalFrame);
        if (!internalFrame.isClosable()) {
          String msg = String.format("The JInternalFrame <%s> is not closable", format(internalFrame));
          throw new IllegalStateException(msg);
        }
        if (internalFrame.isClosed()) {
          return null;
        }
        return closeButtonLocation(internalFrame);
      }
    });
  }
}
