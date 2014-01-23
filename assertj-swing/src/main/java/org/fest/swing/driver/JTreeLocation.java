/*
 * Created on Jan 12, 2008
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

import static org.fest.util.Arrays.format;

import java.awt.Point;
import java.awt.Rectangle;

import javax.annotation.Nonnull;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.util.Pair;

/**
 * A visible location on a {@code JTree}. A row index or a {@link String}ified {@code TreePath} (i.e. each
 * {@code TreePath} component is a {@code String}) or a {@code TreePath} of {@code Object} may be used to indicate the
 * location. Note that if a {@code TreePath} is used, the entire path leading up to the designated node must be viewable
 * at the time the location is used.
 * 
 * @author Alex Ruiz
 */
public final class JTreeLocation {
  /**
   * <p>
   * Returns the bounds and visible coordinates of the given row.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param tree the target {@code JTree}.
   * @param row the given row.
   * @return the the bounds and visible coordinates of the given row.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   * @since 1.2
   */
  @RunsInCurrentThread
  public @Nonnull Pair<Rectangle, Point> rowBoundsAndCoordinates(@Nonnull JTree tree, int row) {
    Rectangle rowBounds = tree.getRowBounds(checkRowInBounds(tree, row));
    if (rowBounds != null) {
      return Pair.of(rowBounds, pointAt(rowBounds));
    }
    throw new LocationUnavailableException(String.format("The tree row <%d> is not visible", row));
  }

  /**
   * <p>
   * Returns the path for the given row.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param tree the target {@code JTree}.
   * @param row the given row.
   * @return the path for the given row.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  @RunsInCurrentThread
  public @Nonnull TreePath pathFor(@Nonnull JTree tree, int row) {
    TreePath path = tree.getPathForRow(checkRowInBounds(tree, row));
    if (path != null) {
      return path;
    }
    throw new LocationUnavailableException(String.format("Unable to find tree path for row <%d>", row));
  }

  /**
   * <p>
   * Validates that the given row index is valid.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param tree the target {@code JTree}.
   * @param row the row index to validate.
   * @return the validated row index.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   */
  @RunsInCurrentThread
  public int checkRowInBounds(@Nonnull JTree tree, int row) {
    int rowCount = tree.getRowCount();
    if (row >= 0 && row < rowCount) {
      return row;
    }
    String msg = String.format("The given row <%d> should be between <0> and <%d>", row, rowCount);
    throw new IndexOutOfBoundsException(msg);
  }

  /**
   * <p>
   * Returns the bounds and visible coordinates of the given path.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param tree the target {@code JTree}.
   * @param path the given path.
   * @return the bounds and visible coordinates of the given path.
   * @throws LocationUnavailableException if any part of the path is not visible.
   * @since 1.2
   */
  @RunsInCurrentThread
  public @Nonnull Pair<Rectangle, Point> pathBoundsAndCoordinates(@Nonnull JTree tree, @Nonnull TreePath path) {
    Rectangle pathBounds = tree.getPathBounds(path);
    if (pathBounds != null) {
      return Pair.of(pathBounds, pointAt(pathBounds));
    }
    throw new LocationUnavailableException(String.format("The tree path %s is not visible", format(path.getPath())));
  }

  private @Nonnull Point pointAt(@Nonnull Rectangle cellBounds) {
    return new Point(cellBounds.x + cellBounds.width / 2, cellBounds.y + cellBounds.height / 2);
  }
}
