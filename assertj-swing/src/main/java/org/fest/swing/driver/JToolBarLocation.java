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

import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;
import static java.lang.Math.max;
import static javax.swing.SwingConstants.HORIZONTAL;
import static org.fest.util.Arrays.format;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;

import javax.annotation.Nonnull;
import javax.swing.JToolBar;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * A visible location on a {@code JToolBar}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class JToolBarLocation {
  private static String[] VALID_CONSTRAINTS = { NORTH, EAST, SOUTH, WEST };

  /**
   * <p>
   * Returns the point where to grab the given {@code JToolBar}.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param toolBar the target {@code JToolBar}.
   * @return the point where to grab the given {@code JToolBar}.
   */
  @RunsInCurrentThread
  public @Nonnull Point pointToGrab(@Nonnull JToolBar toolBar) {
    Insets insets = toolBar.getInsets();
    int width = toolBar.getWidth();
    int height = toolBar.getHeight();
    if (max(max(max(insets.left, insets.top), insets.right), insets.bottom) == insets.left) {
      return new Point(insets.left / 2, height / 2);
    }
    if (max(max(insets.top, insets.right), insets.bottom) == insets.top) {
      return new Point(width / 2, insets.top / 2);
    }
    if (max(insets.right, insets.bottom) == insets.right) {
      return new Point(width - insets.right / 2, height / 2);
    }
    return new Point(width / 2, height - insets.bottom / 2);
  }

  /**
   * <P>
   * Returns the location where to dock the given {@code JToolBar}, at the given constraint position. The constraint
   * position must be one of the constants {@code BorderLayout.NORTH NORTH}, {@code BorderLayout.EAST EAST},
   * {@code BorderLayout.SOUTH SOUTH}, or {@code BorderLayout.WEST WEST}.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param toolBar the target {@code JToolBar}.
   * @param dock the container where to dock the {@code JToolBar} to.
   * @param constraint the constraint position.
   * @return the location where to dock the given {@code JToolBar}.
   * @throws IllegalArgumentException if the constraint has an invalid value.
   */
  @RunsInCurrentThread
  public @Nonnull Point dockLocation(@Nonnull JToolBar toolBar, @Nonnull Container dock, @Nonnull String constraint) {
    checkValid(constraint);
    Insets insets = dock.getInsets();
    // BasicToolBarUI prioritizes location N/E/W/S by proximity to the respective border. Close to top border is N, even
    // if close to the left or right border.
    int offset = isHorizontal(toolBar) ? toolBar.getHeight() : toolBar.getWidth();
    Dimension dockSize = dock.getSize();
    if (NORTH.equals(constraint)) {
      return new Point(dockSize.width / 2, insets.top);
    }
    if (EAST.equals(constraint)) {
      return new Point(dockSize.width - insets.right - 1, verticalDockingYCoordinate(dockSize.height, insets, offset));
    }
    if (WEST.equals(constraint)) {
      return new Point(insets.left, verticalDockingYCoordinate(dockSize.height, insets, offset));
    }
    int x = dockSize.width / 2;
    // Make sure we don't get mistaken for EAST or WEST
    if (x < insets.left + offset) {
      x = insets.left + offset;
    } else if (x > dockSize.width - insets.right - offset - 1) {
      x = dockSize.width - insets.right - offset - 1;
    }
    return new Point(x, dockSize.height - insets.bottom - 1);
  }

  @RunsInCurrentThread
  private boolean isHorizontal(@Nonnull JToolBar toolBar) {
    return toolBar.getOrientation() == HORIZONTAL;
  }

  private void checkValid(@Nonnull String constraint) {
    for (String validConstraint : VALID_CONSTRAINTS) {
      if (validConstraint.equals(constraint)) {
        return;
      }
    }
    throw invalidConstraint(constraint);
  }

  private IllegalArgumentException invalidConstraint(@Nonnull String constraint) {
    String format = "'%s' is not a valid constraint. Valid constraints are %s";
    String msg = String.format(format, constraint, format(VALID_CONSTRAINTS));
    throw new IllegalArgumentException(msg);
  }

  private int verticalDockingYCoordinate(int dockHeight, @Nonnull Insets insets, int offset) {
    int y = dockHeight / 2;
    // Make sure we don't get mistaken for NORTH
    if (y < insets.top + offset) {
      y = insets.top + offset;
    }
    return y;
  }
}
