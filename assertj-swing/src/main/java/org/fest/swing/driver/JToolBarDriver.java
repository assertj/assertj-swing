/*
 * Created on Feb 2, 2008
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

import static javax.swing.SwingUtilities.getWindowAncestor;
import static org.fest.reflect.core.Reflection.field;
import static org.fest.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.fest.swing.driver.JToolBarIsFloatingQuery.isJToolBarFloating;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Container;
import java.awt.Point;
import java.awt.Window;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JToolBar;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.util.GenericRange;
import org.fest.swing.util.Pair;
import org.fest.util.InternalApi;

/**
 * <p>
 * Supports functional testing of {@code JToolBar}s.
 * </p>
 * 
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.fest.swing.fixture} in your tests.
 * </p>
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@InternalApi
public class JToolBarDriver extends JComponentDriver {
  private final JToolBarLocation location = new JToolBarLocation();

  /**
   * Creates a new {@link JToolBarDriver}.
   * 
   * @param robot the robot to use to simulate user input.
   */
  public JToolBarDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Indicates whether the given {@code JToolBar} is floating or not.
   * 
   * @param toolBar the target {@code JToolBar}.
   * @return {@code true} if the {@code JToolBar} is floating, {@code false} otherwise.
   */
  @RunsInEDT
  public boolean isFloating(final @Nonnull JToolBar toolBar) {
    Boolean result = execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return isJToolBarFloating(toolBar);
      }
    });
    return checkNotNull(result);
  }

  /**
   * Makes the given {@code JToolBar} float.
   * 
   * @param toolBar the target {@code JToolBar}.
   * @throws IllegalStateException if the {@code JToolBar} is disabled.
   * @throws IllegalStateException if the {@code JToolBar} is not showing on the screen.
   * @throws IllegalStateException if the {@code JToolBar} is not floatable.
   * @throws ActionFailedException if the {@code JToolBar} cannot be dragged.
   */
  public void makeFloat(@Nonnull JToolBar toolBar) {
    Pair<Point, Pair<Window, Point>> floatInfo = floatInfo(toolBar, location());
    Point p = floatInfo.second.second; // ancestor location
    doFloat(toolBar, p.x, p.y, floatInfo);
  }

  /**
   * Drags the {@code JToolBar} to the given location, causing it to float.
   * 
   * @param toolBar the target {@code JToolBar}.
   * @param x the horizontal coordinate of the location to drag the {@code JToolBar} to.
   * @param y the vertical coordinate of the location to drag the {@code JToolBar} to.
   * @throws IllegalStateException if the {@code JToolBar} is disabled.
   * @throws IllegalStateException if the {@code JToolBar} is not showing on the screen.
   * @throws IllegalStateException if the {@code JToolBar} is not floatable.
   * @throws ActionFailedException if the {@code JToolBar} cannot be dragged.
   */
  @RunsInEDT
  public void floatTo(@Nonnull JToolBar toolBar, int x, int y) {
    doFloat(toolBar, x, y, floatInfo(toolBar, location()));
  }

  @RunsInEDT
  private static @Nonnull Pair<Point, Pair<Window, Point>> floatInfo(final @Nonnull JToolBar toolBar,
      final @Nonnull JToolBarLocation location) {
    Pair<Point, Pair<Window, Point>> result = execute(new GuiQuery<Pair<Point, Pair<Window, Point>>>() {
      @Override
      protected Pair<Point, Pair<Window, Point>> executeInEDT() {
        checkEnabledAndShowing(toolBar);
        checkFloatable(toolBar);
        Pair<Window, Point> windowAndLocation = ancestorAndLocation(toolBar);
        return Pair.of(location.pointToGrab(toolBar), windowAndLocation);
      }
    });
    return checkNotNull(result);
  }

  @RunsInCurrentThread
  private static void checkFloatable(@Nonnull JToolBar toolBar) {
    if (!toolBar.isFloatable()) {
      String msg = String.format("JToolbar <%s> is not floatable", format(toolBar));
      throw new IllegalStateException(msg);
    }
  }

  @RunsInCurrentThread
  private static @Nonnull Pair<Window, Point> ancestorAndLocation(final @Nonnull JToolBar toolBar) {
    Window window = getWindowAncestor(toolBar);
    return Pair.of(window, window.getLocation());
  }

  @RunsInEDT
  private void doFloat(@Nonnull JToolBar toolBar, int x, int y, Pair<Point, Pair<Window, Point>> floatInfo) {
    drag(toolBar, checkNotNull(floatInfo.first));
    Pair<Window, Point> locationAndAncestor = floatInfo.second;
    Point ancestorLocation = locationAndAncestor.second;
    drop(checkNotNull(locationAndAncestor.first), new Point(x - ancestorLocation.x, y - ancestorLocation.y));
    checkFloated(toolBar);
  }

  @RunsInEDT
  private static void checkFloated(final @Nonnull JToolBar toolBar) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        if (!isJToolBarFloating(toolBar)) {
          throw actionFailure(String.format("Unable to float JToolbar <%s>", format(toolBar)));
        }
      }
    });
  }

  /**
   * Drop the {@code JToolBar} to the requested constraint position. The constraint position must be one of the
   * constants {@code BorderLayout.NORTH NORTH}, {@code BorderLayout.EAST EAST}, {@code BorderLayout.SOUTH SOUTH}, or
   * {@code BorderLayout.WEST WEST}.
   * 
   * @param toolBar the target {@code JToolBar}.
   * @param constraint the constraint position.
   * @throws IllegalStateException if the {@code JToolBar} is disabled.
   * @throws IllegalStateException if the {@code JToolBar} is not showing on the screen.
   * @throws IllegalArgumentException if the constraint has an invalid value.
   * @throws ActionFailedException if the dock container cannot be found.
   */
  @RunsInEDT
  public void unfloat(@Nonnull JToolBar toolBar, @Nonnull String constraint) {
    Pair<GenericRange<Point>, Container> unfloatInfo = unfloatInfo(toolBar, constraint, location());
    GenericRange<Point> fromAndTo = unfloatInfo.first;
    drag(toolBar, fromAndTo.from());
    drop(checkNotNull(unfloatInfo.second), fromAndTo.to());
    validateIsNotFloating(toolBar, constraint);
  }

  @RunsInEDT
  private static @Nonnull  Pair<GenericRange<Point>, Container> unfloatInfo(final @Nonnull JToolBar toolBar,
      final @Nonnull String constraint, final @Nonnull JToolBarLocation location) {
    Pair<GenericRange<Point>, Container> result = execute(new GuiQuery<Pair<GenericRange<Point>, Container>>() {
      @Override
      protected Pair<GenericRange<Point>, Container> executeInEDT() {
        checkEnabledAndShowing(toolBar);
        Container dock = dockFor(toolBar);
        Point from = location.pointToGrab(toolBar);
        Point to = location.dockLocation(toolBar, dock, constraint);
        return Pair.of(new GenericRange<Point>(from, to), dock);
      }
    });
    return checkNotNull(result);
  }

  @RunsInEDT
  private static void validateIsNotFloating(final @Nonnull JToolBar toolBar, final @Nonnull String constraint) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        if (isJToolBarFloating(toolBar)) {
          String msg = String.format("Failed to dock <%s> using constraint ''", format(toolBar), constraint);
          throw actionFailure(msg);
        }
      }
    });
  }

  @RunsInCurrentThread
  private static @Nonnull Container dockFor(final @Nonnull JToolBar toolBar) {
    try {
      return checkNotNull(field("dockingSource").ofType(Container.class).in(toolBar.getUI()).get());
    } catch (RuntimeException e) {
      throw actionFailure("Unabled to determine dock for JToolBar");
    }
  }

  /**
   * Closes a floating {@code JToolBar}, making it go back to its original container in its last known location.
   * 
   * @param toolBar the target {@code JToolBar}.
   * @throws IllegalStateException if the {@code JToolBar} is disabled.
   * @throws IllegalStateException if the {@code JToolBar} is not showing on the screen.
   */
  @RunsInEDT
  public void unfloat(@Nonnull JToolBar toolBar) {
    Window w = windowAncestorOf(toolBar);
    if (w != null) {
      robot.close(w);
    }
  }

  @RunsInEDT
  private static @Nullable Window windowAncestorOf(final @Nonnull JToolBar toolBar) {
    return execute(new GuiQuery<Window>() {
      @Override
      protected Window executeInEDT() {
        checkEnabledAndShowing(toolBar);
        return getWindowAncestor(toolBar);
      }
    });
  }

  private @Nonnull JToolBarLocation location() {
    return location;
  }
}
